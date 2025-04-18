package com.umpaytest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hu.ChangLiang
 * @date 2024/3/20 11:34
 */
@Data
public class Student {
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotNull(message = "挂账金额不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "挂账金额必须大于0")
    private BigDecimal amount;

    @NotBlank(message = "供货仓编码")
    @Size(max = 20, message = "供货仓编码不能超过200个字符")
    private String storeCode;
}
