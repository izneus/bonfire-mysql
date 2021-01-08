package com.izneus.bonfire.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 字典类常量，都为枚举类型，数据库字典表中的字典，如果出现在业务逻辑判断里，补充在该类中
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

    /**
     * 调度任务状态
     */
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

    /**
     * 通知类型
     */
    @RequiredArgsConstructor
    @Getter
    public enum NoticeType {
        /**
         * 全局公告
         */
        GLOBAL("0"),

        /**
         * 对用户(组)的消息
         */
        PM("1");

        private final String value;
    }

    /**
     * 用户通知状态
     */
    @RequiredArgsConstructor
    @Getter
    public enum UserNoticeStatus {
        /**
         * 未读
         */
        UNREAD("0"),

        /**
         * 已读
         */
        READ("1"),

        /**
         * 删除
         */
        DELETE("2");

        private final String value;
    }

    /**
     * 工单状态
     */
    @RequiredArgsConstructor
    @Getter
    public enum TicketStatus {
        /**
         * 待处理
         */
        PENDING("0"),

        /**
         * 处理中
         */
        PROCESSING("1"),

        /**
         * 已解决
         */
        SOLVED("2"),

        /**
         * 已关闭
         */
        CLOSED("3"),

        /**
         * 已评价
         */
        EVALUATED("4");

        private final String value;
    }


}
