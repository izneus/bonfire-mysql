package com.izneus.bonfire.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 字典类常量，数据库字典表中的字典，如果出现在业务逻辑判断里，补充在该类中
 *
 * @author Izneus
 * @date 2020/07/06
 */
public class Dict {

    /**
     * 用户状态
     */
    @RequiredArgsConstructor
    @Getter
    public enum UserStatus {
        /**
         * 正常
         */
        OK("0", "正常"),

        /**
         * 锁定
         */
        LOCK("1", "已锁定");

        private final String code;
        private final String name;
    }

    /**
     * 字典状态
     */
    @RequiredArgsConstructor
    @Getter
    public enum DictStatus {
        /**
         * 正常
         */
        OK("0", "正常"),

        /**
         * 禁用
         */
        DISABLE("1", "已禁用");

        private final String code;
        private final String name;
    }

    @RequiredArgsConstructor
    @Getter
    public enum JobStatus {
        /**
         * 正常
         */
        OK("0"),

        /**
         * 暂停
         */
        PAUSE("1");

        private final String value;
    }

}
