package com.xiaodai.customize.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaodai.customize.annotation.JsonAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

/**
 * 解析json切面实现类
 * @author My
 */
@Component
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class JsonToJsonAop {

    public static HashMap safeMap = new HashMap();


    /**
     * 切点
     */
    @Pointcut("@annotation(com.xiaodai.customize.annotation.JsonAnnotation)")
    public void pointCut() {

    }

    /**
     * json转换实体对象
     * @param joinPoint
     * @param around
     * @throws Throwable
     */
    @Before(value = "@annotation(around)")
    public void json2Bean(JoinPoint joinPoint, JsonAnnotation around) throws Throwable {
        /*Class cls = joinPoint.getSignature().getDeclaringType();
        Class willCla = around.toBean();
        System.out.println("获得签名=" + cls);*/
        //获取要转换的类型
        Class willCla = around.toBean();
        //获取方法的参数
        Object  objects = joinPoint.getArgs()[0];
        //转换为jsonObjct类型
        JSONObject jsonObject = JSON.parseObject((String) objects);
        //处理转换
        Object result =  jsonToObject(willCla, jsonObject);
        //放入缓存中 或者放入容器启动后执行
        //System.out.println("输出结果" + JSONObject.toJSONString(result));
        //todo 获取解析结果
    }

    /**
     * 处理转换方法
     * @param clazz  转换的目标
     * @param source  被转换的
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public  <T> T jsonToObject(Class<T> clazz, JSONObject source) throws IllegalAccessException, InstantiationException {
        //目标对象
        T result = clazz.newInstance();
        //目标方法
        Field[] fields = clazz.getDeclaredFields();
        //目标方法集合
        HashMap<String, Field> methodMap = new HashMap<String, Field>(16);
        for (Field field : fields) {
            field.setAccessible(true);
            methodMap.put(field.getName(), field);
        }
        //遍历JSONObject 对象
        for (String key : source.keySet()) {
            if (methodMap.containsKey(key)) {
                //对目标对象赋值
                SafeMap.setKey(key);
                setProperty(result, methodMap.get(key), source.get(key));
                SafeMap.getKey(key);
            }
        }
        return result;
    }

    /**
     * 向目标对象赋值
     *
     * @param obj
     * @param field
     * @param setValue
     * @param <T>
     */
    private  synchronized <T>  void setProperty(T obj, Field field, Object setValue) {
        String setValueString = setValue.toString();
        Class<?> clazz = obj.getClass();
        Method method = null;
        try {
            //拼接set方法
            method = clazz.getDeclaredMethod("set"
                            + field.getName().substring(0, 1).toUpperCase(Locale.getDefault())
                            + field.getName().substring(1),
                    field.getType());
            //设置set方法可访问
            method.setAccessible(true);
            //方法参数类型
            Class<?> type = method.getParameterTypes()[0];
            if (type.getName().equals("int")) {
                method.invoke(obj, Integer.parseInt(setValueString));
            }
            if (type.getName().equals("boolean") && isBoolean(setValueString)) {
                method.invoke(obj, (setValueString));
            }
            if (type.getName().equals("long") && isDate(setValueString)) {
                method.invoke(obj, Long.parseLong(setValueString));
            }
            if (type == String.class) {
                method.invoke(obj, setValueString);
            }
            //调用set方法为类的实例赋值
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测boolean
     * @param setValueString
     * @return
     */
    private boolean isBoolean(String setValueString) {
        if ("true".equals(setValueString) || "false".equals(setValueString)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是date
     * @param setValue
     * @return
     */
    public boolean isDate(String setValue) {
        boolean isDate = false;
        SimpleDateFormat sdf=  new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        try {
            sdf.format(setValue);
            isDate = true;
        } catch (Exception e) {
            e.printStackTrace();
            isDate = false;
        }
        return isDate;
    }




}
