package com.hxl.mapper;

import com.hxl.annotation.AutoFill;
import com.hxl.entity.Employee;
import com.hxl.enumeration.OperationType;

public interface EmployeeMapper {
    /**
     * 查询员工相关信息
     * @param condition 动态查询
     * @return 将员工信息封装到Employee类里返回
     */
    Employee dynamicQuerySingleEmployee(Employee condition);

    /**
     * 新增员工
     * @param employee 封装员工相关信息
     * @return 返回0表示插入失败 1表示成功
     */
    @AutoFill(OperationType.INSERT) //共有字段自动填充
    int insertEmployee(Employee employee);
}
