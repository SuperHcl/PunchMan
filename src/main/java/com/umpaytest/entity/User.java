package com.umpaytest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * @author: Hucl
 * @date: 2019/3/26 10:04
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)    // 链式编程
public class User {
    @NotNull(message = "id not null")
    private Integer id;

    @Size(max = 20)
    private String name;

    @Max(150)
    @Min(1)
    private int age;

    @NotEmpty(message = "地址不能为空")
    private String address;

    @NotNull(message = "monty not empty")
    private Integer money;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")
    private String email;

    private List<Integer> ossServerId;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
