package cn.rzpt.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum DataResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(500, "系统错误"),
    // 用户相关
    UNAUTHORIZED(801, "未认证"),
    USERNAME_PASSWORD_ERROR(802, "用户名或密码错误"),
    USERNAME_EXIST(803, "用户名已存在"),
    USERNAME_NOT_ALLOW_EMPTY(804, "用户名不允许为空"),
    USERNAME_LENGTH_TOO_SHORT_ERROR(805, "用户名长度过短"),
    USERNAME_LENGTH_TOO_LONG_ERROR(806, "用户名长度过长"),
    PASSWORD_NOT_ALLOW_EMPTY(807, "密码不允许为空"),
    PASSWORD_LENGTH_TOO_SHORT_ERROR(808, "密码长度过短"),
    PASSWORD_LENGTH_TOO_LONG_ERROR(809, "密码长度过长"),
    EMAIL_ERROR(810, "邮箱格式错误"),
    EMAIL_EXIST(811, "邮箱已存在"),
    EMAIL_NOT_ALLOW_EMPTY(813, "邮箱不允许为空"),
    PHONE_ERROR(814, "手机号格式错误"),
    PHONE_EXIST(815, "手机号已存在"),
    PHONE_NOT_ALLOW_EMPTY(816, "手机号不允许为空"),
    // 卡券生成异常
    CARD_GENERATE_EXIST(1001, "卡券已存在"),
    CARD_ERROR(1002,"卡券异常"),
    CARD_ALWAYS_USED(1003,"卡券已兑换");
    private final Integer code;
    private final String message;
}
