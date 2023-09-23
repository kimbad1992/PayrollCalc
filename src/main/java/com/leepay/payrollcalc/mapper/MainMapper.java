package com.leepay.payrollcalc.mapper;

import com.leepay.payrollcalc.dto.AdminUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {
    AdminUser getAdminUser(String username);
}
