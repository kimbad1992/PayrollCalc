package com.leepay.payrollcalc.mapper;

import com.leepay.payrollcalc.dto.Employee;

import java.util.List;

public interface EmployeeMapper {

    int insertEmployeeListByExcel(List<Employee> empList);

    void upsertPersonInfoByExcel(List<Employee> empList);

    void upsertPersonInfo(Employee employee);

    void upsertWorkInfo(Employee employee);

    void upsertFinancialInfo(Employee employee);

    void upsertEducationAndCareer(Employee employee);

    List<Employee> getEmployeeList();
}
