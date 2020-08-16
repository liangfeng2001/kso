package com.ekek.tftcooker.utils;

public class StringUtils {

    public static boolean isNullOrEmpty(String value) {
        if (value == null) {
            return true;
        }
        return value.trim().equals("");
    }
    public static String byte2hex(byte[] b, char separator) {
        if (b == null) return null;
        if (b.length == 0) return "";
        StringBuffer sb = new StringBuffer(b.length);
        String s = "";
        for (int i = 0; i < b.length; i++) {
            s = Integer.toHexString(b[i] & 0xFF).toUpperCase();
            if (s.length() == 1) {
                sb = sb.append("0").append(s);
            } else {
                sb = sb.append(s);
            }
            if (separator != '\0' && i != b.length - 1) {
                sb.append(separator);
            }
        }

        return String.valueOf(sb);
    }
}
