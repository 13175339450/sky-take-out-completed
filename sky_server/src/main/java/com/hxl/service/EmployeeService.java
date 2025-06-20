package com.hxl.service;

import com.hxl.dto.EmployeeAddDTO;
import com.hxl.dto.EmployeePageDTO;
import com.hxl.entity.Employee;
import com.hxl.result.PageResult;
import com.hxl.vo.EmployeeLoginVO;

public interface EmployeeService {
    /**
     * 员工登录相关接口
     * @param employee 实体类：封装了 前端上传的 username 和 明文密码 password
     * @return 返回 该员工的相关信息 不返回密码
     */
    EmployeeLoginVO employeeLogin(Employee employee);

    /**
     * 新增员工相关接口
     * @param employeeAddDTO 实体DTO：封装了前端上传的 员工基本信息
     */
    void addEmployee(EmployeeAddDTO employeeAddDTO);

    /**
     * 员工分页查询接口
     * @param employeePageDTO 分页信息DTO
     * @return 返回 封装后的分页结果
     */
    PageResult employeePage(EmployeePageDTO employeePageDTO);

    /**
     * 启用、禁用员工
     * @param status 员工的状态 1：启用  0：禁用
     * @param id 员工id
     */
    void startOrStopEmployee(Integer status, Long id);
}
