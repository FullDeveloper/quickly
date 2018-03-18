package com.zrbmyself.quickly.bean;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/17 0017
 * Time: 21:02
 * Description:
 */
public class Handler {

    /**
     * controller请求方法
     */
    private Method requestMethod;

    /**
     * controller类
     */
    private Class<?> controllerClass;

    public Handler(Method requestMethod, Class<?> controllerClass) {
        this.requestMethod = requestMethod;
        this.controllerClass = controllerClass;
    }

    public Method getRequestMethod() {
        return requestMethod;
    }


    public Class<?> getControllerClass() {
        return controllerClass;
    }


}
