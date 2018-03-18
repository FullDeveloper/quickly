package com.zrbmyself.quickly.proxy;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/17 0017
 * Time: 23:07
 * Description: 代理接口
 *  链式代理: 可以将多个代理通过一条链子串起来,一个一个去执行,执行顺序取决于添加到链中的先后顺序
 */
public interface Proxy {
    /**
     * 执行链式代理
     * @param proxyChain
     * @return
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
