package com.peach.security.util;

import com.google.common.collect.Maps;
import com.peach.common.constant.PubCommonConst;
import com.peach.common.enums.StatusEnum;
import com.peach.common.util.StringUtil;
import com.peach.common.util.ThrowUtil;
import com.peach.security.constant.ImageConstant;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/17 11:00
 */
@Slf4j
public class VerifyImageUtil {

    /**
     * 源文件宽度
     */
    private static int ORI_WIDTH = 590;

    /**
     * 源文件高度
     */
    private static int ORI_HEIGHT = 360;

    /**
     * 抠图坐标x
     */
    private static int X;

    /**
     * 抠图坐标y
     */
    private static int Y;

    /**
     * 抠图宽度
     */
    private static int WIDTH;

    /**
     * 抠图高度
     */
    private static int HEIGHT;

    /**
     * 源文件X轴移动百分比
     */
    private static float xPercent;

    /**
     * 源文件Y轴移动百分比
     */
    private static float yPercent;

    /**
     * 裁剪模板图片
     * @param templateImage 模板图片
     * @param targetImage 目标图片
     * @param templateImageType 模板图片类型
     * @param targetImageType 目标图片类型
     * @return
     * @throws Exception
     */
    public static Map<String, Object> pictureTemplatesCut(byte[] templateImage, byte[] targetImage, String templateImageType, String targetImageType) throws Exception {
        ThrowUtil.throwIf(StringUtil.isBlank(templateImage) || StringUtil.isBlank(targetImage), StatusEnum.PARAM_ERROR,"参数错误,模板图片和目标图片的类型不能为空");
//        Map<String, Object> resultMap = Maps.newHashMap();
        // 1、读取并缩放
        // 1.1、模板图片 (imageTemplate)
        BufferedImage templateBufferedImage = ImageIO.read(new ByteArrayInputStream(templateImage));
        templateBufferedImage = scaleBufferedImage(templateBufferedImage, ImageConstant.TEMPLATE_WIDTH, ImageConstant.TEMPLATE_WIDTH, PubCommonConst.FALSE);
        WIDTH = templateBufferedImage.getWidth();
        HEIGHT = templateBufferedImage.getHeight();

        // 1.2、目标图片 (imageTarget)
        BufferedImage targetBufferedImage = ImageIO.read(new ByteArrayInputStream(targetImage));
        targetBufferedImage = scaleBufferedImage(targetBufferedImage, ImageConstant.BACKGROUND_IMAGE_WIDTH, ImageConstant.BACKGROUND_IMAGE_HEIGHT, PubCommonConst.FALSE);
        ORI_HEIGHT = targetBufferedImage.getHeight();
        HEIGHT = targetBufferedImage.getHeight();

        // 2、生成随机切图坐标
        generateCutoutCoordinates();

        // 3、生成滑块并美化
        BufferedImage newImage = getBufferedImage(targetImage, templateBufferedImage, targetImageType);

        // 4、返回结果
        Map<String, Object> pictureMap = Maps.newHashMap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。
        ImageIO.write(newImage, templateImageType, os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        byte[] newImages = os.toByteArray();
        pictureMap.put("newImage", newImages);
        // 源图生成遮罩
        BufferedImage oriImage = targetBufferedImage;
        byte[] oriCopyImages = dealOriPictureByTemplate(oriImage, templateBufferedImage, X, Y);
        pictureMap.put("oriCopyImage", oriCopyImages);
        pictureMap.put("X", X);
        pictureMap.put("Y", Y);
        return pictureMap;
    }

    /**
     * 抠图后原图生成
     *
     * @param oriImage
     * @param templateImage
     * @param x
     * @param y
     * @return
     * @throws Exception
     */
    private static byte[] dealOriPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage, int x,
                                                   int y) throws Exception {
        // 源文件备份图像矩阵 支持alpha通道的rgb图像
        BufferedImage ori_copy_image = new BufferedImage(oriImage.getWidth(), oriImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        // 源文件图像矩阵
        int[][] oriImageData = getData(oriImage);
        // 模板图像矩阵
        int[][] templateImageData = getData(templateImage);

        //copy 源图做不透明处理
        for (int i = 0; i < oriImageData.length; i++) {
            for (int j = 0; j < oriImageData[0].length; j++) {
                int rgb = oriImage.getRGB(i, j);
                int r = (0xff & rgb);
                int g = (0xff & (rgb >> 8));
                int b = (0xff & (rgb >> 16));
                //无透明处理
                rgb = r + (g << 8) + (b << 16) + (255 << 24);
                ori_copy_image.setRGB(i, j, rgb);
            }
        }
        //定义颜色
        int color = Color.black.getRGB();
        int color2 = Color.white.getRGB();
        int backColor = new Color(36, 6, 7).getRGB();//背景阴影颜色
        for (int i = 0; i < templateImageData.length; i++) {
            for (int j = 0; j < templateImageData[0].length - 5; j++) {
                int rgb = templateImage.getRGB(i, j);
                //对源文件备份图像(x+i,y+j)坐标点进行透明处理
                if (rgb != color && rgb != color2 && rgb < 0) {
                    int rgb_ori = ori_copy_image.getRGB(x + i, y + j);
                    int r = (0xff & rgb_ori);
                    int g = (0xff & (rgb_ori >> 8));
                    int b = (0xff & (rgb_ori >> 16));
                    ori_copy_image.setRGB(x + i, y + j, backColor);
                } else {
                    //do nothing
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。
        ImageIO.write(ori_copy_image, "png", os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        byte b[] = os.toByteArray();//从流中获取数据数组。
        return b;
    }

    /**
     * 获取抠图、美化后的新图片
     * @param targetFile
     * @param imageTemplate
     * @param targetFiletype
     * @return
     * @throws Exception
     */
    private static BufferedImage getBufferedImage(byte[] targetFile, BufferedImage imageTemplate, String targetFiletype) throws Exception {
        BufferedImage newImage = new BufferedImage(WIDTH, HEIGHT, imageTemplate.getType());
        Graphics2D graphics = newImage.createGraphics();
        graphics.setBackground(Color.white);
        int bold = 5;
        // 获取感兴趣的目标区域——获取颜色
        BufferedImage targetImageNoDeal = getTargetArea(X, Y, WIDTH, HEIGHT, new ByteArrayInputStream(targetFile), targetFiletype);
        // 根据模板图片抠图
        newImage = dealCutPictureByTemplate(targetImageNoDeal, imageTemplate, newImage);

        // 设置“抗锯齿”的属性
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(newImage, 0, 0, null);
        graphics.dispose();
        return newImage;
    }

    /**
     * 根据模板图片抠图
     *
     * @param oriImage
     * @param templateImage
     * @return
     */

    private static BufferedImage dealCutPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage,
                                                          BufferedImage targetImage) throws Exception {
        // 源文件图像矩阵
        int[][] oriImageData = getData(oriImage);
        // 模板图像矩阵
        int[][] templateImageData = getData(templateImage);
        // 模板图像宽度
        try {
            int color = Color.black.getRGB();
            int color2 = Color.white.getRGB();
            int temp = 0;
            int count = 0;
            int borderColor = new Color(81, 198, 251).getRGB();
            for (int i = 0; i < templateImageData.length; i++) {
                // 模板图片高度
                for (int j = 0; j < templateImageData[0].length; j++) {
                    // 如果模板图像当前像素点不是白色 copy源文件信息到目标图片中
                    int rgb = templateImageData[i][j];
                    if (rgb != color && rgb != color2 && rgb < 0) {
                        targetImage.setRGB(i, j, oriImageData[i][j]);
                        temp = j;
                        if (count <= 0) {//抠图描边
                            targetImage.setRGB(i, j, borderColor);
                            targetImage.setRGB(i, j + 1, borderColor);
                            count = 1;
                        }
                    }
                }
                count = 0;
                long rgb2 = templateImageData[i][temp];
                if (rgb2 != color && rgb2 < 0) {//抠图描边
                    targetImage.setRGB(i, temp, borderColor);
                    targetImage.setRGB(i, temp + 1, borderColor);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {/*数组越界错误处理，这样页面就不会返回图像问题。*/
            log.error("X:" + X + "||Y:" + Y, e);
        } catch (Exception e) {
            log.error("X:" + X + "||Y:" + Y, e);
        }
        return targetImage;
    }

    /**
     * 生成图像矩阵
     *
     * @param
     * @return
     * @throws Exception
     */
    private static int[][] getData(BufferedImage bimg) throws Exception {
        int[][] data = new int[bimg.getWidth()][bimg.getHeight()];
        for (int i = 0; i < bimg.getWidth(); i++) {
            for (int j = 0; j < bimg.getHeight(); j++) {
                data[i][j] = bimg.getRGB(i, j);
            }
        }
        return data;
    }

    /**
     * 获取目标区域
     *
     * @param x            随机切图坐标x轴位置
     * @param y            随机切图坐标y轴位置
     * @param targetWidth  切图后目标宽度
     * @param targetHeight 切图后目标高度
     * @param ois          源文件输入流
     * @return
     * @throws Exception
     */
    private static BufferedImage getTargetArea(int x, int y, int targetWidth, int targetHeight, InputStream ois,
                                               String filetype) throws Exception {
        Iterator<ImageReader> imageReaderList = ImageIO.getImageReadersByFormatName(filetype);
        ImageReader imageReader = imageReaderList.next();
        // 获取图片流
        ImageInputStream iis = ImageIO.createImageInputStream(ois);
        // 输入源中的图像将只按顺序读取
        imageReader.setInput(iis, true);

        ImageReadParam param = imageReader.getDefaultReadParam();
        Rectangle rec = new Rectangle(x, y, targetWidth, targetHeight);
        param.setSourceRegion(rec);
        BufferedImage targetImage = imageReader.read(0, param);
        return targetImage;
    }

    /**
     * 对模板图片和目标图片进行缩放
     * @param source 源图片
     * @param targetW 目标图片宽度
     * @param targetH 目标图片高度
     * @param flag 是否同比例调整
     * @return
     */
    private static BufferedImage scaleBufferedImage(BufferedImage source, int targetW, int targetH, boolean flag) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (flag && sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else if (flag && sx <= sy) {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * 随机生成切图坐标
     */
    private static void generateCutoutCoordinates() {
        Random random;
        try {
            random = new SecureRandom();
            int widthDifference = ORI_WIDTH - WIDTH;
            int heightDifference = ORI_HEIGHT - HEIGHT;

            if (widthDifference <= 0) {
                X = 5;
            } else {
                X = random.nextInt(ORI_WIDTH - WIDTH);
                if (X < WIDTH) {/*@herb 解决切图相对位置问题*/
                    X = WIDTH;
                }
            }

            Y = heightDifference <= 0 ? 5 : random.nextInt(heightDifference);

            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);

            xPercent = Float.parseFloat(numberFormat.format((float) X / (float) ORI_WIDTH));
            yPercent = Float.parseFloat(numberFormat.format((float) Y / (float) ORI_HEIGHT));
        } catch (Exception e) {
            log.error("随机生成切图坐标失败:"+e.getMessage(),e);
            throw new RuntimeException("随机生成切图坐标失败");
        }

    }
}
