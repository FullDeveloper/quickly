package com.zrbmyself.quickly.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/17 0017
 * Time: 19:26
 * Description: 类加载器
 */
public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 根据类名来加载类
     *
     * @param className
     * @param isInitialized 是否需要初始化 为了提高类的加载性能 可以将其设置为false
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class {0} failure", className);
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Class<?> loadClass(String className){
        return loadClass(className,false);
    }

    /**
     * 根据包名加载该包下的所有类.需要根据包名并将其转化成文件路径,读取class文件或者jar包,获取指定的类名去加载类.
     * 加载当前类加载器以及父类加载器所在路径的资源文件.将遇到的所有资源文件全部返回！
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urlEnumeration = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath();
                        System.out.println(packagePath);
                        addClass(classSet, packagePath, packageName);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry entry = jarEntries.nextElement();
                                    String jarEntryName = entry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        System.out.println("jarName:" + jarEntryName);
                                        String className = jarEntryName.substring(0, jarEntryName.indexOf(".")).replaceAll("/", ".");
                                        System.out.println("className:" + className);
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("get class set failure", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            System.out.println(file.getName());
            if (file.isFile()) {
                System.out.println(file.getName());
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                System.out.println(className);
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                System.out.println(className);
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(subPackagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }


    public static void main(String[] args) {
        getClassSet("com.zrbmyself.quickly");
    }

}
