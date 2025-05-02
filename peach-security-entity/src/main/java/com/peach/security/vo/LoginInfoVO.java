package com.peach.security.vo;

import com.peach.common.generator.EntityGenerator;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/22 17:25
 */
@Data
public class LoginInfoVO {

//    private List<> ;

    private static final List<String> tableList = new ArrayList<String>();

    static {
        tableList.add("peach_user");
//        tableList.add("peach_auth_function");
//        tableList.add("peach_auth_party");
//        tableList.add("peach_auth_resource");
//        tableList.add("peach_menu");
//        tableList.add("peach_role");
//        tableList.add("peach_router");
//        tableList.add("peach_router");
//        tableList.add("peach_app_function");

    }

    public static void main(String[] args) {
        for (String s : tableList) {
            EntityGenerator.generateEntity(s);
        }

    }
}
