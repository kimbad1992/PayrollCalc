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

@Slf4j
@Service
public class SystemService {

    @Autowired
    private SystemMapper systemMapper;

    public List<Menu> getAllMenu() {
        List<Menu> menus = systemMapper.getAllMenu();
        Map<String, Menu> menuMap = new HashMap<>();
        List<Menu> topLevelMenus = new ArrayList<>();

        for (Menu menu : menus) {
            menuMap.put(menu.getPage_seq(), menu);
        }

        for (Menu menu : menus) {
            if (menu.getParent_page_seq() == null || menu.getParent_page_seq().equals("0")) { // 루트 메뉴
                topLevelMenus.add(menu);
            } else { // 서브 메뉴
                Menu parent = menuMap.get(menu.getParent_page_seq());
                if (parent != null) {
                    parent.getSub_menus().add(menu);
                }
            }
        }
        return topLevelMenus;
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
        log.debug("INSERT ADMIN USER : {}",adminUser);

    }
}
