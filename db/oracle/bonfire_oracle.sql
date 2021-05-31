-- ----------------------------
-- 调度任务表
-- ----------------------------
create table SCHED_JOB
(
	ID VARCHAR2(64) not null
		constraint SYS_JOB_PK
			primary key,
	JOB_NAME VARCHAR2(100),
	JOB_GROUP VARCHAR2(100),
	JOB_METHOD VARCHAR2(100),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	CREATE_USER VARCHAR2(64),
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(64),
	REMARK VARCHAR2(500),
	CRON VARCHAR2(100),
	STATUS VARCHAR2(10),
	JOB_CLASS VARCHAR2(64),
	PARAM VARCHAR2(100)
)
/

comment on table SCHED_JOB is '调度任务表'
/

comment on column SCHED_JOB.ID is 'id'
/

comment on column SCHED_JOB.JOB_NAME is '任务名称'
/

comment on column SCHED_JOB.JOB_GROUP is '任务组'
/

comment on column SCHED_JOB.JOB_METHOD is '任务具体实现的方法名'
/

comment on column SCHED_JOB.CREATE_TIME is '创建时间'
/

comment on column SCHED_JOB.CREATE_USER is '创建人'
/

comment on column SCHED_JOB.UPDATE_TIME is '更新时间'
/

comment on column SCHED_JOB.UPDATE_USER is '更新人'
/

comment on column SCHED_JOB.REMARK is '备注'
/

comment on column SCHED_JOB.CRON is 'cron表达式'
/

comment on column SCHED_JOB.STATUS is '任务状态'
/

comment on column SCHED_JOB.JOB_CLASS is '任务具体实现的类名'
/

comment on column SCHED_JOB.PARAM is '调用任务参数'
/

-- ----------------------------
-- 调度任务日志表
-- ----------------------------
create table SCHED_JOB_LOG
(
	ID VARCHAR2(64) not null
		constraint SYS_JOB_LOG_PK
			primary key,
	JOB_ID VARCHAR2(100),
	JOB_CLASS VARCHAR2(100),
	JOB_METHOD VARCHAR2(100),
	PARAM VARCHAR2(100),
	STATUS VARCHAR2(10),
	MESSAGE VARCHAR2(1000),
	DURATION_MILLIS NUMBER(20),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	CREATE_USER VARCHAR2(100),
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(100)
)
/

comment on table SCHED_JOB_LOG is '调度任务日志表'
/

comment on column SCHED_JOB_LOG.ID is 'id'
/

comment on column SCHED_JOB_LOG.JOB_ID is 'sys_job表id'
/

comment on column SCHED_JOB_LOG.STATUS is '任务执行状态'
/

comment on column SCHED_JOB_LOG.MESSAGE is '出错信息'
/

comment on column SCHED_JOB_LOG.DURATION_MILLIS is '执行消耗时间，单位毫秒'
/

-- ----------------------------
-- 系统_访问日志
-- ----------------------------
create table SYS_ACCESS_LOG
(
	ID VARCHAR2(64) not null
		constraint SYS_LOG_PK
			primary key,
	METHOD VARCHAR2(500),
	USER_AGENT VARCHAR2(1000),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	CLIENT_IP VARCHAR2(64),
	DESCRIPTION VARCHAR2(64),
	USERNAME VARCHAR2(64),
	BROWSER VARCHAR2(100),
	OS VARCHAR2(100),
	CREATE_USER VARCHAR2(64),
	ELAPSED_TIME NUMBER(19),
	PARAM CLOB,
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(64),
	REMARK VARCHAR2(100)
)
/

comment on table SYS_ACCESS_LOG is '系统_访问日志'
/

comment on column SYS_ACCESS_LOG.ID is 'id'
/

comment on column SYS_ACCESS_LOG.METHOD is 'http method'
/

comment on column SYS_ACCESS_LOG.USER_AGENT is '用户代理'
/

comment on column SYS_ACCESS_LOG.CREATE_TIME is '创建时间'
/

comment on column SYS_ACCESS_LOG.CLIENT_IP is '客户端ip'
/

comment on column SYS_ACCESS_LOG.DESCRIPTION is '注解描述'
/

comment on column SYS_ACCESS_LOG.USERNAME is '发起请求的用户名'
/

comment on column SYS_ACCESS_LOG.BROWSER is '浏览器'
/

comment on column SYS_ACCESS_LOG.OS is '系统'
/

comment on column SYS_ACCESS_LOG.CREATE_USER is '发起请求的用户id'
/

comment on column SYS_ACCESS_LOG.ELAPSED_TIME is '访问经过的时间'
/

comment on column SYS_ACCESS_LOG.PARAM is '请求参数'
/

