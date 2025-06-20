package com.peach.security.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/21 0:12
 */
public class UserAvarGenerator {


    /**
     * 生成头像图像
     * @param username 用户名
     * @return BufferedImage 对象
     */
    public static BufferedImage generateAvatar(String username) {
        // 1. 获取展示文字（后两个字符或完整英文名）
        String text = extractDisplayText(username);

        // 2. 设置图像参数
        int width = 200;
        int height = 200;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();

        // 3. 设置抗锯齿和渲染质量
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 4. 生成背景颜色（根据名字哈希）
        Color bgColor = generateColorByName(username);
        g2.setColor(bgColor);
        g2.fillRect(0, 0, width, height);

        // 5. 设置字体颜色（可根据背景色判断深浅做反差）
        g2.setColor(Color.WHITE);

        // 6. 设置字体（中文、英文都可用）
        Font font = new Font("SansSerif", Font.BOLD, 80);
        g2.setFont(font);

        // 7. 文字居中绘制
        FontMetrics fm = g2.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(text, x, y);

        g2.dispose();
        return image;
    }

    /**
     * 获取文件的MD5值
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        try (InputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] digest = md.digest();
            return bytesToHex(digest);
        } catch (Exception e) {
            throw new RuntimeException("计算文件MD5失败", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 截取需要用作头像的汉字或者字母
     * @param username 姓名
     * @return
     */
    private static String extractDisplayText(String username) {
        if (username == null || username.isEmpty()) {
            return "用户";
        }

        String cleaned = username.trim();

        // 处理英文名（只保留前2个字母）
        if (cleaned.matches("^[a-zA-Z\\s]+$")) {
            cleaned = cleaned.replaceAll("\\s+", ""); // 去空格
            return cleaned.length() <= 2 ? cleaned : cleaned.substring(0, 2).toUpperCase();
        }

        // 中文、其他语言取最后两个字
        return cleaned.length() <= 2 ? cleaned : cleaned.substring(cleaned.length() - 2);
    }

    /**
     * 根据姓名生成背景图或者字体颜色
     * @param name 名字
     * @return
     */
    private static Color generateColorByName(String name) {
        int hash = Math.abs(name.hashCode());

        float hue = (hash % 360);               // 色相分布在整圆上
        float saturation = 0.7f;                // 饱和度（鲜艳）
        float brightness = 0.85f;               // 明度（阳光）

        return Color.getHSBColor(hue / 360f, saturation, brightness);
    }


}
