-- ----------------------------
-- 调度任务表
-- ----------------------------
CREATE TABLE `sched_job`
(
    `id`          varchar(64) NOT NULL COMMENT 'uuid,pk',
    `job_name`    varchar(100) DEFAULT NULL COMMENT '任务名称',
    `job_group`   varchar(100) DEFAULT NULL COMMENT '任务组',
    `job_method`  varchar(100) DEFAULT NULL COMMENT '任务具体实现的方法名',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(64)  DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(64)  DEFAULT NULL COMMENT '更新人',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    `cron`        varchar(100) DEFAULT NULL COMMENT 'cron表达式',
    `status`      varchar(10)  DEFAULT NULL COMMENT '任务状态',
    `job_class`   varchar(64)  DEFAULT NULL COMMENT '任务具体实现的类名',
    `param`       varchar(500) DEFAULT NULL COMMENT '调用任务参数',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sched_job_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='调度任务表'
;

-- ----------------------------
-- 调度任务日志表
-- ----------------------------
CREATE TABLE `sched_job_log`
(
    `id`              varchar(64) NOT NULL COMMENT 'id',
    `job_id`          varchar(100)   DEFAULT NULL COMMENT 'sys_job表id',
    `job_class`       varchar(100)   DEFAULT NULL,
    `job_method`      varchar(100)   DEFAULT NULL,
    `param`           varchar(100)   DEFAULT NULL,
    `status`          varchar(10)    DEFAULT NULL COMMENT '任务执行状态',
    `message`         varchar(1000)  DEFAULT NULL COMMENT '出错信息',
    `duration_millis` decimal(20, 0) DEFAULT NULL COMMENT '执行消耗时间，单位毫秒',
    `create_time`     datetime       DEFAULT NULL,
    `create_user`     varchar(100)   DEFAULT NULL,
    `update_time`     datetime       DEFAULT NULL,
    `update_user`     varchar(100)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `sched_job_log_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='调度任务日志表'
;

-- ----------------------------
-- 系统_访问日志
-- ----------------------------
CREATE TABLE `sys_access_log`
(
    `id`           varchar(64) NOT NULL COMMENT 'id',
    `method`       varchar(500) DEFAULT NULL COMMENT 'http method',
    `user_agent`   text COMMENT '用户代理',
    `create_time`  datetime     DEFAULT NULL COMMENT '创建时间',
    `client_ip`    varchar(64)  DEFAULT NULL COMMENT '客户端ip',
    `description`  varchar(100) DEFAULT NULL COMMENT '注解描述',
    `username`     varchar(64)  DEFAULT NULL COMMENT '发起请求的用户名',
    `browser`      varchar(100) DEFAULT NULL COMMENT '浏览器',
    `os`           varchar(100) DEFAULT NULL COMMENT '系统',
    `create_user`  varchar(100) DEFAULT NULL COMMENT '发起请求的用户id',
    `elapsed_time` bigint(20)   DEFAULT NULL COMMENT '访问经过的时间，单位毫秒',
    `param`        text COMMENT '请求参数',
    `update_time`  datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user`  varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark`       varchar(100) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_access_log_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统_访问日志'
;

-- ----------------------------
-- 系统_权限
-- ----------------------------
CREATE TABLE `sys_authority`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `authority`   varchar(64)  DEFAULT NULL COMMENT '权限名称',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark`      varchar(100) DEFAULT NULL COMMENT '备注',
    `type`        varchar(10)  DEFAULT NULL COMMENT '权限类型',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_authority_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统_权限'
;

-- ----------------------------
-- 系统_字典
-- ----------------------------
CREATE TABLE `sys_dict`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `dict_type`   varchar(64)  DEFAULT NULL COMMENT '字典类型',
    `dict_value`  varchar(64)  DEFAULT NULL COMMENT '字典值（编码）',
    `dict_label`  varchar(100) DEFAULT NULL COMMENT '字典文本',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `create_user` varchar(64)  DEFAULT NULL COMMENT '创建人',
    `update_user` varchar(64)  DEFAULT NULL COMMENT '更新人',
    `status`      varchar(64)  DEFAULT NULL COMMENT '字典状态，0=有效，1=无效',
    `dict_sort`   varchar(10)  DEFAULT NULL COMMENT '排序号',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_dict_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统_字典'
;

-- ----------------------------
-- 上传文件信息表
-- ----------------------------
CREATE TABLE `sys_file`
(
    `id`              varchar(64) NOT NULL COMMENT 'id',
    `filename`        varchar(100)   DEFAULT NULL COMMENT '文件名，特指上传之前的文件名',
    `unique_filename` varchar(500)   DEFAULT NULL COMMENT '哈希文件名，特指服务器保存在硬盘上的文件名，目前为uuid',
    `suffix`          varchar(64)    DEFAULT NULL COMMENT '后缀',
    `path`            varchar(500)   DEFAULT NULL COMMENT '文件路径',
    `file_size`       decimal(20, 0) DEFAULT NULL COMMENT '文件大小',
    `create_time`     datetime       DEFAULT NULL COMMENT '创建时间',
    `create_user`     varchar(100)   DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime       DEFAULT NULL COMMENT '更新时间',
    `update_user`     varchar(100)   DEFAULT NULL COMMENT '更新人',
    `remark`          varchar(500)   DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_file_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='上传文件信息表'
;

-- ----------------------------
-- 系统通知表
-- ----------------------------
CREATE TABLE `sys_notice`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `title`       varchar(20)  DEFAULT NULL COMMENT '标题',
    `notice`      varchar(500) DEFAULT NULL COMMENT '通知内容',
    `notice_type` varchar(20)  DEFAULT NULL COMMENT '通知类型，字典，0=全局通知，1=局部消息',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark`      varchar(100) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_notice_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统通知表'
;

-- ----------------------------
-- 系统_角色
-- ----------------------------
CREATE TABLE `sys_role`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `role_name`   varchar(64)  DEFAULT NULL COMMENT '角色名称',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_role_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统_角色'
;

-- ----------------------------
-- 角色_权限
-- ----------------------------
CREATE TABLE `sys_role_authority`
(
    `id`           varchar(64) NOT NULL COMMENT 'id',
    `role_id`      varchar(64)  DEFAULT NULL COMMENT 'sys_role表id',
    `authority_id` varchar(64)  DEFAULT NULL COMMENT 'sys_authority表id',
    `create_time`  datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user`  varchar(100) DEFAULT NULL COMMENT '创建人',
    `update_time`  datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user`  varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark`       varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_role_authority_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色_权限'
;

-- ----------------------------
-- 系统工单表
-- ----------------------------
CREATE TABLE `sys_ticket`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `title`       varchar(100) DEFAULT NULL COMMENT '工单标题',
    `ticket`      varchar(500) DEFAULT NULL COMMENT '工单内容',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    `status`      varchar(10)  DEFAULT NULL COMMENT '工单状态',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_ticket_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统工单表'
;

-- ----------------------------
-- 工单回复信息
-- ----------------------------
CREATE TABLE `sys_ticket_flow`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `ticket_id`   varchar(64)   DEFAULT NULL COMMENT 'sys_ticket.id',
    `flow`        varchar(2000) DEFAULT NULL COMMENT '回复信息',
    `create_time` datetime      DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(100)  DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(100)  DEFAULT NULL COMMENT '更新人',
    `remark`      varchar(500)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_ticket_flow_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='工单回复信息'
;

-- ----------------------------
-- 系统_用户
-- ----------------------------
CREATE TABLE `sys_user`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `username`    varchar(100) DEFAULT NULL COMMENT '用户名',
    `password`    varchar(100) DEFAULT NULL COMMENT '密码',
    `nickname`    varchar(100) DEFAULT NULL COMMENT '昵称',
    `fullname`    varchar(100) DEFAULT NULL COMMENT '全名',
    `email`       varchar(100) DEFAULT NULL COMMENT '电子邮件',
    `mobile`      varchar(100) DEFAULT NULL COMMENT '手机号码',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    `status`      varchar(10)  DEFAULT NULL COMMENT '账号状态',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_id_uindex` (`id`),
    UNIQUE KEY `uk_sys_user_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统_用户'
;

-- ----------------------------
-- 用户通知表
-- ----------------------------
CREATE TABLE `sys_user_notice`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `user_id`     varchar(100) DEFAULT NULL COMMENT '用户id，sys_user表主键id',
    `notice_id`   varchar(100) DEFAULT NULL COMMENT '通知id，sys_notice表主键id',
    `status`      varchar(10)  DEFAULT NULL COMMENT '通知状态',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_notice_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户通知表'
;

-- ----------------------------
-- 系统_用户_角色关联表
-- ----------------------------
CREATE TABLE `sys_user_role`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `user_id`     varchar(100) DEFAULT NULL COMMENT '用户id',
    `role_id`     varchar(100) DEFAULT NULL COMMENT '角色id',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_role_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统_用户_角色关联表'
;

