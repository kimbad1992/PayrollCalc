package com.leepay.payrollcalc.mapper;

import com.leepay.payrollcalc.dto.AdminUser;
import com.leepay.payrollcalc.dto.Menu;

import java.util.List;

public interface SystemMapper {
    List<Menu> getAllMenu();

    AdminUser getAdminUser(String username);

    List<AdminUser> getAdminUserList();
}
