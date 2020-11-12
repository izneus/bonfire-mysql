package com.izneus.bonfire.common.constant;

/**
 * 系统的缺省常量
 *
 * @author Izneus
 * @date 2020/07/06
 */
public class Constant {
    public static final String REDIS_KEY_TYPE_CAPTCHA = "captcha:";
    public static final String REDIS_KEY_TYPE_AUTHORITIES = "captcha:";

    public static final int MAX_PASSWORD_RETRY_COUNT = 5;

    public static final String JOB_KEY = "job";

    /**
     * 所有状态的成功、失败常量
     */
    public static final String SUCCESS = "0";
    public static final String FAIL = "1";

    /**
     * 所有的是否常量
     */
    public static final String YES = "1";
    public static final String NO = "0";

}
