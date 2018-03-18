package com.zrbmyself.quickly.helper;

import com.zrbmyself.quickly.annotation.Controller;
import com.zrbmyself.quickly.annotation.Service;
import com.zrbmyself.quickly.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/17 0017
 * Time: 20:17
 * Description:
 */
public class ClassHelper {

    /**
     * 定义类集合(用于存放所有加载的类)
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String baePackagePath = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(baePackagePath);
    }

    /**
     * 获取应用包名下某父类(接口)的所有子类(或者实现类)
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> cls : CLASS_SET){
            //是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口
            //判断cls是否是superClass的子类或者同类
            if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> cls : CLASS_SET){
            if(cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有的类
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取所有service类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> serviceSet = new HashSet<>();
        for(Class<?> cls : CLASS_SET){
            if(cls.isAnnotationPresent(Service.class));
            serviceSet.add(cls);
        }
        return serviceSet;
    }

    /**
     * 获取所有controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> controllerSet = new HashSet<>();
        for(Class<?> cls : CLASS_SET){
            if(cls.isAnnotationPresent(Controller.class));
            controllerSet.add(cls);
        }
        return controllerSet;
    }

    /**
     * 获取所有应用名下所有的Bean类(包括:Service Controller等)
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }

}
