package com.izneus.bonfire.common.constant;

/**
 * 常用正则表达式
 *
 * @author Izneus
 * @date 2020/06/29
 */
public class RegExp {
    /**
     * 18位身份证号码
     */
    public static final String ID_CARD = "([1-9]\\d{5}[12]\\d{3}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])\\d{3}[0-9xX])";

    /**
     * 密码强度，必须包含小写字母、大写字母、数字，长度为8～16
     */
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$";
}
