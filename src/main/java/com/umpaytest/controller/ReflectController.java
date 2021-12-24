package com.umpaytest.controller;

import com.umpaytest.springProxy.reflect.ReflectService;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author Hu.ChangLiang
 * @date 2021/12/23 4:30 下午
 */
@RestController
@RequestMapping("/reflect")
public class ReflectController {
    @Resource
    private ReflectService reflectService;

    @PostMapping(value = "/{methodName}")
    public Object handle(@PathVariable(value = "methodName") String methodName, @RequestBody String parameter) throws InvocationTargetException, IllegalAccessException {
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request.getRequestURI());

        JSONArray arrays = JSONArray.fromObject(parameter);
        Object[] args = new Object[arrays.size()];
        for(int i = 0; i < arrays.size(); ++i) {
            if (!(arrays.get(i) instanceof JSONObject) || !((JSONObject)arrays.get(i)).isNullObject()) {
                args[i] = arrays.get(i);
            }
        }

        Class<? extends ReflectService> aClass = reflectService.getClass();
        Method[] methods = aClass.getMethods();

        Method method = loopMethods(methodName, methods, args);
        if (method != null) {
            return method.invoke(reflectService, args);
        }
        return null;
    }

    private Method loopMethods(String methodName, Method[] methods, Object[] args) {
        Method em = null;
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                Type[] types = m.getGenericParameterTypes();
                if (args != null && args.length != 0) {
                    if (types.length == args.length) {
                        boolean mf = this.loopTypes(args, types);
                        if (mf) {
                            em = m;
                            break;
                        }
                    }
                } else if (types.length == 0) {
                    em = m;
                    break;
                }
            }
        }

        return em;
    }

    private boolean loopTypes(Object[] args, Type[] types) {
        boolean mf = true;

        for(int i = 0; i < types.length; ++i) {
            Object o = args[i];
            if (o instanceof JSONNull) {
                args[i] = null;
            } else if (o != null && this.checkTypes(types[i], o)) {
                try {
                    if (!((ParameterizedTypeImpl)types[i]).getRawType().isInstance(o)) {
                        mf = false;
                        break;
                    }
                } catch (Exception var7) {
                    mf = false;
                    break;
                }
            }
        }

        return mf;
    }

    private boolean checkTypes(Type type, Object o) {
        return !type.toString().equals(o.getClass().toString()) && !this.checkInteger(type, o) && !this.checkBoolean(type, o) && !this.checkCharacter(type, o) && !this.checkByte(type, o) && !this.checkLong(type, o) && !this.checkFloat(type, o) && !this.checkShort(type, o) && !this.checkDouble(type, o);
    }


    private boolean checkDouble(Type type, Object o) {
        return "double".equals(type.toString()) && Double.class.getCanonicalName().equals(o.getClass().getCanonicalName());
    }

    private boolean checkShort(Type type, Object o) {
        return "short".equals(type.toString()) && Short.class.getCanonicalName().equals(o.getClass().getCanonicalName());
    }

    private boolean checkFloat(Type type, Object o) {
        return "float".equals(type.toString()) && Float.class.getCanonicalName().equals(o.getClass().getCanonicalName());
    }

    private boolean checkLong(Type type, Object o) {
        return "long".equals(type.toString()) && Long.class.getCanonicalName().equals(o.getClass().getCanonicalName());
    }

    private boolean checkByte(Type type, Object o) {
        return "byte".equals(type.toString()) && Byte.class.getCanonicalName().equals(o.getClass().getCanonicalName());
    }

    private boolean checkCharacter(Type type, Object o) {
        return "char".equals(type.toString()) && Character.class.getCanonicalName().equals(o.getClass().getCanonicalName());
    }

    private boolean checkBoolean(Type type, Object o) {
        return "boolean".equals(type.toString()) && Boolean.class.getCanonicalName().equals(o.getClass().getCanonicalName());
    }

    private boolean checkInteger(Type type, Object o) {
        return "int".equals(type.toString()) && Integer.class.getCanonicalName().equals(o.getClass().getCanonicalName());
    }


}
