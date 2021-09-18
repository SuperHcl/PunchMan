package com.umpaytest.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author hucl
 * @date 2021/9/18 2:58 下午
 */
@Component
@PropertySource(value = {"classpath:user.properties"})
@ConfigurationProperties(prefix = "demo")
public class ReadByPropertySourceAndConfProperties {

    private String name;

    private Integer age;

    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ReadByPropertySourceAndConfProperties{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

}
