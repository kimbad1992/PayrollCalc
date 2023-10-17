package com.leepay.payrollcalc.config;

import com.leepay.payrollcalc.handler.AuthenticationHandler;
import com.leepay.payrollcalc.provider.AdminAuthenticatorProvider;
import com.leepay.payrollcalc.service.AdminLoginDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(adminAuthenticatorProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/css/**")
                                ,new AntPathRequestMatcher("/js/**")
                                ,new AntPathRequestMatcher("/images/**")
                                ,new AntPathRequestMatcher("/bootstrap/**/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // 로그인
                        .loginProcessingUrl("/login_request") // 로그인 처리할 경로
                        .successHandler(authenticationHandler)
                        .failureHandler(authenticationHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JESSIONID")
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("payroll")
                        .tokenValiditySeconds(60 * 60 * 24 * 7)
                        .userDetailsService(adminLoginDetailService)
                        .rememberMeParameter("remember-me")
                );
        return http.build();
    }
}
