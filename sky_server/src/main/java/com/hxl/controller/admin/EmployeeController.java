package com.hxl.controller.admin;

import com.hxl.EmployeeAddDTO;
import com.hxl.entity.Employee;
import com.hxl.result.Result;
import com.hxl.service.EmployeeService;
import com.hxl.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("AdminEmployeeController")
@RequestMapping("/admin/employee")
@Slf4j //开启日志
@Api(tags = "管理端的员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录接口
     */
    @PostMapping("login")
    @ApiOperation("员工登录接口")
    public Result<EmployeeLoginVO> employeeLogin(@RequestBody Employee employee){
        log.info("员工信息为：{}", employee);
        //调用服务层
        EmployeeLoginVO vo = employeeService.employeeLogin(employee);
        //登录成功 返沪结果给前端
        return Result.success(vo);
    }

    /**
     * 退出登录接口
     */
    @PostMapping("logout")
    @ApiOperation("退出登录")
    public Result employeeLogout(){
        return Result.success();
    }

    /**
     * 新增员工接口
     */
    @PostMapping
    @ApiOperation("新增员工接口")
    public Result addEmployee(@RequestBody EmployeeAddDTO employeeAddDTO){
        log.info("新增员工dto：{}", employeeAddDTO);
        employeeService.addEmployee(employeeAddDTO);
        return Result.success();
    }
}
