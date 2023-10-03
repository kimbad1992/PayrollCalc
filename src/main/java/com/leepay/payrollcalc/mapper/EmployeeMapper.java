package com.leepay.payrollcalc.mapper;

import com.leepay.payrollcalc.dto.Employee;

import java.util.List;

public interface EmployeeMapper {

    int insertEmployeeListByExcel(List<Employee> empList);

    void upsertPersonInfoByExcel(List<Employee> empList);
}