comment on column SYS_ACCESS_LOG.UPDATE_TIME is '更新时间'
/

comment on column SYS_ACCESS_LOG.REMARK is '备注'
/

-- ----------------------------
-- 系统_权限
-- ----------------------------
create table SYS_AUTHORITY
(
	ID VARCHAR2(64) not null
		constraint SYS_AUTHORITY_PK
			primary key,
	AUTHORITY VARCHAR2(64),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	CREATE_USER VARCHAR2(64),
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(64),
	REMARK VARCHAR2(100),
	TYPE VARCHAR2(10)
)
/

comment on table SYS_AUTHORITY is '系统_权限'
/

comment on column SYS_AUTHORITY.ID is 'id'
/

comment on column SYS_AUTHORITY.AUTHORITY is '权限名称'
/

comment on column SYS_AUTHORITY.CREATE_TIME is '创建时间'
/

comment on column SYS_AUTHORITY.CREATE_USER is '创建人'
/

comment on column SYS_AUTHORITY.UPDATE_TIME is '更新时间'
/

comment on column SYS_AUTHORITY.UPDATE_USER is '更新人'
/

comment on column SYS_AUTHORITY.REMARK is '备注'
/

comment on column SYS_AUTHORITY.TYPE is '权限类型'
/

-- ----------------------------
-- 系统_字典
-- ----------------------------
create table SYS_DICT
(
	ID VARCHAR2(64) not null
		constraint SYS_DICT_PK
			primary key,
	DICT_TYPE VARCHAR2(64),
	DICT_VALUE VARCHAR2(64),
	DICT_LABEL VARCHAR2(100),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	CREATE_USER VARCHAR2(64),
	UPDATE_USER VARCHAR2(64),
	STATUS VARCHAR2(64),
	DICT_SORT VARCHAR2(10),
	REMARK VARCHAR2(500)
)
/

comment on table SYS_DICT is '系统_字典'
/

comment on column SYS_DICT.ID is 'id'
/

comment on column SYS_DICT.DICT_TYPE is '字典类型'
/

comment on column SYS_DICT.DICT_VALUE is '字典值（编码）'
/

comment on column SYS_DICT.DICT_LABEL is '字典文本'
/

comment on column SYS_DICT.CREATE_TIME is '创建时间'
/

comment on column SYS_DICT.UPDATE_TIME is '更新时间'
/

comment on column SYS_DICT.CREATE_USER is '创建人'
/

comment on column SYS_DICT.UPDATE_USER is '更新人'
/

comment on column SYS_DICT.STATUS is '字典状态，0=有效，1=无效'
/

comment on column SYS_DICT.DICT_SORT is '排序号'
/

comment on column SYS_DICT.REMARK is '备注'
/

-- ----------------------------
-- 上传文件信息表
-- ----------------------------
create table SYS_FILE
(
	ID VARCHAR2(64) not null
		constraint SYS_FILE_PK
			primary key,
	FILENAME VARCHAR2(100),
	UNIQUE_FILENAME VARCHAR2(500),
	SUFFIX VARCHAR2(64),
	PATH VARCHAR2(500),
	FILE_SIZE NUMBER(20),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	CREATE_USER VARCHAR2(64),
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(64),
	REMARK VARCHAR2(1000)
)
/

comment on table SYS_FILE is '上传文件信息表'
/

comment on column SYS_FILE.ID is 'id'
/

comment on column SYS_FILE.FILENAME is '文件名，特指上传之前的文件名'
/

comment on column SYS_FILE.UNIQUE_FILENAME is '哈希文件名，特指服务器保存在硬盘上的文件名，目前为uuid'
/

comment on column SYS_FILE.SUFFIX is '后缀'
/

comment on column SYS_FILE.PATH is '文件路径'
/

comment on column SYS_FILE.FILE_SIZE is '文件大小'
/

comment on column SYS_FILE.CREATE_TIME is '创建时间'
/

comment on column SYS_FILE.CREATE_USER is '创建人'
/

comment on column SYS_FILE.UPDATE_TIME is '更新时间'
/

comment on column SYS_FILE.UPDATE_USER is '更新人'
/

comment on column SYS_FILE.REMARK is '备注'
/

-- ----------------------------
-- 系统通知表
-- ----------------------------
create table SYS_NOTICE
(
	ID VARCHAR2(100) not null
		constraint SYS_NOTICE_PK
			primary key,
	TITLE VARCHAR2(20),
	NOTICE VARCHAR2(500),
	CREATE_USER VARCHAR2(100),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(100),
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	REMARK VARCHAR2(100),
	NOTICE_TYPE VARCHAR2(10)
)
/

comment on table SYS_NOTICE is '系统通知表'
/

comment on column SYS_NOTICE.TITLE is '标题'
/

