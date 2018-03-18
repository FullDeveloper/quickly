package com.zrbmyself.quickly.helper;

import com.zrbmyself.quickly.annotation.Autowired;
import com.zrbmyself.quickly.util.ArrayUtil;
import com.zrbmyself.quickly.util.CollectionUtil;
import com.zrbmyself.quickly.util.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/17 0017
 * Time: 20:46
 * Description: 依赖注入助手类
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            //遍历bean map
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                //从map中拿到class和bean的实例
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();
                //拿到bean中所有定义的成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    //遍历所有的字段
                    for (Field field : beanFields) {
                        //查看字段上是否拥有Autowired注解
                        if (field.isAnnotationPresent(Autowired.class)) {
                            //在Bean Map中获取bean field对应的实例
                            Class<?> beanFieldClass = field.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }


}
