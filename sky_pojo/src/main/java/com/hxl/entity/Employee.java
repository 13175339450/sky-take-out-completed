package com.hxl.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
/*
| id          | bigint      | NO   | PRI | NULL    | auto_increment |
| name        | varchar(32) | NO   |     | NULL    |                |
| username    | varchar(32) | NO   | UNI | NULL    |                |
| password    | varchar(64) | NO   |     | NULL    |                |
| phone       | varchar(11) | NO   |     | NULL    |                |
| sex         | varchar(2)  | NO   |     | NULL    |                |
| id_number   | varchar(18) | NO   |     | NULL    |                |
| status      | int         | NO   |     | 1       |                |
| create_time | datetime    | YES  |     | NULL    |                |
| update_time | datetime    | YES  |     | NULL    |                |
| create_user | bigint      | YES  |     | NULL    |                |
| update_user | bigint      | YES  |     | NULL    |                |
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/* 实现了Serializable接口，它具备序列化的能力。
 * 所谓序列化，就是把对象的状态转变为字节流，方便进行存储（例如写入文件）或者传输（例如通过网络发送）
 * 而反序列化则是相反的过程，能将字节流重新恢复成对象。
 */
@ApiModel("员工表相对应的实体类")
public class Employee implements Serializable {

    /* 手动设定一个固定的serialVersionUID（例如示例中的1L），能够确保即便类的结构有所改变，
     * 反序列化过程依旧可以进行。不过，这种情况下可能会丢失一些数据，或者使用字段的默认值。
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("员工id")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("身份证")
    private String idNumber;

    @ApiModelProperty("账号状态")
    private Integer status;

    /*
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        也可以在WebMvcConfig里配置消息转化器
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建者id")
    private Long createUser;

    @ApiModelProperty("更新者id")
    private Long updateUser;
}
