package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.dto.AdminUser;
import com.leepay.payrollcalc.dto.Menu;
import com.leepay.payrollcalc.mapper.SystemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SystemService {

    @Autowired
    private SystemMapper systemMapper;

    public List<Menu> getAllMenu() {
        List<Menu> menus = systemMapper.getAllMenu();
        Map<String, Menu> menuMap = new HashMap<>();

        return menus.stream()
                .peek(menu -> menuMap.put(menu.getPage_seq(), menu)) // 메뉴들을 Map에 저장
                .filter(menu -> {
                    if (menu.getParent_page_seq() == null || menu.getParent_page_seq().equals("0")) {
                        return true; // 최상위 메뉴로 유지
                    } else {
                        Menu parent = menuMap.get(menu.getParent_page_seq());
                        if (parent != null) {
                            parent.getSub_menus().add(menu); // 서브 메뉴로 추가
                        }
                        return false; // 최상위 메뉴에서 제외
                    }
                })
                .collect(Collectors.toList()); // 최상위 메뉴만 리스트로 반환
    }

    public List<AdminUser> getAdminUserList() {
        return systemMapper.getAdminUserList();
    }

    @Transactional
    public void adminUserRegister(AdminUser adminUser) {
        String rawPassword = adminUser.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(rawPassword);
        adminUser.setPassword(encodePassword);

        systemMapper.upsertAdminInfo(adminUser);
        systemMapper.upsertAdminRoleInfo(adminUser);
        adminUser.setPassword(rawPassword);
        log.debug("INSERT ADMIN USER : {}",adminUser);
        // 여기서 INSERT 성공/실패 여부 확인용 변수 Return
    }

    public AdminUser getAdminUserById(Long id) {
        return systemMapper.getAdminUserById(id);
    }
}
