package com.zrbmyself.quickly.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/17 0017
 * Time: 23:29
 * Description:
 */
public class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        begin();
        try{
            //如果返回true代表拦截
            if(interceptor(cls,method,params)){
                before(cls,method,params);
                result = proxyChain.doProxyChain();
                after(cls,method,params);
            }else{
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            LOGGER.error("proxy failure",e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    public void begin() {
        System.out.println("begin");
    }

    public boolean interceptor(Class<?> cls, Method method, Object[] params) {
        System.out.println("interceptor");
        return true;
    }

    public void before(Class<?> cls, Method method, Object params) {
        System.out.println("before");
    }

    public void after(Class<?> cls, Method method, Object params) {
        System.out.println("after");
    }

    public void error(Class<?> cls, Method method, Object params) {

    }

    public void end() {

    }

}
