package com.peach.security.common;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 1:26
 */
public enum ResourceTypeEnum {
    API("API","API"),
    MENU("MENU","菜单"),
    BUTTON("BUTTON","按钮");

    /**
     * 编码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    ResourceTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
