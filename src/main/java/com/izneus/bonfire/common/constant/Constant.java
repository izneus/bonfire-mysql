package com.izneus.bonfire.common.constant;

/**
 * 系统的缺省常量
 *
 * @author Izneus
 * @date 2020/07/06
 */
public class Constant {

    /**
     * 验证码的redis key前缀
     */
    public static final String REDIS_KEY_CAPTCHA = "captcha:";

    /**
     * 登陆重试的redis key前缀，重试一般指的是密码错误
     */
    public static final String REDIS_KEY_LOGIN_RETRY = "login-retry:user:";

    public static final String REDIS_KEY_AUTHORITIES = "captcha:";

    /**
     * 最大密码错误重试次数
     */
    public static final int MAX_RETRY_COUNT = 5;

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

    public static final String NOTICE_TYPE_GLOBAL = "0";
    public static final String NOTICE_TYPE_PM = "1";

}
