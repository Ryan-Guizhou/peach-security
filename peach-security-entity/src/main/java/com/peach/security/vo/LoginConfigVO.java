package com.peach.security.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 17:39
 */
@Data
public class LoginConfigVO implements Serializable {

    private static final long serialVersionUID = -9102838295156842713L;

    /**
     * 加密公钥
     */
    private String publicKey;

    /**
     * 语言类型
     */
    private String language;

    /**
     * 滑块验证码的原始图片
     */
    private String sourceImage;

    /**
     * 滑块验证码的目标图片
     */
    private String targetImage;

    /**
     * 滑块验证码的Y轴坐标
     */
    private Integer Y;

    /**
     * 滑块验证码 token
     */
    private String token;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 背景图片地址
     */
    private String backgroundImage;

    /**
     * 验证类型
     */
    private Integer validateType;
}
