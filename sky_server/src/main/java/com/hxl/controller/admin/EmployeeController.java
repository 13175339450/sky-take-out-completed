package com.hxl.controller.admin;

import com.hxl.constant.RedisNameConstant;
import com.hxl.context.BaseContext;
import com.hxl.dto.EmployeeEditInfoDTO;
import com.hxl.dto.EditPasswordDTO;
import com.hxl.dto.EmployeeAddDTO;
import com.hxl.dto.EmployeePageDTO;
import com.hxl.entity.Employee;
import com.hxl.result.PageResult;
import com.hxl.result.Result;
import com.hxl.service.EmployeeService;
import com.hxl.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("AdminEmployeeController")
@RequestMapping("/admin/employee")
@Slf4j //开启日志
@Api(tags = "管理端的员工相关接口")
public class EmployeeController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录接口
     */
    @PostMapping("login")
    @ApiOperation("员工登录接口")
    public Result<EmployeeLoginVO> employeeLogin(@RequestBody Employee employee) {
        log.info("员工登录：{}", employee);
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
    public Result employeeLogout(@RequestHeader("token") String token) {
        String redisKey = RedisNameConstant.EMPLOYEE_TOKEN_CACHE + token;
        stringRedisTemplate.delete(redisKey);
        return Result.success();
    }

    /**
     * 新增员工接口
     */
    @PostMapping
    @ApiOperation("新增员工接口")
    public Result addEmployee(@RequestBody EmployeeAddDTO employeeAddDTO) {
        log.info("新增员工：{}", employeeAddDTO);
        employeeService.addEmployee(employeeAddDTO);
        return Result.success();
    }

    /**
     * 员工分页查询
     */
    @GetMapping("page")
    @ApiOperation("员工分页查询接口")
    public Result<PageResult> employeePage(EmployeePageDTO employeePageDTO) {
        log.info("员工分页查询: {}", employeePageDTO);

        PageResult vo = employeeService.employeePage(employeePageDTO);

        return Result.success(vo);
    }

    /**
     * 启用、禁用员工
     */
    @PostMapping("status/{status}")
    @ApiOperation("启用、禁用员工相关接口")
    public Result startOrStopEmployee(@PathVariable Integer status, Long id) {
        log.info("启用、禁用员工: {}, {}", status, id);

        employeeService.startOrStopEmployee(status, id);

        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("editPassword")
    @ApiOperation("修改账户密码相关接口")
    public Result editPassword(@RequestBody EditPasswordDTO editPasswordDTO) {
        //程序有bug 手动赋值 用户id
        editPasswordDTO.setEmpId(BaseContext.getCurrentId());

        log.info("修改账户密码: {}", editPasswordDTO);

        employeeService.editPassword(editPasswordDTO);

        return Result.success();
    }

    /**
     * 根据id查询员工信息
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result<Employee> queryEmployeeInfoById(@PathVariable Long id) {
        log.info("根据id查询员工信息: {}", id);

        Employee employee = employeeService.queryEmployeeInfoById(id);

        return Result.success(employee);
    }

    /**
     * 编辑员工信息
     */
    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result editEmployeeInfo(@RequestBody EmployeeEditInfoDTO employeeEditInfoDTO) {
        log.info("编辑员工信息: {}", employeeEditInfoDTO);

        employeeService.editEmployeeInfo(employeeEditInfoDTO);

        return Result.success();
    }
}
