-- ----------------------------
-- 创建数据库
-- ----------------------------
-- CREATE DATABASE bonfire DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='调度任务表'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='调度任务日志表'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='系统_访问日志'
;

-- ----------------------------
-- 系统_权限
-- ----------------------------
CREATE TABLE `sys_privilege` (
    `id` varchar(64) NOT NULL COMMENT 'id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
    `remark` varchar(100) DEFAULT NULL COMMENT '备注',
    `priv_type` tinyint(4) DEFAULT NULL COMMENT '权限类型，1=菜单，2=功能',
    `priv_name` varchar(20) DEFAULT NULL COMMENT '权限文本，中文，支持修改',
    `priv_key` varchar(50) DEFAULT NULL COMMENT '权限key，英文，一般不可修改',
    `parent_id` varchar(64) DEFAULT NULL COMMENT '父级id，根节点该值为0',
    `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_privilege_id_uindex` (`id`),
    UNIQUE KEY `sys_privilege_priv_key_uindex` (`priv_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci COMMENT='系统_权限表'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='系统_字典'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='上传文件信息表'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='系统通知表'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='系统_角色'
;

-- ----------------------------
-- 角色_权限
-- ----------------------------
CREATE TABLE `sys_role_privilege` (
    `id` varchar(64) NOT NULL COMMENT 'uuid，pk',
    `create_time` datetime DEFAULT NULL,
    `create_user` varchar(100) DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    `update_user` varchar(100) DEFAULT NULL,
    `remark` varchar(200) DEFAULT NULL,
    `role_id` varchar(64) DEFAULT NULL COMMENT 'sys_role.id',
    `priv_id` varchar(64) DEFAULT NULL COMMENT 'sys_privilege.id',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_role_privilege_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='系统_角色_权限关联表'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='系统工单表'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='工单回复信息'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='系统_用户'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='用户通知表'
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
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='系统_用户_角色关联表'
;

-- ----------------------------
-- 工作流_OA_请假业务表
-- ----------------------------
CREATE TABLE `bpm_oa_leave`
(
    `id`                  varchar(64) NOT NULL COMMENT 'pk',
    `process_instance_id` varchar(64)  DEFAULT NULL COMMENT '流程实例id',
    `start_time`          datetime     DEFAULT NULL COMMENT '请假开始时间',
    `end_time`            datetime     DEFAULT NULL COMMENT '请假结束时间',
    `reason`              varchar(200) DEFAULT NULL COMMENT '请假原因',
    `create_time`         datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`         datetime     DEFAULT NULL COMMENT '更新时间',
    `create_user`         varchar(64)  DEFAULT NULL COMMENT '创建用户',
    `update_user`         varchar(64)  DEFAULT NULL COMMENT '更新用户',
    `remark`              varchar(200) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `oa_leave_id_uindex` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='请假业务数据'
;

-- ----------------------------
-- 初始化数据
-- ----------------------------
-- sched_job
INSERT INTO sched_job (id, job_name, job_group, job_method, create_time, create_user, update_time, update_user, remark, cron, status, job_class, param) VALUES ('1506235348047417346', '5秒自动打印', 'g1', 'test', '2022-03-22 19:44:26', '1', '2022-03-24 16:00:07', '1', '测试任务，每5秒后端打印一次调用参数', '*/5 * * * * ?', '1', 'sysTask', '111,aaa,222');
INSERT INTO sched_job (id, job_name, job_group, job_method, create_time, create_user, update_time, update_user, remark, cron, status, job_class, param) VALUES ('1506235583117185026', '10秒自动打印', 'g2', 'test', '2022-03-22 19:45:22', '1', '2022-03-24 16:19:39', '1', '测试任务，每10秒后端打印一次调用参数', '*/10 * * * * ?', '1', 'sysTask', 'aaa,111,bbb');

-- sched_job_log
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242315549671426', '1506235583117185026', 'sysTask', 'test', 'aaa,111,bbb', '0', null, 0, '2022-03-22 20:12:07', '1', '2022-03-22 20:12:07', '1');
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242844845670401', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 1, '2022-03-22 20:14:14', null, '2022-03-22 20:14:14', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242848687652866', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:14:14', '1', '2022-03-22 20:14:14', '1');
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242851673997313', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:14:15', null, '2022-03-22 20:14:15', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242872712626178', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:14:20', null, '2022-03-22 20:14:20', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242893772226562', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 1, '2022-03-22 20:14:25', null, '2022-03-22 20:14:25', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242914647277569', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:14:30', null, '2022-03-22 20:14:30', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242935631380482', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:14:35', null, '2022-03-22 20:14:35', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242956703563777', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:14:40', null, '2022-03-22 20:14:40', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242977570226177', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:14:45', null, '2022-03-22 20:14:45', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506242998541746177', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:14:50', null, '2022-03-22 20:14:50', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506243019530043394', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:14:55', null, '2022-03-22 20:14:55', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506243040484786177', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:15:00', null, '2022-03-22 20:15:00', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506243061452111874', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:15:05', null, '2022-03-22 20:15:05', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506244165271289857', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 1, '2022-03-22 20:19:28', '1', '2022-03-22 20:19:28', '1');
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506244354950299650', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:20:14', null, '2022-03-22 20:20:14', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506244361694740481', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:20:15', null, '2022-03-22 20:20:15', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506244382657871874', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:20:20', null, '2022-03-22 20:20:20', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506244403637780481', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:20:25', null, '2022-03-22 20:20:25', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506244424605106178', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:20:30', null, '2022-03-22 20:20:30', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506244445576626178', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:20:35', null, '2022-03-22 20:20:35', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506244466648809473', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:20:40', null, '2022-03-22 20:20:40', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506244487494500354', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-22 20:20:45', null, '2022-03-22 20:20:45', null);
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506903739804454914', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 4, '2022-03-24 16:00:23', '1', '2022-03-24 16:00:23', '1');
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506904199630196737', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 2, '2022-03-24 16:02:13', '1', '2022-03-24 16:02:13', '1');
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506907794644680706', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-24 16:16:30', '1', '2022-03-24 16:16:30', '1');
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506908216038014978', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 2, '2022-03-24 16:18:10', '1', '2022-03-24 16:18:10', '1');
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506908374087778305', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 1, '2022-03-24 16:18:48', '1', '2022-03-24 16:18:48', '1');
INSERT INTO sched_job_log (id, job_id, job_class, job_method, param, status, message, duration_millis, create_time, create_user, update_time, update_user) VALUES ('1506908420908793858', '1506235348047417346', 'sysTask', 'test', '111,aaa,222', '0', null, 0, '2022-03-24 16:18:59', '1', '2022-03-24 16:18:59', '1');

