package com.umpaytest.property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * PropertySource的使用
 * 作用：加载指定的外部属性文件（.properties）到Spring的Environment中
 * - 可以配合@Value和@ConfigurationProperties使用
 * - @PropertySource和@Value组合使用，可以将自定义属性文件中的属性变量值注入到当前类的使用@Value注解的成员变量中。
 * - @PropertySource 和 @ConfigurationProperties
 * 组合使用，可以将属性文件与一个Java类绑定，将属性文件中的变量值注入到该Java类的成员变量中。
 *
 * @author hucl
 * @date 2021/9/18 2:20 下午
 */
@Component
@PropertySource(value = {"classpath:user.properties"})
public class PropertySourceValueDemo {

    @Value("${demo.name}")
    private String name;

    @Value("${demo.age}")
    private Integer age;

    @Value("${demo.gender}")
    private String gender;

    @Override
    public String toString() {
        return "PropertySourceValueDemo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }


}
