package com.hxl.service.impl;

import com.hxl.EmployeeAddDTO;
import com.hxl.constant.JwtClaimsConstant;
import com.hxl.constant.MessageConstant;
import com.hxl.constant.PasswordConstant;
import com.hxl.constant.StatusConstant;
import com.hxl.context.BaseContext;
import com.hxl.entity.Employee;
import com.hxl.exception.AccountNotFoundException;
import com.hxl.exception.PasswordErrorException;
import com.hxl.mapper.EmployeeMapper;
import com.hxl.properties.JwtProperties;
import com.hxl.service.EmployeeService;
import com.hxl.utils.JwtUtil;
import com.hxl.vo.EmployeeLoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
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
        Employee condition = Employee.builder().username(username).build();
        Employee employeeInfo = employeeMapper.dynamicQuerySingleEmployee(condition);

        //3.判断是否存在该用户
        if (employeeInfo == null){
            //抛出自定义的 账户不存在 异常信息
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //4.用户存在 比对密码是否一致
        String submitPassword = employee.getPassword();//提交的明文密码
        String queryPassword = employeeInfo.getPassword();//查询的密文密码

        //将明文密码转换成密文
        String newPassword = DigestUtils.md5DigestAsHex(submitPassword.getBytes());

        //密文密码不一致
        if (!newPassword.equals(queryPassword)){
            //抛出自定义异常 密码错误 异常信息
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
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
}
