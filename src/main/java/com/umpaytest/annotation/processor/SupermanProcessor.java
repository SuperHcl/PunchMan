package com.umpaytest.annotation.processor;

import com.umpaytest.annotation.Superman;
import com.umpaytest.entity.SupermanBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/3 18:06
 * @description: @Superman注解的处理
 */
@Component
public class SupermanProcessor {

    public List<SupermanBean> process(HttpServletRequest request) {
        //
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        // 扫描所有的controller中的@RequestMapping注解，放进map中
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();

        List<SupermanBean> supermanBeans = new ArrayList<>(map.size());
        Map<String, SupermanBean> supermanBeanMap = new HashMap<>();
        map.forEach((requestMappingInfo, handlerMethod) -> {
            SupermanBean supermanBean = parseSupermanBean(requestMappingInfo, handlerMethod);
            if (supermanBean != null) {
                supermanBeans.add(supermanBean);
            }
        });
//        supermanBeans.stream().forEach(supermanBean -> {
//            System.out.println(supermanBean.toString());
//        });
        return supermanBeans;
    }

    private SupermanBean parseSupermanBean(RequestMappingInfo requestMappingInfo, HandlerMethod handlerMethod) {
        Superman superman = handlerMethod.getMethodAnnotation(Superman.class);
        if (superman == null) {
            return null;
        }
        return getSupermanBean(requestMappingInfo, superman);
    }

    private SupermanBean getSupermanBean(RequestMappingInfo requestMappingInfo, Superman superman) {
        String apiUrl = requestMappingInfo.getPatternsCondition().toString();
        String requestMethodName = requestMappingInfo.getMethodsCondition().toString();
        String name = superman.methodName();
        return new SupermanBean().setApiUrl(apiUrl).setName(name).setRequestMethodName(requestMethodName);
    }
}
