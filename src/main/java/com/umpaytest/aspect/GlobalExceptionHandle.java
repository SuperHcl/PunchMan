package com.umpaytest.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 异常处理器
 *
 * @author Hu.ChangLiang
 * @date 2022/5/26 14:30
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {


    @ExceptionHandler(Exception.class)
    public Map<String, Object> exceptionHandler(Exception e) {
        log.warn("异常==", e);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", e.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object exceptionHandler(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.warn("接口数据验证异常:[{}]", errorMessages);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", errorMessages);
        return map;
    }
}
