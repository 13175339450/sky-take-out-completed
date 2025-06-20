package com.hxl.service;

import com.hxl.EmployeeAddDTO;
import com.hxl.entity.Employee;
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
}