-- sys_access_log
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486144941818228737', 'com.izneus.bonfire.module.system.controller.v1.AuthUserController.getUserInfo()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:12:20', '0:0:0:0:0:0:0:1', '我的信息', 'admin1', 'Chrome', 'OSX', '1', 15367, null, '2022-01-26 09:12:20', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486144877397913602', 'com.izneus.bonfire.module.system.controller.v1.LoginController.getCaptcha()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:12:05', '0:0:0:0:0:0:0:1', '获得验证码', null, 'Chrome', 'OSX', null, 47610, null, '2022-01-26 09:12:05', null, null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486143392693989378', 'com.izneus.bonfire.module.system.controller.v1.SysDictController.listDictDetails()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:06:11', '0:0:0:0:0:0:0:1', '通过dictType获得字典详情', 'admin1', 'Chrome', 'OSX', '1', 51, '[{"dictType":"user_status"}]', '2022-01-26 09:06:11', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486143388659068929', 'com.izneus.bonfire.module.system.controller.v1.LoginController.login()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:06:10', '0:0:0:0:0:0:0:1', '用户登录', null, 'Chrome', 'OSX', null, 469, '[{"captchaId":"770e935fba1a4251a70731a79f6e97d8","password":"*","captcha":"1","username":"admin1"}]', '2022-01-26 09:06:10', null, null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486143390496174081', 'com.izneus.bonfire.module.system.controller.v1.AuthUserController.getUserInfo()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:06:10', '0:0:0:0:0:0:0:1', '我的信息', 'admin1', 'Chrome', 'OSX', '1', 152, null, '2022-01-26 09:06:10', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486143371521142786', 'com.izneus.bonfire.module.system.controller.v1.LoginController.getCaptcha()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:06:06', '0:0:0:0:0:0:0:1', '获得验证码', null, 'Chrome', 'OSX', null, 852, null, '2022-01-26 09:06:06', null, null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486143366999683073', 'com.izneus.bonfire.module.system.controller.v1.LoginController.logout()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:06:05', '0:0:0:0:0:0:0:1', '登出', 'admin1', 'Chrome', 'OSX', '1', 78, null, '2022-01-26 09:06:05', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486143165924749314', 'com.izneus.bonfire.module.system.controller.v1.SysDictController.listDictDetails()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:05:17', '0:0:0:0:0:0:0:1', '通过dictType获得字典详情', 'admin1', 'Chrome', 'OSX', '1', 100, '[{"dictType":"user_status"}]', '2022-01-26 09:05:17', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486143163156508673', 'com.izneus.bonfire.module.system.controller.v1.AuthUserController.getUserInfo()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:05:16', '0:0:0:0:0:0:0:1', '我的信息', 'admin1', 'Chrome', 'OSX', '1', 272, null, '2022-01-26 09:05:16', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486143121049890818', 'com.izneus.bonfire.module.system.controller.v1.SysDictController.listDictDetails()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:05:06', '0:0:0:0:0:0:0:1', '通过dictType获得字典详情', 'admin1', 'Chrome', 'OSX', '1', 153, '[{"dictType":"user_status"}]', '2022-01-26 09:05:06', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1486143113978294273', 'com.izneus.bonfire.module.system.controller.v1.AuthUserController.getUserInfo()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-26 09:05:04', '0:0:0:0:0:0:0:1', '我的信息', 'admin1', 'Chrome', 'OSX', '1', 1206, null, '2022-01-26 09:05:04', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1484068083084591105', 'com.izneus.bonfire.module.system.controller.v1.SysPrivilegeController.listPrivTree()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-20 15:39:38', '127.0.0.1', '权限树', 'admin1', 'Chrome', 'OSX', '1', 89, null, '2022-01-20 15:39:38', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1484068020883062786', 'com.izneus.bonfire.module.system.controller.v1.LoginController.login()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-20 15:39:23', '127.0.0.1', '用户登录', null, 'Chrome', 'OSX', null, 1133, '[{"captchaId":"string","password":"*","captcha":"string","username":"admin1"}]', '2022-01-20 15:39:23', null, null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1484043098203979777', 'com.izneus.bonfire.module.system.controller.v1.SysAuthorityController.listAuthorities()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-20 14:00:21', '0:0:0:0:0:0:0:1', '权限列表', 'admin1', 'Chrome', 'OSX', '1', 40, '[{"pageSize":100,"pageNum":1}]', '2022-01-20 14:00:21', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1484043098275282945', 'com.izneus.bonfire.module.system.controller.v1.SysRoleController.getRoleById()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-20 14:00:21', '0:0:0:0:0:0:0:1', '角色详情', 'admin1', 'Chrome', 'OSX', '1', 59, '[{"id":"1"}]', '2022-01-20 14:00:21', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1484043091992215554', 'com.izneus.bonfire.module.system.controller.v1.SysAuthorityController.listAuthorities()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-20 14:00:20', '0:0:0:0:0:0:0:1', '权限列表', 'admin1', 'Chrome', 'OSX', '1', 443, '[{"pageSize":100,"pageNum":1}]', '2022-01-20 14:00:20', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1484043092248068098', 'com.izneus.bonfire.module.system.controller.v1.SysRoleController.getRoleById()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-20 14:00:20', '0:0:0:0:0:0:0:1', '角色详情', 'admin1', 'Chrome', 'OSX', '1', 516, '[{"id":"1"}]', '2022-01-20 14:00:20', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1483801568170999810', 'com.izneus.bonfire.module.system.controller.v1.SysAuthorityController.listAuthorities()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-19 22:00:36', '0:0:0:0:0:0:0:1', '权限列表', 'admin1', 'Chrome', 'OSX', '1', 43, '[{"pageSize":100,"pageNum":1}]', '2022-01-19 22:00:36', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1483801568217137154', 'com.izneus.bonfire.module.system.controller.v1.SysRoleController.getRoleById()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-19 22:00:36', '0:0:0:0:0:0:0:1', '角色详情', 'admin1', 'Chrome', 'OSX', '1', 41, '[{"id":"1"}]', '2022-01-19 22:00:36', '1', null);
INSERT INTO sys_access_log (id, method, user_agent, create_time, client_ip, description, username, browser, os, create_user, elapsed_time, param, update_time, update_user, remark) VALUES ('1483801563561459713', 'com.izneus.bonfire.module.system.controller.v1.SysRoleController.listRoles()', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36', '2022-01-19 22:00:35', '0:0:0:0:0:0:0:1', '角色列表', 'admin1', 'Chrome', 'OSX', '1', 37, '[{"pageSize":10,"pageNum":1}]', '2022-01-19 22:00:35', '1', null);

-- sys_dict
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('1', 'user_status', '0', '正常', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 1, '用户账号状态');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('10', 'job_status', '0', '正常', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 2, '调度任务状态');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('11', 'job_status', '1', '暂停', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 2, '调度任务状态');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('12', 'job_log_status', '0', '成功', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 1, '任务运行状态');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('13', 'job_log_status', '1', '失败', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 2, '任务运行状态');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('1506536710605905921', 'privilege_type', '3', 'API接口', '2022-03-23 15:41:57', '2022-03-23 15:45:30', '1', '1', '0', 3, '权限类型，特指API');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('2', 'user_status', '1', '锁定', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 2, '用户账号状态');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('3', 'yes_no', '1', '是', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 1, '是否定义');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('4', 'yes_no', '0', '否', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 2, '是否定义');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('5', 'dict_status', '0', '有效', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 1, '字典状态');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('6', 'dict_status', '1', '无效', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 2, '字典状态');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('7', 'auth_type', '0', '系统', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 1, '权限类型');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('8', 'privilege_type', '1', '菜单', '2022-03-15 13:39:11', '2022-03-23 15:45:15', '1', '1', '0', 1, '权限类型，特指侧边菜单');
INSERT INTO sys_dict (id, dict_type, dict_value, dict_label, create_time, update_time, create_user, update_user, status, dict_sort, remark) VALUES ('9', 'privilege_type', '2', '功能', '2022-03-15 13:39:11', '2022-03-15 13:39:11', '1', '1', '0', 2, '权限类型');

-- sys_file
INSERT INTO sys_file (id, filename, unique_filename, suffix, path, file_size, create_time, create_user, update_time, update_user, remark) VALUES ('1456886116274782209', '0d38efbd7d8f46c8bf476890b74a62b4.xlsx', 'bfd8a166699141bf8fbd1ed1c6076f6b.xlsx', 'xlsx', '/bfd8a166699141bf8fbd1ed1c6076f6b.xlsx', 3804, '2021-11-06 15:28:12', '1', '2021-11-06 15:28:12', '1', null);
INSERT INTO sys_file (id, filename, unique_filename, suffix, path, file_size, create_time, create_user, update_time, update_user, remark) VALUES ('1456886191218606082', '0d38efbd7d8f46c8bf476890b74a62b4.xlsx', '51d90be96f5944af8d375e04a80085b3.xlsx', 'xlsx', '/51d90be96f5944af8d375e04a80085b3.xlsx', 3804, '2021-11-06 15:28:30', '1', '2021-11-06 15:28:30', '1', null);
INSERT INTO sys_file (id, filename, unique_filename, suffix, path, file_size, create_time, create_user, update_time, update_user, remark) VALUES ('1456886238299668482', '0d38efbd7d8f46c8bf476890b74a62b4.xlsx', '028b8d01048745198804094a03bc4acb.xlsx', 'xlsx', '/028b8d01048745198804094a03bc4acb.xlsx', 3804, '2021-11-06 15:28:42', '1', '2021-11-06 15:28:42', '1', null);
INSERT INTO sys_file (id, filename, unique_filename, suffix, path, file_size, create_time, create_user, update_time, update_user, remark) VALUES ('1456886394717847554', '0d38efbd7d8f46c8bf476890b74a62b4.xlsx', 'd3c0ffb5a2414a878398055d49d5143a.xlsx', 'xlsx', '/d3c0ffb5a2414a878398055d49d5143a.xlsx', 3804, '2021-11-06 15:29:19', '1', '2021-11-06 15:29:19', '1', null);
INSERT INTO sys_file (id, filename, unique_filename, suffix, path, file_size, create_time, create_user, update_time, update_user, remark) VALUES ('1457985164553502722', '0d38efbd7d8f46c8bf476890b74a62b4.xlsx', 'aa3568f6d9da44ada2a47501c1532dd2.xlsx', 'xlsx', '/aa3568f6d9da44ada2a47501c1532dd2.xlsx', 3804, '2021-11-09 16:15:26', '1', '2021-11-09 16:15:26', '1', null);
INSERT INTO sys_file (id, filename, unique_filename, suffix, path, file_size, create_time, create_user, update_time, update_user, remark) VALUES ('1457989234311839745', '0d38efbd7d8f46c8bf476890b74a62b4.xlsx', 'caccc23081fa4b539a328592f040a662.xlsx', 'xlsx', '/caccc23081fa4b539a328592f040a662.xlsx', 3804, '2021-11-09 16:31:36', '1', '2021-11-09 16:31:36', '1', null);

-- sys_privilege
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1', '2022-03-12 17:07:54', '1', '2022-03-13 22:35:36', '1', '管理员权限（注意和管理员角色的区别），有该权限即为管理员', 2, '管理员权限', 'admin', '0', 0);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1502993773800464385', '2022-03-13 21:03:35', '1', '2022-03-13 21:03:48', '1', '侧边栏菜单', 1, '菜单', 'menu', '0', 1);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503000867006062594', '2022-03-13 21:31:46', '1', '2022-03-13 22:14:24', '1', '首页菜单', 1, '首页', 'menu:dashboard', '1502993773800464385', 1);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503001540745502722', '2022-03-13 21:34:26', '1', '2022-03-13 22:14:31', '1', '系统管理菜单', 1, '系统管理', 'menu:system', '1502993773800464385', 2);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503001915892441089', '2022-03-13 21:35:56', '1', '2022-03-13 21:43:18', '1', '字典管理菜单', 1, '字典管理', 'menu:dict', '1503001540745502722', 1);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503002225570488322', '2022-03-13 21:37:10', '1', '2022-03-13 21:43:23', '1', '访问日志管理菜单', 1, '访问日志管理', 'menu:accessLog', '1503001540745502722', 2);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503003590455730177', '2022-03-13 21:42:35', '1', '2022-03-13 21:43:26', '1', '文件管理菜单', 1, '文件管理', 'menu:file', '1503001540745502722', 3);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503011552993746945', '2022-03-13 22:14:13', '1', '2022-03-13 22:14:13', '1', '人员管理菜单', 1, '人员管理', 'menu:staff', '1502993773800464385', 3);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503011993722822658', '2022-03-13 22:15:59', '1', '2022-03-13 22:15:59', '1', '用户管理菜单', 1, '用户管理', 'menu:user', '1503011552993746945', 1);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503012069375483905', '2022-03-13 22:16:17', '1', '2022-03-13 22:16:17', '1', '角色管理菜单', 1, '角色管理', 'menu:role', '1503011552993746945', 2);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503012145925726209', '2022-03-13 22:16:35', '1', '2022-03-13 22:16:35', '1', '权限管理菜单', 1, '权限管理', 'menu:privilege', '1503011552993746945', 3);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503013104257081346', '2022-03-13 22:20:23', '1', '2022-03-13 22:26:21', '1', '任务调度菜单', 1, '任务调度', 'menu:task', '1502993773800464385', 4);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503013230816010242', '2022-03-13 22:20:54', '1', '2022-03-13 22:20:54', '1', '任务管理菜单', 1, '任务管理', 'menu:job', '1503013104257081346', 1);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503013320397955074', '2022-03-13 22:21:15', '1', '2022-03-13 22:21:15', '1', '任务日志菜单', 1, '任务日志', 'menu:jobLog', '1503013104257081346', 2);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503013597729529857', '2022-03-13 22:22:21', '1', '2022-03-13 22:27:31', '1', '监控菜单', 1, '监控', 'menu:monitor', '1502993773800464385', 5);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503013894086467586', '2022-03-13 22:23:32', '1', '2022-03-13 22:28:19', '1', '公告菜单', 1, '公告', 'menu:inform', '1502993773800464385', 6);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503014994386624513', '2022-03-13 22:27:54', '1', '2022-03-13 22:27:54', '1', '主机监控菜单', 1, '主机监控', 'menu:server', '1503013597729529857', 1);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503015201773985794', '2022-03-13 22:28:43', '1', '2022-03-13 22:28:43', '1', '通知公告菜单', 1, '通知公告', 'menu:notice', '1503013894086467586', 1);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503015389884325889', '2022-03-13 22:29:28', '1', '2022-03-13 22:29:28', '1', '工单管理菜单', 1, '工单管理', 'menu:order', '1502993773800464385', 7);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503015490711199745', '2022-03-13 22:29:52', '1', '2022-03-13 22:29:52', '1', '工单菜单', 1, '工单', 'menu:work', '1503015389884325889', 1);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1503015695561007106', '2022-03-13 22:30:41', '1', '2022-03-13 22:33:47', '1', '开发工具箱菜单，包括下面的所有子菜单', 1, '开发工具箱', 'menu:dev', '1502993773800464385', 99);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1506537885799587842', '2022-03-23 15:46:37', '1', '2022-03-23 15:46:37', '1', 'api颗粒度的权限', 3, 'API接口', 'api', '0', 2);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1506538238335033346', '2022-03-23 15:48:01', '1', '2022-03-23 15:48:01', '1', '用户管理父权限', 3, '用户管理', 'sys:user', '1506537885799587842', 0);
INSERT INTO sys_privilege (id, create_time, create_user, update_time, update_user, remark, priv_type, priv_name, priv_key, parent_id, sort) VALUES ('1506538439275749378', '2022-03-23 15:48:49', '1', '2022-03-23 15:48:49', '1', '用户列表查询', 3, '用户查询', 'sys:user:list', '1506538238335033346', 0);

