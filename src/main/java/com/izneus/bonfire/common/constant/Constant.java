package com.izneus.bonfire.common.constant;

/**
 * 系统的缺省常量
 *
 * @author Izneus
 * @date 2020-07-06
 */
public class Constant {

    private static final String PROJECT_NAME = "bf";

    /**
     * redis key 相关
     */
    public static class RedisKey {
        /**
         * 登录界面的验证码，{}为captchaId
         */
        public static final String CAPTCHA = PROJECT_NAME + ":captcha:{}";

        /**
         * 登陆重试次数，重试一般指的是密码错误，{}为用户名，实际存储的key形如 bf:login:xxx:retryCount
         */
        public static final String RETRY_COUNT = PROJECT_NAME + ":login:{}:retryCount";

        /**
         * 用户权限，{}为userId，实际存储的key形如 bf:userId:xxx:privilege
         */
        public static final String PRIVILEGE = PROJECT_NAME + ":userId:{}:privilege";

        /**
         * 黑名单，黑名单用来实现登出、服务端锁定账号等，{}为jwt
         */
        public static final String BLACKLIST = PROJECT_NAME + ":blacklist:{}";

        /**
         * 钉钉access_token，钉钉的token针对某一应用，有需要可以添加应用区分的标志
         */
        public static final String DINGTALK_ACCESS_TOKEN = PROJECT_NAME + ":dd:accessToken";
    }

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

    public static final String READ = "0";
    public static final String UNREAD = "1";

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
