package com.leepay.payrollcalc.config;

import com.leepay.payrollcalc.constant.Constant;
import com.leepay.payrollcalc.handler.AuthenticationHandler;
import com.leepay.payrollcalc.provider.AdminAuthenticatorProvider;
import com.leepay.payrollcalc.provider.CsrfTokenProvider;
import com.leepay.payrollcalc.service.AdminLoginDetailService;
import com.leepay.payrollcalc.util.PropUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AdminAuthenticatorProvider adminAuthenticatorProvider;
    @Autowired
    AdminLoginDetailService adminLoginDetailService;
    @Autowired
    private AuthenticationHandler authenticationHandler;
    @Autowired
    private CsrfTokenProvider csrfTokenProvider;

    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(adminAuthenticatorProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String loginMode = PropUtil.getProperty("system.login-mode");

        /* 경로별 권한 설정 */
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                    .requestMatchers(new AntPathRequestMatcher("/bootstrap/**/**"), new AntPathRequestMatcher("/favicon/**"))
                    .permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()) // 정적 자원에 대해 허용, (/css/**, /js/**, /images/**, /webjars/**, /**/favicon.ico)
                    .permitAll()
                    .anyRequest().authenticated()
            );

        /* 로그인 모드별 Http 설정 */
        if (loginMode.equals(Constant.LOGIN_MODE_SESSION)) {
            http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenProvider)
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/logout"))) // 로그아웃 경로에 대해서는 무시
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // 로그인
                        .loginProcessingUrl("/login_request") // 로그인 처리할 경로
                        .successHandler(authenticationHandler)
                        .failureHandler(authenticationHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                )
                .rememberMe(rememberMe -> rememberMe
                    .key("payroll")
                    .tokenValiditySeconds(60 * 60 * 24 * 7)
                    .userDetailsService(adminLoginDetailService)
                    .rememberMeParameter("remember-me")
                )
                .sessionManagement(sessionManagement -> sessionManagement
                    .sessionFixation()
                        .changeSessionId()
                    .maximumSessions(1)
//                    .expiredSessionStrategy()
                    .maxSessionsPreventsLogin(false)
                    .sessionRegistry(sessionRegistry())
                    .expiredUrl("/login?expired")
                );
        } else if (loginMode.equals(Constant.LOGIN_MODE_JWT)) {
            // Json Web Token 확인용 Filter
        }
        return http.build();
    }

    // CORS 설정 Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Content-Type", "Authorization", "X-CSRF-TOKEN"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}
