package com.hxl.service.impl;

import com.hxl.constant.JwtClaimsConstant;
import com.hxl.constant.MessageConstant;
import com.hxl.entity.Employee;
import com.hxl.exception.AccountNotFoundException;
import com.hxl.exception.PasswordErrorException;
import com.hxl.mapper.EmployeeMapper;
import com.hxl.properties.JwtProperties;
import com.hxl.service.EmployeeService;
import com.hxl.utils.JwtUtil;
import com.hxl.vo.EmployeeLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
}
