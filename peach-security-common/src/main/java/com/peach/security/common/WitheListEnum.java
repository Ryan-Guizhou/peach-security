package com.peach.security.common;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/11 21:15
 */
public interface WitheListEnum {

    enum WitheListTypeEnum implements WitheListEnum {

        IPV4("IPV4"),
        IPV6("IPV6");

        private final String code;

        WitheListTypeEnum(final String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

    enum WitheListStatusEnum implements WitheListEnum {

        ENABLE("ENABLE","启用",1),
        DISABLE("DISABLE","禁用",0);

        private final String code;

        private final String name;

        private final Integer value;

        WitheListStatusEnum(final String code,final String name, final Integer value) {
            this.code = code;
            this.name = name;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }
    }
}
