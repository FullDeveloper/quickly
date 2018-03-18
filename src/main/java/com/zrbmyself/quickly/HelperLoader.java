package com.zrbmyself.quickly;

import com.zrbmyself.quickly.helper.*;
import com.zrbmyself.quickly.util.ClassUtil;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/17 0017
 * Time: 21:23
 * Description: 初始化框架
 */
public class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls: classList){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }

}
