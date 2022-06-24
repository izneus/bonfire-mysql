-- 多数据源部分用来演示的数据库

-- ----------------------------
-- 创建数据库
-- ----------------------------
-- CREATE DATABASE lamp DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- ----------------------------
-- 多数据源测试表
-- ----------------------------
CREATE TABLE `ds_city`
(
    `id`   varchar(100) NOT NULL DEFAULT '',
    `city` varchar(100)          DEFAULT NULL COMMENT '城市名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE utf8mb4_general_ci COMMENT ='多数据源测试表'
;
