package com.hxl.entity;

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
public class Employee implements Serializable {

    /* 手动设定一个固定的serialVersionUID（例如示例中的1L），能够确保即便类的结构有所改变，
     * 反序列化过程依旧可以进行。不过，这种情况下可能会丢失一些数据，或者使用字段的默认值。
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    private Integer status;

    /*
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        也可以在WebMvcConfig里配置消息转化器
     */
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
