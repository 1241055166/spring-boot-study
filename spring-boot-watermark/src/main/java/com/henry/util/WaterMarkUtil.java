package com.henry.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 *   图片水印工具类
 *
 */
public class WaterMarkUtil {

    private static Logger logger = LoggerFactory.getLogger(WaterMarkUtil.class);

    // 水印文字字体
    private static Font font = new Font("宋体", Font.BOLD, 20);
    // 水印文字颜色
    private static Color color = Color.red;
    // 水印透明度
    private static float alpha = 0.3f;
    // 水印横向位置
    private static int positionWidth = 100;
    // 水印纵向位置
    private static int positionHeight = 200;


    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     */
    public static void ImagemarkWater(String text, InputStream inputStream, OutputStream outputStream,
                                     Integer degree, String typeName) {
        try {
            // 1、源图片
            Image srcImg = ImageIO.read(inputStream);

            int imgWidth = srcImg.getWidth(null);
            int imgHeight = srcImg.getHeight(null);
            BufferedImage buffImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)

            g.drawString(text, positionWidth, positionHeight);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            ImageIO.write(buffImg, typeName.toUpperCase(), outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //文件保存路径
        String targetFilePath = "/Users/huangjiawen/software/exportFile/waterPrint";
        //重命名保存新文件的文件名
        String fileName = UUID.randomUUID().toString().replace("-", "");

        File targetFile = new File(targetFilePath + File.separator + fileName+".jpg");

        try{
            // 源文件
            String originalFilename = "/Users/huangjiawen/software/exportFile/waterPrint/16272274161bd5.jpg";
            String typeName = originalFilename.substring(originalFilename.indexOf(".")+1 ,originalFilename.length());
            InputStream inputStream = new FileInputStream(originalFilename);
            OutputStream outputStream = new FileOutputStream(targetFile) ;
            //调用添加水印的方法
            WaterMarkUtil.ImagemarkWater("你打水印了吗", inputStream, outputStream, 0, typeName);
            outputStream.close();
            inputStream.close();
            logger.info("------图片上传、添加水印成功------");

            //String originalFilename = imgFile.getOriginalFilename();
            //String typeName = originalFilename.substring(originalFilename.indexOf(".")+1 ,originalFilename.length());
            //InputStream inputStream = imgFile.getInputStream();
            //OutputStream outputStream = new FileOutputStream(targetFile) ;
            ////调用添加水印的方法
            //WaterMarkUtil.ImagemarkWater("1024笔记", inputStream, outputStream, 0, typeName);
            //outputStream.close();
            //inputStream.close();
            //logger.info("------图片上传、添加水印成功------");
        }catch(IOException e){

        }

    }





}
