package com.peach.security.common;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 14:16
 */
public enum SecurityStatusEnum {

    UNKNOWN_ACCOUNT("410", "用户不存在"),
    INVALID_USERNAME("411", "用户名不正确"),
    INVALID_VERIFICATION_CODE("412", "验证码不正确"),
    INVALID_PASSWORD("413", "密码不正确"),
    LOCKED_ACCOUNT("414", "用户已被锁定"),
    CANCELLATION_ACCOUNT("414", "用户已被注销"),
    EXPIRED_PASSWORD("415", "用户密码已失效"),
    INVALID_AUTHORITY("416", "用户未授权"),
    ERROR_AUTH_MODEL("417", "该账户不允许此登录方式登录"),
    EXPIRED_ACCOUNT("418", "该用户已失效"),
    NOT_ACTIVE_ACCOUNT("419", "该用户未生效"),
    INVALID_ACCOUNT("420", "用户名不存在或认证信息错误"),
    DUPLICATE_LOGIN_ACCOUNT("421", "用户已登录"),
    STANDARD_LOGIN_ACCOUNT("422", "用户名不符合规范"),
    STANDARD_LOGIN_PASSWORD("423", "密码不符合规范"),
    SMS_SUCCESS("200", "短信发送成功"),
    SMS_FAIL("400", "短信发送失败"),
    CAPTCHA_EXPIRED("400", "验证码过期"),
    SMS_CHECK_FAILURE("408", "登录验证失败，请先注册"),
    REGISTER_SUCCESS("200", "注册成功"),
    REGISTER_FAIL("408", "注册失败"),
    GRAPH_CAPTCHA_SUCCESS("200", "验证码校验成功"),
    GRAPH_CAPTCHA_FAIL("400", "验证码校验失败"),
    NO_MOBILE_NO("409", "该用户手机号不存在"),
    MOBILE_BLANK("500", "手机号为空"),
    CAPTCHA_USED("500", "验证码1分钟内有效，请勿重复发送"),
    USER_MOBILE_NOT_MATCH("500", "非该用户手机号"),
    NO_VERIFYKEY("500", "修改密码秘钥不正确"),
    MOBILE_REGISTERED("500", "手机号已注册");


    private String code;
    private String msg;

    SecurityStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

