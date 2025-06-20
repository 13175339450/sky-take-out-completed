package com.hxl.mapper;

import com.github.pagehelper.Page;
import com.hxl.annotation.AutoFill;
import com.hxl.entity.Employee;
import com.hxl.enumeration.OperationType;
import com.hxl.vo.EmployeePageVO;

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

    /**
     * 分页动态查询员工数据
     * @param employee 员工实体类
     * @return 返回分页结果 List<Employee></>集合
     */
    Page<EmployeePageVO> employeeDynamicPage(Employee employee);


    /**
     * 通用的update方法
     * @param employee 要修改的员工信息 的实体类
     * @return 1为修改成功 0为修改失败
     */
    int updateEmployeeInfo(Employee employee);
}