comment on column SYS_NOTICE.NOTICE is '通知内容'
/

comment on column SYS_NOTICE.CREATE_USER is '创建者'
/

comment on column SYS_NOTICE.CREATE_TIME is '创建时间'
/

comment on column SYS_NOTICE.UPDATE_USER is '更新者'
/

comment on column SYS_NOTICE.UPDATE_TIME is '更新时间'
/

comment on column SYS_NOTICE.REMARK is '备注'
/

comment on column SYS_NOTICE.NOTICE_TYPE is '通知类型，字典，0=全局通知，1=局部消息'
/

-- ----------------------------
-- 系统_角色
-- ----------------------------
create table SYS_ROLE
(
	ID VARCHAR2(64) not null
		constraint SYS_ROLE_PK
			primary key,
	ROLE_NAME VARCHAR2(64),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	REMARK VARCHAR2(100),
	CREATE_USER VARCHAR2(64),
	UPDATE_USER VARCHAR2(64)
)
/

comment on table SYS_ROLE is '系统_角色'
/

comment on column SYS_ROLE.ID is 'id'
/

comment on column SYS_ROLE.ROLE_NAME is '角色名称'
/

comment on column SYS_ROLE.CREATE_TIME is '创建时间'
/

comment on column SYS_ROLE.UPDATE_TIME is '更新时间'
/

comment on column SYS_ROLE.REMARK is '备注'
/

comment on column SYS_ROLE.CREATE_USER is '创建人'
/

comment on column SYS_ROLE.UPDATE_USER is '更新人'
/

-- ----------------------------
-- 角色_权限
-- ----------------------------
create table SYS_ROLE_AUTHORITY
(
	ID VARCHAR2(64) not null
		constraint SYS_ROLE_AUTHORITY_PK
			primary key,
	ROLE_ID VARCHAR2(64),
	AUTHORITY_ID VARCHAR2(64),
	CREATE_TIME TIMESTAMP(6),
	CREATE_USER VARCHAR2(64),
	UPDATE_TIME TIMESTAMP(6),
	UPDATE_USER TIMESTAMP(6),
	REMARK VARCHAR2(500)
)
/

comment on table SYS_ROLE_AUTHORITY is '角色_权限'
/

comment on column SYS_ROLE_AUTHORITY.ID is 'id'
/

comment on column SYS_ROLE_AUTHORITY.ROLE_ID is 'sys_role表id'
/

comment on column SYS_ROLE_AUTHORITY.AUTHORITY_ID is 'sys_authority表id'
/

comment on column SYS_ROLE_AUTHORITY.CREATE_TIME is '创建时间'
/

comment on column SYS_ROLE_AUTHORITY.CREATE_USER is '创建用户'
/

comment on column SYS_ROLE_AUTHORITY.UPDATE_TIME is '更新时间'
/

comment on column SYS_ROLE_AUTHORITY.UPDATE_USER is '更新用户'
/

comment on column SYS_ROLE_AUTHORITY.REMARK is '备注'
/

-- ----------------------------
-- 系统工单表
-- ----------------------------
create table SYS_TICKET
(
	ID VARCHAR2(100) not null
		constraint SYS_TICKET_PK
			primary key,
	TITLE VARCHAR2(100),
	TICKET VARCHAR2(500),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	CREATE_USER VARCHAR2(100),
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(100),
	REMARK VARCHAR2(500),
	STATUS VARCHAR2(10)
)
/

comment on table SYS_TICKET is '系统工单表'
/

comment on column SYS_TICKET.TITLE is '工单标题'
/

comment on column SYS_TICKET.TICKET is '工单内容'
/

comment on column SYS_TICKET.CREATE_TIME is '创建时间'
/

comment on column SYS_TICKET.CREATE_USER is '创建人'
/

comment on column SYS_TICKET.UPDATE_TIME is '更新时间'
/

comment on column SYS_TICKET.UPDATE_USER is '更新人'
/

comment on column SYS_TICKET.REMARK is '备注'
/

comment on column SYS_TICKET.STATUS is '工单状态'
/

-- ----------------------------
-- 工单回复信息
-- ----------------------------
create table SYS_TICKET_FLOW
(
	ID VARCHAR2(100) not null
		constraint SYS_TICKET_CHAT_PK
			primary key,
	TICKET_ID VARCHAR2(100),
	FLOW VARCHAR2(2000),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	CREATE_USER VARCHAR2(100),
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(100),
	REMARK VARCHAR2(500)
)
/

comment on table SYS_TICKET_FLOW is '工单回复信息'
/

comment on column SYS_TICKET_FLOW.TICKET_ID is 'sys_ticket.id'
/

comment on column SYS_TICKET_FLOW.FLOW is '回复信息'
/

