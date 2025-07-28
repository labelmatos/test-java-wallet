package br.com.pay.wallet.util;

import java.security.MessageDigest;

public class HashUtil {

    public static String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
        for (byte b : encodedhash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
