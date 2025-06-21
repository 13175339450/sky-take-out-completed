package com.hxl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hxl.dto.EmployeeEditInfoDTO;
import com.hxl.dto.EditPasswordDTO;
import com.hxl.dto.EmployeeAddDTO;
import com.hxl.constant.JwtClaimsConstant;
import com.hxl.constant.MessageConstant;
import com.hxl.constant.PasswordConstant;
import com.hxl.constant.StatusConstant;
import com.hxl.dto.EmployeePageDTO;
import com.hxl.entity.Employee;
import com.hxl.exception.AccountLockStatusException;
import com.hxl.exception.AccountNotFoundException;
import com.hxl.exception.PasswordErrorException;
import com.hxl.mapper.EmployeeMapper;
import com.hxl.properties.JwtProperties;
import com.hxl.result.PageResult;
import com.hxl.service.EmployeeService;
import com.hxl.utils.JwtUtil;
import com.hxl.vo.EmployeeLoginVO;
import com.hxl.vo.EmployeePageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service //手动将类标记为 Spring Bean
public class EmployeeServiceImpl implements EmployeeService {

    //注入接口的代理实现类
    @Autowired
    private EmployeeMapper employeeMapper;
    //注入Jwt校验的代理实现类
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 员工登录功能实现
     */
    @Override
    public EmployeeLoginVO employeeLogin(Employee employee) {
        //1.获取前端传入的 username 去数据库里查找 该用户是否存在
        String username = employee.getUsername();

        //2.使用动态查询语句：用用户名来查找 单个 员工信息
        Employee emp = Employee.builder().username(username).build();
        Employee employeeInfo = employeeMapper.dynamicQuerySingleEmployee(emp);

        //3.判断是否存在该用户
        if (employeeInfo == null) {
            //抛出自定义的 账户不存在 异常信息
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //4.用户存在 比对密码是否一致
        String submitPassword = employee.getPassword();//提交的明文密码
        String queryPassword = employeeInfo.getPassword();//查询的密文密码

        //将明文密码转换成密文
        String newPassword = DigestUtils.md5DigestAsHex(submitPassword.getBytes());

        //密文密码不一致
        if (!newPassword.equals(queryPassword)) {
            //抛出自定义异常 密码错误 异常信息
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //TODO: 修复 被禁用的账号不能登录
        if (!employeeInfo.getStatus().equals(StatusConstant.EMP_DEFAULT_STATUS)) {
            throw new AccountLockStatusException(MessageConstant.ACCOUNT_LOCK_STATUS_ERROR);
        }

        //创建token: 根据员工id值 + 加密密钥 + Token有效时间 组合创建
        Map claims = new HashMap();

        //进行信息封装
        claims.put(JwtClaimsConstant.EMP_ID, employeeInfo.getId());

        //进行token生成 调用工具类的静态方法进行创建
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        //结果返回
        return EmployeeLoginVO.builder()
                .id(employeeInfo.getId())
                .name(employeeInfo.getName())
                .userName(employee.getUsername())
                .token(token).build();
    }

    /**
     * 新增员工功能实现
     * TODO:因为在新增员工时 要同时插入 create_time 和 create_user等信息
     *      采用 AOP 切片编程
     */
    @Override
    public void addEmployee(EmployeeAddDTO employeeAddDTO) {
        // 存储员工信息的实体类
        Employee employee = new Employee();

        //1.属性拷贝 将填写的用户信息拷贝
        BeanUtils.copyProperties(employeeAddDTO, employee);

        //2.对部分信息进行补充 初始密码、账户状态、创建时间、更新时间、更新人、创建人
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(StatusConstant.EMP_DEFAULT_STATUS);

        //已用共有AutoFill进行自动填充
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        //3.执行新增操作 如果该username已存在 会插入失败 利用全局异常处理器
        int row = employeeMapper.insertEmployee(employee);
    }


    /**
     * 员工分页查询
     */
    @Override
    public PageResult employeePage(EmployeePageDTO employeePageDTO) {
        //1.设置分页数据 当前页 页容量
        PageHelper.startPage(employeePageDTO.getPage(), employeePageDTO.getPageSize());

        //2.封装查询参数 name可以为空
        Employee employee = Employee.builder().name(employeePageDTO.getName()).build();

        //3.按照分页形式 去动态查询分页数据 用视图VO来接收
        Page<EmployeePageVO> page = employeeMapper.employeeDynamicPage(employee);

        //4.获取相关数据
        long total = page.getTotal();
        List<EmployeePageVO> records = page.getResult();

        //5.结果封装 并返回
        return new PageResult(total, records);
    }

    /**
     * 启用、禁用员工
     */
    @Override
    public void startOrStopEmployee(Integer status, Long id) {
        //构建对象
        Employee employee = Employee.builder().id(id).status(status).build();

        //此处用动态sql 声明一个动态的 通用的update方法
        int row = employeeMapper.updateEmployeeInfo(employee);
    }

    /**
     * 修改账户密码
     *
     * @param editPasswordDTO 员工提交新密码的封装DTO类
     */
    @Override
    public void editPassword(EditPasswordDTO editPasswordDTO) {
        //封装实体类
        Employee employee = Employee.builder().id(editPasswordDTO.getEmpId()).build();

        //查询当前账户的相关数据
        Employee employeeInfo = employeeMapper.dynamicQuerySingleEmployee(employee);

        //2.判断旧密码是否正确
        String oldPassword = editPasswordDTO.getOldPassword();
        if (!DigestUtils.md5DigestAsHex(oldPassword.getBytes()).equals(employeeInfo.getPassword())) {
            //旧密码有误
            throw new PasswordErrorException(MessageConstant.OLD_PASSWORD_ERROR);
        }

        //3.将密码进行更新 需要转换成密文
        String newPassword = editPasswordDTO.getNewPassword();
        employee.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));

        //4.调用通用update方法
        employeeMapper.updateEmployeeInfo(employee);
    }

    /**
     * 根据id查询员工信息
     */
    @Override
    public Employee queryEmployeeInfoById(Long id) {
        //封装实体类
        Employee condition = Employee.builder().id(id).build();

        //调用通用查询方法
        Employee employee = employeeMapper.dynamicQuerySingleEmployee(condition);

        //将密码进行隐藏
        employee.setPassword("******");

        return employee;
    }

    /**
     * 编辑员工信息
     */
    @Override
    public void editEmployeeInfo(EmployeeEditInfoDTO employeeEditInfoDTO) {
        Employee employee = new Employee();
        //属性拷贝
        BeanUtils.copyProperties(employeeEditInfoDTO, employee);

        //调用通用的更新方法
        employeeMapper.updateEmployeeInfo(employee);
    }
}
