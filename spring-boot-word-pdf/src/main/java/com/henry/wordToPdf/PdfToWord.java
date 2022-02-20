package com.henry.wordToPdf;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @Description:  pdf转转word
 * @Author: henry
 * @Date: 2022/1/10
 */
public class PdfToWord {

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

    public static void main(String[] args) {
        getLicense();
        String pdfPath = "/Users/huangjiawen/software/wordToPdf/Java 基础核心总结_副本.pdf";
        String wordPath = "/Users/huangjiawen/software/wordToPdf/Java 基础核心总结_副本.docx";
        File file = new File(wordPath);
        try  {
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(pdfPath);
            doc.save(os, SaveFormat.DOCX);
            System.out.println("pdf转换word成功");
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public static void main(String[] args) {
    //    try{
    //        String pdfFile = "/Users/huangjiawen/software/wordToPdf/Java开发手册（嵩山版）.pdf";
    //        PDDocument doc = PDDocument.load(new File(pdfFile));
    //        int pagenumber = doc.getNumberOfPages();
    //        pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf("."));
    //        String fileName = pdfFile + ".docx";
    //        File file = new File(fileName);
    //        if (!file.exists())
    //        {
    //            file.createNewFile();
    //        }
    //        FileOutputStream fos = new FileOutputStream(fileName);
    //        Writer writer = new OutputStreamWriter(fos, "UTF-8");
    //        PDFTextStripper stripper = new PDFTextStripper();
    //        stripper.setSortByPosition(true);// 排序
    //        stripper.setStartPage(1);// 设置转换的开始页
    //        stripper.setEndPage(pagenumber);// 设置转换的结束页
    //        stripper.writeText(doc, writer);
    //        writer.close();
    //        doc.close();
    //        System.out.println("pdf转换word成功！");
    //    }
    //    catch (IOException e)
    //    {
    //        e.printStackTrace();
    //    }
    //}

}
