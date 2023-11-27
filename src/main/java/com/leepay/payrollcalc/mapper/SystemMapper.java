package com.leepay.payrollcalc.mapper;

import com.leepay.payrollcalc.dto.AdminUser;
import com.leepay.payrollcalc.dto.Menu;

import java.util.List;

public interface SystemMapper {
    List<Menu> getAllMenu();

    AdminUser getAdminUserByUsername(String username);
    AdminUser getAdminUserById(Long id);

    List<AdminUser> getAdminUserList();

    void upsertAdminInfo(AdminUser adminUser);

    void upsertAdminRoleInfo(AdminUser adminUser);
}
