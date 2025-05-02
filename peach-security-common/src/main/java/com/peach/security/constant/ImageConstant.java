package com.peach.security.constant;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/17 11:19
 */
public interface ImageConstant {

    /**
     * 原始图片缓存key
     */
    String ORI_IAMGE_CACHE_KEY = "IAMGE_CACHE_KEY";

    /**
     * 模板图片缓存key
     */
    String TEMPLATE_IMAGE_CACHE_KEY = "TEMPLATE_CACHE_KEY";

    /**
     * 验证码token
      */
    String VERIFICATION_CODE_TOKEN = "VERIFICATION_CODE_TOKEN";

    /**
     * 图片过期时间
     */
    Long IAMGE_EXPIRE_TIME = 6L;

    /**
     * token过期时间
     */
    Long TOKEN_TIMEOUT = 300L;

    /**
     * 背景图片宽高
     */
    int BACKGROUND_IMAGE_WIDTH = 328;

    /**
     * 背景图片高
     */
    int BACKGROUND_IMAGE_HEIGHT = 124;

    /**
     * 模板图片宽高
     */
    int TEMPLATE_WIDTH = 52;

    /**
     * 模板图片高
     */
    int TEMPLATE_HEIGHT = 124;
}
