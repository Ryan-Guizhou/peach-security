package com.peach.security.util;


/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description 除本人外，这些敏感信息都需要加密显示
 * @CreateTime 2025/3/17 11:00
 */
public class UserInfoMaskUtil {

    /**
     * 脱敏手机号：138****5678
     *
     * @param phone 手机号
     */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() != 11) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 脱敏身份证号：42010*********6789
     * @param idCard 身份证号
     */
    public static String maskIdCard(String idCard) {
        if (idCard == null || idCard.length() < 10) {
            return idCard;
        }
        return idCard.substring(0, 6) + "********" + idCard.substring(idCard.length() - 4);
    }

    /**
     * 脱敏邮箱：h****@domain.com
     * @param email 邮箱号
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        if (parts[0].length() < 2) return "*@" + parts[1];
        return parts[0].substring(0, 1) + "****@" + parts[1];
    }

    public static void main(String[] args) {
        System.out.println(maskPhone("18886200426"));
        System.out.println(maskIdCard("520198199999992056"));
        System.out.println(maskEmail("88888888888@gmail.com"));
    }
}
