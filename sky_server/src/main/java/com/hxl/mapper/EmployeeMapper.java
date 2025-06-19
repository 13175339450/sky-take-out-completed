package com.hxl.mapper;

import com.hxl.entity.Employee;

public interface EmployeeMapper {
    /**
     * 查询员工相关信息
     * @param condition 动态查询
     * @return 将员工信息封装到Employee类里返回
     */
    Employee dynamicQuerySingleEmployee(Employee condition);
}
