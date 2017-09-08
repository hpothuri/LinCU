package com.linCu.view.backingBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Test {


    public static void main(String[] args) {

try {
            String todayAsString = new SimpleDateFormat("ddMMyyyy").format(new Date());
    System.out.println(todayAsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
