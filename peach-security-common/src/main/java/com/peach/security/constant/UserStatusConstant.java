package com.peach.security.constant;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 15:16
 */
public interface UserStatusConstant {

    /**
     * 启用
     */
    Integer ENABLE = 1;


    /**
     * 禁用
     */
    Integer DISABLE = 2;


    /**
     * 锁定
     */
    Integer LOCKED = 3;

    /**
     * 五分钟内密码错误的最大次数 超过这个次数将会被锁定
     */
    Integer MAX_ERROR_COUNT = 5;

    /**
     * 密码错误次数的锁定key
     */
    String PASSWORD_ERROR_LOCKED_COUNT_KEY_PRIX = "PASSWORD_ERROR_LOCKED_COUNT_KEY_";


    /**
     * 密码错误的锁定key
     */
    String PASSWORD_ERROR_LOCKED_LEVEL_KEY_PRIX = "PASSWORD_ERROR_LOCKED_LEVEL_KEY_";

    /**
     * 密码锁定惩罚追加 最多为2个小时
     */
    Integer [] PASSWORD_ERROR_LOCKED_LEVEL = {15,30,60,120};

}
