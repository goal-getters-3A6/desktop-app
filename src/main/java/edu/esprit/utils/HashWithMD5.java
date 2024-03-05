package edu.esprit.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashWithMD5 {
    private static MessageDigest md;

    public static String hashWithMD5(String pass) {

        byte[] msg = pass.getBytes();

        byte[] hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(msg);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuilder strBuilder = new StringBuilder();
        for (byte b : hash) {
            strBuilder.append(String.format("%02x", b));
        }
        String strHash = strBuilder.toString();
        return strHash;

    }
}
