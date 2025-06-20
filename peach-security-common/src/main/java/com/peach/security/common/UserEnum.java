package com.peach.security.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 14:38
 */
public interface UserEnum {

    enum UserStatus implements UserEnum{

        /**
         * 启用
         */
        ENABLE(1,"启用"),

        /**
         * 禁用
         */
        DISABLE(2,"禁用"),

        /**
         * 锁定
         */
        DELETE(3,"锁定");

        private final int value;

        private final String desc;

        public static final Map<Integer, UserStatus> USER_STATUS_MAP = new HashMap<Integer, UserStatus>();

        static {
            for (UserStatus status : UserStatus.values()) {
                USER_STATUS_MAP.put(status.getValue(), status);
            }
        }


        UserStatus(int value,String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }


    }

    enum ChanegReason implements UserEnum{
        /**
         * 系统生成
         */
        SYSTEM_UPLOAD("SYSTEM_UPLOAD","系统生成"),

        /**
         * 用户上传
         */
        USER_UPLOAD("USER_UPLOAD","用户上传");

        private final String value;

        private final String desc;


        ChanegReason(String value,String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }


    }
}
