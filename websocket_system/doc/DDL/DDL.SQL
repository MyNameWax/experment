CREATE TABLE ws_user
(
    id         BIGINT       NOT NULL auto_increment PRIMARY KEY COMMENT 'ID编号',
    username   VARCHAR(100) NOT NULL COMMENT '用户名',
    `PASSWORD` VARCHAR(200) NOT NULL COMMENT '密码'
) COMMENT 'ws用户表';