-- sys_role
INSERT INTO sys_role (id, role_name, create_time, create_user, update_time, update_user, remark) VALUES ('1', '管理员', '2022-03-15 19:44:35', '1', '2022-03-24 15:19:20', '1', '预设的管理员角色');
INSERT INTO sys_role (id, role_name, create_time, create_user, update_time, update_user, remark) VALUES ('1503698668706971650', '普通用户', '2022-03-15 19:44:35', '1', '2022-03-24 15:16:02', '1', '预设的普通用户角色，演示只有首页、用户管理2个菜单，和用户列表查询api的权限');

-- sys_role_privilege
INSERT INTO sys_role_privilege (id, create_time, create_user, update_time, update_user, remark, role_id, priv_id) VALUES ('1506892576362369025', '2022-03-24 15:16:02', '1', '2022-03-24 15:16:02', '1', null, '1503698668706971650', '1503011993722822658');
INSERT INTO sys_role_privilege (id, create_time, create_user, update_time, update_user, remark, role_id, priv_id) VALUES ('1506892576374951937', '2022-03-24 15:16:02', '1', '2022-03-24 15:16:02', '1', null, '1503698668706971650', '1506537885799587842');
INSERT INTO sys_role_privilege (id, create_time, create_user, update_time, update_user, remark, role_id, priv_id) VALUES ('1506892576379146241', '2022-03-24 15:16:02', '1', '2022-03-24 15:16:02', '1', null, '1503698668706971650', '1506538238335033346');
INSERT INTO sys_role_privilege (id, create_time, create_user, update_time, update_user, remark, role_id, priv_id) VALUES ('1506892576383340545', '2022-03-24 15:16:02', '1', '2022-03-24 15:16:02', '1', null, '1503698668706971650', '1506538439275749378');
INSERT INTO sys_role_privilege (id, create_time, create_user, update_time, update_user, remark, role_id, priv_id) VALUES ('1506892576391729154', '2022-03-24 15:16:02', '1', '2022-03-24 15:16:02', '1', null, '1503698668706971650', '1502993773800464385');
INSERT INTO sys_role_privilege (id, create_time, create_user, update_time, update_user, remark, role_id, priv_id) VALUES ('1506892576391729155', '2022-03-24 15:16:02', '1', '2022-03-24 15:16:02', '1', null, '1503698668706971650', '1503011552993746945');
INSERT INTO sys_role_privilege (id, create_time, create_user, update_time, update_user, remark, role_id, priv_id) VALUES ('1506893408571002881', '2022-03-24 15:19:20', '1', '2022-03-24 15:19:20', '1', null, '1', '1');

