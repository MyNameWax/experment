create database experiment;

use experiment;


create table friend_sse_user
(
    id       bigint auto_increment not null primary key not null comment 'ID',
    username varchar(100)          not null comment '用户名',
    password varchar(200)          not null comment '密码'
) comment 'SSE好友模块 - 用户登录';

create table friend_sse_audit
(
    id          bigint auto_increment not null PRIMARY key comment 'ID',
    user_id     bigint                not null comment '用户ID',
    relate_id   bigint                not null comment '关系ID',
    relate_type int                   not null comment '关系类型(1:群聊 2:好友)'
) comment 'SSE好友模块 - 好友表';

CREATE TABLE message_notification
(
    id             BIGINT PRIMARY KEY,
    user_id        BIGINT NOT NULL COMMENT '用户 id',
    to_user_id     BIGINT NOT NULL COMMENT '接收用户id',
    notice_type    INT    NOT NULL COMMENT '消息通知类型 1 系统通知 2 群聊通知 3 好友通知',
    notice_content VARCHAR(255) COMMENT '消息通知内容',
    read_target    INT    NOT NULL DEFAULT 0 COMMENT '消息通知标识 0 未读 1 已读',
    process_result VARCHAR(255) COMMENT '处理结果'
) comment 'SSE好友模块 - 添加好友';
