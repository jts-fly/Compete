package com.compete.util;

public class StringUtil {

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String number) {
        boolean isNumber = false;
        if (StringUtil.isNotEmpty(number)) {
            isNumber = number.matches("^([1-9]\\d*)|(0)$");
        }
        return isNumber;
    }

    /**
     * 判断字符串不为空
     */
    public static boolean isNotEmpty(String str) {
        boolean isNotEmpty = false;
        if (str != null && !"".equals(str.trim()) && !str.trim().equalsIgnoreCase("NULL")) {
            isNotEmpty = true;
        }
        return isNotEmpty;
    }

    /**
     * 判断字符串为空
     */
    public static boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }

    /**
     * 将数组转成SQL认识的字符串 123,432,2312 id in('123','432','2312')
     */
    public static String fromArrayToStr(String[] ids) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            str.append("'" + ids[i] + "',");
        }
        if (ids.length > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

}