comment on column SYS_TICKET_FLOW.CREATE_TIME is '创建时间'
/

comment on column SYS_TICKET_FLOW.CREATE_USER is '创建人'
/

comment on column SYS_TICKET_FLOW.UPDATE_TIME is '更新时间'
/

comment on column SYS_TICKET_FLOW.UPDATE_USER is '更新人'
/

comment on column SYS_TICKET_FLOW.REMARK is '备注'
/

-- ----------------------------
-- 系统_用户
-- ----------------------------
create table SYS_USER
(
	ID VARCHAR2(64) not null
		constraint PK_SYS_USER
			primary key,
	USERNAME VARCHAR2(100),
	PASSWORD VARCHAR2(100) not null,
	NICKNAME VARCHAR2(100),
	FULLNAME VARCHAR2(100),
	EMAIL VARCHAR2(100),
	MOBILE VARCHAR2(100),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	REMARK VARCHAR2(200),
	STATUS VARCHAR2(1),
	CREATE_USER VARCHAR2(64),
	UPDATE_USER VARCHAR2(64)
)
/

comment on table SYS_USER is '系统_用户'
/

comment on column SYS_USER.ID is '主键'
/

comment on column SYS_USER.USERNAME is '用户名'
/

comment on column SYS_USER.PASSWORD is '密码'
/

comment on column SYS_USER.NICKNAME is '昵称'
/

comment on column SYS_USER.FULLNAME is '全名'
/

comment on column SYS_USER.EMAIL is '电子邮件'
/

comment on column SYS_USER.MOBILE is '手机号码'
/

comment on column SYS_USER.CREATE_TIME is '创建时间'
/

comment on column SYS_USER.UPDATE_TIME is '修改时间'
/

comment on column SYS_USER.REMARK is '备注'
/

comment on column SYS_USER.STATUS is '账号状态'
/

comment on column SYS_USER.CREATE_USER is '创建人'
/

comment on column SYS_USER.UPDATE_USER is '更新人'
/

create unique index PK_SYS_USER
	on SYS_USER (ID)
/

create unique index UK_SYS_USER_USERNAME
	on SYS_USER (USERNAME)
/

-- ----------------------------
-- 用户通知表
-- ----------------------------
create table SYS_USER_NOTICE
(
	ID VARCHAR2(100) not null
		constraint SYS_USER_NOTICE_PK
			primary key,
	USER_ID VARCHAR2(100),
	NOTICE_ID VARCHAR2(100),
	STATUS VARCHAR2(10),
	CREATE_USER VARCHAR2(100),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(100),
	UPDATE_TIME TIMESTAMP(6),
	REMARK VARCHAR2(500)
)
/

comment on table SYS_USER_NOTICE is '用户通知表'
/

comment on column SYS_USER_NOTICE.USER_ID is '用户id，sys_user表主键id'
/

comment on column SYS_USER_NOTICE.NOTICE_ID is '通知id，sys_notice表主键id'
/

comment on column SYS_USER_NOTICE.STATUS is '通知状态'
/

comment on column SYS_USER_NOTICE.CREATE_USER is '创建人'
/

comment on column SYS_USER_NOTICE.CREATE_TIME is '创建时间'
/

comment on column SYS_USER_NOTICE.UPDATE_USER is '更新人'
/

comment on column SYS_USER_NOTICE.UPDATE_TIME is '更新时间'
/

comment on column SYS_USER_NOTICE.REMARK is '备注'
/

-- ----------------------------
-- 系统_用户_角色关联表
-- ----------------------------
create table SYS_USER_ROLE
(
	ID VARCHAR2(64) not null
		constraint SYS_USER_ROLE_PK
			primary key,
	USER_ID VARCHAR2(64),
	ROLE_ID VARCHAR2(64),
	CREATE_TIME TIMESTAMP(6) default sysdate,
	CREATE_USER VARCHAR2(64),
	UPDATE_TIME TIMESTAMP(6) default sysdate,
	UPDATE_USER VARCHAR2(64),
	REMARK VARCHAR2(100)
)
/

comment on table SYS_USER_ROLE is '系统_用户_角色关联表'
/

comment on column SYS_USER_ROLE.ID is 'id'
/

comment on column SYS_USER_ROLE.USER_ID is '用户id'
/

comment on column SYS_USER_ROLE.ROLE_ID is '角色id'
/

comment on column SYS_USER_ROLE.CREATE_TIME is '创建时间'
/

comment on column SYS_USER_ROLE.CREATE_USER is '创建人'
/

comment on column SYS_USER_ROLE.UPDATE_TIME is '更新时间'
/

comment on column SYS_USER_ROLE.UPDATE_USER is '更新人'
/

comment on column SYS_USER_ROLE.REMARK is '备注'
/

