package com.zrbmyself.quickly.helper;

import com.zrbmyself.quickly.annotation.RequestMapping;
import com.zrbmyself.quickly.annotation.RequestMethod;
import com.zrbmyself.quickly.bean.Handler;
import com.zrbmyself.quickly.bean.Request;
import com.zrbmyself.quickly.util.ArrayUtil;
import com.zrbmyself.quickly.util.ClassUtil;
import com.zrbmyself.quickly.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/17 0017
 * Time: 21:04
 * Description:
 */
public class ControllerHelper {

    private static final Map<Request, Handler> REQUEST_MAP = new HashMap<>();

    static {
        //获取所有的Controller类
        Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerSet)) {
            for (Class<?> controller : controllerSet) {
                //获取controller中的方法定义
                Method[] methods = controller.getMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    //遍历方法数组
                    for (Method method : methods) {
                        //判断方法当前是否带有requestMapping注解
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            //获取请求方法
                            String mappingUrl = requestMapping.value();
                            RequestMethod[] requestMethods = requestMapping.method();
                            if (ArrayUtil.isNotEmpty(requestMethods)) {
                                for (RequestMethod requestMethod : requestMethods) {
                                    String requestMethodName = requestMethod.name();
                                    Request request = new Request(requestMethodName, mappingUrl);
                                    Handler handler = new Handler(method, controller);
                                    REQUEST_MAP.put(request, handler);
                                }
                            } else {
                                //默认可以进行get和post请求
                                Request requestGet = new Request(mappingUrl, RequestMethod.GET.name());
                                Request requestPost = new Request(mappingUrl, RequestMethod.POST.name());
                                Handler handler = new Handler(method, controller);
                                REQUEST_MAP.put(requestGet, handler);
                                REQUEST_MAP.put(requestPost, handler);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据请求方式和请求路径获得相对应的处理器
     *
     * @param requestMethod 请求方式
     * @param requestPath   请求路径
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return REQUEST_MAP.get(request);
    }

}
