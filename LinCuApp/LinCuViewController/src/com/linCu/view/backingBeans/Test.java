package com.linCu.view.backingBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Test {


    public static void main(String[] args) {

//        String zipFile = "C:/Test/archive.zip";
//        String[] srcFiles = { "C:/Test/GaneshMarksList.pdf", "C:/Test/imp.txt", "C:/Test/photo.jpg" };
//        try {
//            // create byte buffer
//            byte[] buffer = new byte[1024];
//            FileOutputStream fos = new FileOutputStream(zipFile);
//            ZipOutputStream zos = new ZipOutputStream(fos);
//            for (int i = 0; i < srcFiles.length; i++) {
//                File srcFile = new File(srcFiles[i]);
//                FileInputStream fis = new FileInputStream(srcFile);
//                // begin writing a new ZIP entry, positions the stream to the start of the entry data
//                zos.putNextEntry(new ZipEntry(srcFile.getName()));
//                int length;
//                while ((length = fis.read(buffer)) > 0) {
//                    zos.write(buffer, 0, length);
//                }
//                zos.closeEntry();
//                // close the InputStream
//                fis.close();
//            }
//            // close the ZipOutputStream
//            zos.close();
        SimpleDateFormat format = new SimpleDateFormat("YYDDD");
        String date = format.format(new Date());
        System.out.println(date);
//        }
//        catch (IOException ioe) {
//            System.out.println("Error creating zip file: " + ioe);
//        }
    }

}