-- sys_user
INSERT INTO sys_user (id, username, password, nickname, fullname, email, mobile, create_time, create_user, update_time, update_user, remark, status) VALUES ('1', 'admin', '$2a$10$cAQsPzb7bZP/DIsV6em2rOA/BTkzGGJFWKzMecuJwKwjMqaFnKJwu', '超级管理员', '张三', 'zhangs@email.com', '13900000000', '2020-07-05 15:31:09', '1', '2022-03-15 19:45:35', '1', '预设的管理员帐号', '0');
INSERT INTO sys_user (id, username, password, nickname, fullname, email, mobile, create_time, create_user, update_time, update_user, remark, status) VALUES ('2', 'normal', '$2a$10$cAQsPzb7bZP/DIsV6em2rOA/BTkzGGJFWKzMecuJwKwjMqaFnKJwu', '普通用户', '李四', null, '', null, '1', '2022-03-15 19:48:01', '1', '预设的普通用户', '0');

-- sys_user_role
INSERT INTO sys_user_role (id, user_id, role_id, create_time, create_user, update_time, update_user, remark) VALUES ('1503698920629452802', '1', '1', '2022-03-15 19:45:35', '1', '2022-03-15 19:45:35', '1', null);
INSERT INTO sys_user_role (id, user_id, role_id, create_time, create_user, update_time, update_user, remark) VALUES ('1503699533425655809', '2', '1503698668706971650', '2022-03-15 19:48:01', '1', '2022-03-15 19:48:01', '1', null);
