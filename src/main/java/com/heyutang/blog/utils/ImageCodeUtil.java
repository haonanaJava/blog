package com.heyutang.blog.utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ImageCodeUtil {

    private static final Integer FONT_SIZE = 24;
    private static final Integer CODE_LENGTH = 4;


    /**
     * 随机获得颜色
     */
    private static Color getRandomColor() {
        Random random = new Random();
        int r = 1 + random.nextInt(200);
        int g = 1 + random.nextInt(200);
        int b = 1 + random.nextInt(200);
        return new Color(r, g, b);
    }

    /**
     * 生成随机图片
     */
    public static String getImageCode(HttpServletRequest request, HttpServletResponse response, String codeKey) {

        int width = FONT_SIZE * CODE_LENGTH;
        int height = FONT_SIZE + FONT_SIZE / 2;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 图片绘制
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);

        // 生成随机字符
        String codeStr = drawText(g);

        System.out.println("请求随机数：" + codeKey);
        System.out.println("随机验证码：" + codeStr);
        g.dispose();

        try {
            // 回写图片
            response.setContentType("image/jpeg");
            ImageIO.write(image, "JPG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return codeStr;
    }

    /**
     * 绘制字符串
     */
    private static String drawText(Graphics g) {
        // 生成字符串
        char[] chars = getRandomString(CODE_LENGTH);
        // 绘制文本
        for (int i = 1; i <= chars.length; i++) {
            char c = chars[i - 1];

            Font font = new Font("宋体", Font.BOLD, FONT_SIZE);
            g.setFont(font);

            g.setColor(getRandomColor());
            // 设置间距
            g.translate(5, 0);
            g.drawString(String.valueOf(c), (FONT_SIZE / 2) * i, FONT_SIZE);
        }
        // 返回字符串
        return new String(chars);
    }


    /**
     * 获取随机的字符
     */
    public static char[] getRandomString(int length) {
        String randomStr = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        char[] chars = randomStr.toCharArray();
        // 定义返回新的字符数组
        char[] newChars = new char[length];
        // 随机选择length个索引
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(randomStr.length());
            newChars[i] = chars[index];
        }
        return newChars;
    }
}


