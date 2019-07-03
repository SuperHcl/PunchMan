package com.umpaytest.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

/**
 * @author: Hucl
 * @date: 2019/3/26 10:04
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)    // 链式编程
@ApiModel("base user information")
public class User {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("姓名")
    @Size(max = 20)
    private String name;

    @ApiModelProperty("年龄")
    @Max(150)
    @Min(1)
    private Integer age;

    @ApiModelProperty("地址")
    @NotNull
    private String address;

    @ApiModelProperty("邮箱")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")
    private String email;

//    public User(String name, Integer age, String address, String email) {
//        this.name = name;
//        this.age = age;
//        this.address = address;
//        this.email = email;
//    }
}
