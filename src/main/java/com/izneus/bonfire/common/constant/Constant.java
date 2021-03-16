package com.izneus.bonfire.common.constant;

/**
 * 系统的缺省常量
 *
 * @author Izneus
 * @date 2020/07/06
 */
public class Constant {

    /**
     * 验证码的redis key，{}为captchaId
     */
    public static final String REDIS_KEY_CAPTCHA = "captcha:{}";

    /**
     * 登陆重试的redis key，重试一般指的是密码错误，{}为用户名，实际存储的key形如 username:userName:retry
     */
    public static final String REDIS_KEY_LOGIN_RETRY = "username:{}:retry";

    /**
     * 用户权限的redis key，{}为userId，实际存储的key形如 user:userId:auths
     */
    public static final String REDIS_KEY_AUTHS = "user:{}:auths";

    /**
     * 黑名单的redis key，黑名单用来实现登出、服务端锁定账号等，{}为jwt
     */
    public static final String REDIS_KEY_BLACKLIST = "blacklist:{}";

    /**
     * 最大密码错误重试次数
     */
    public static final int MAX_RETRY_COUNT = 5;

    public static final String JOB_KEY = "job";

    /**
     * 临时文件
     */
    public static final String TEMP_FILE = "0";

    /**
     * 已经上传的文件
     */
    public static final String UPLOAD_FILE = "1";


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
