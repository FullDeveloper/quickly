package com.zrbmyself.quickly.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Author : 周润斌
 * Date : create in 上午 16:38 2018/3/16 0016
 * Description : 属性文件工具类
 */
public class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);


    /**
     * 根据文件名读取配置文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadPropsFile(String fileName) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " not found !");
            }
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            logger.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("close input stream failure", e);
                }
            }
        }
        return properties;
    }

    /**
     * 根据key来获取配置文件中的value 默认值为""
     *
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    public static int getInt(Properties props, String key) {

        return 0;
    }

    public static boolean getBoolean(Properties props, String key) {

        return false;
    }

}
