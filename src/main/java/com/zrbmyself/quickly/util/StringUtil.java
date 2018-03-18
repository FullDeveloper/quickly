package com.zrbmyself.quickly.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Author : 周润斌
 * Date : create in 上午 17:13 2018/3/16 0016
 * Description :
 */
public class StringUtil {

    public static boolean isNotEmpty(String strValue) {

        return !isEmpty(strValue);
    }

    public static boolean isEmpty(String str){
        if(str != null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }
    /**
     * 分割固定格式的字符串
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);

    }
}
