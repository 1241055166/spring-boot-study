package com.henry.wordToPdf;


import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @Description:  word转pdf
 * @Author: henry
 * @Date: 2022/1/10
 */
public class WordToPdf {

    /**
     * 加载license 用于破解 不生成水印
     *
     * @author Henry
     * @date 2022/1/10 13:51
     */
    @SneakyThrows
    private static void getLicense() {
        try (InputStream is = WordToPdf.class.getClassLoader().getResourceAsStream("License.xml")) {
            License license = new License();
            license.setLicense(is);
        }
    }

    /**
     * word转pdf
     *
     * @param wordPath word文件保存的路径
     * @param pdfPath  转换后pdf文件保存的路径
     * @author Henry
     * @date 2022/1/10 13:51
     */
    @SneakyThrows
    public static void wordToPdf(String wordPath, String pdfPath) {
        getLicense();
        wordPath = "/Users/huangjiawen/software/wordToPdf/jenkins部署微服务.docx";
        pdfPath = "/Users/huangjiawen/software/wordToPdf/jenkins部署微服务.pdf";
        File file = new File(pdfPath);
        try (FileOutputStream os = new FileOutputStream(file)) {
            Document doc = new Document(wordPath);
            doc.save(os, SaveFormat.PDF);
        }
    }

    public static void main(String[] args) {
        getLicense();
        String wordPath = "/Users/huangjiawen/software/wordToPdf/jenkins部署微服务.docx";
        String pdfPath = "/Users/huangjiawen/software/wordToPdf/jenkins部署微服务.pdf";
        File file = new File(pdfPath);
        try  {
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);
            doc.save(os, SaveFormat.PDF);
            System.out.println("word转换pdf成功");
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
