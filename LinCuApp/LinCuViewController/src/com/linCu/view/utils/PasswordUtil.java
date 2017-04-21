package com.linCu.view.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.RandomStringUtils;

public class PasswordUtil {
    public PasswordUtil() {
        super();
    }
    
    public static String encryptPassword(String password){
        String encryptedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = sb.toString();
            System.out.println("Hex format : " + encryptedPassword);
        } catch (NoSuchAlgorithmException nsae) {
            // TODO: Add catch code
            nsae.printStackTrace();
        }
       return encryptedPassword; 
    }
    
    public static String decryptPassword(String password){
        String decryptedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 2
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            decryptedPassword = hexString.toString();
            System.out.println("Hex format : " + decryptedPassword);
        } catch (NoSuchAlgorithmException nsae) {
            // TODO: Add catch code
            nsae.printStackTrace();
        }
        return decryptedPassword;
    }
    
    public static String generateRamdomPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$&";
        String pwd = RandomStringUtils.random( 10, characters );
        System.out.println( "-------------Random Password------------------"+pwd );
       return pwd; 
    }
}
