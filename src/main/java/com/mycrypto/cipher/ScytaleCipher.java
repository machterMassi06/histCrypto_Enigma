package com.mycrypto.cipher;

import com.mycrypto.utils.Utils;

public class ScytaleCipher {
    // Encrypt text or file content using the Scytale cipher
    public static String encrypt(String input , int key,boolean isFilePath){
        String text = isFilePath? Utils.readFile(input) : input;
        // Remove spaces to simplify encryption
        text = text.replaceAll("\\s", "");
        int len = text.length();
        int cols = (int) Math.ceil((double) len / key);
        char[] res =new char[len];
        int i=0;

        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < key; r++) {
                int charIndex = r * cols + c;
                if (charIndex < text.length()) {
                    res[i++] = text.charAt(charIndex);
                }
            }
        }
        return new String(res);
    }

    // Decrypt text or file content using the Scytale cipher
    public static String decrypt(String input , int key,boolean isFilePath){
        input = input.replaceAll("\\s", "");
        int len = input.length();
        int cols = (int) Math.ceil((double) len / key);
        char[][] result = new char[key][cols];
        int index = 0;
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < key; row++) {
                if (index < len) {
                    result[row][col] = input.charAt(index++);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < key; row++) {
            for (int col = 0; col < cols; col++) {
                if (result[row][col] != 0) {
                    sb.append(result[row][col]);
                }
            }
        }

        return sb.toString();
    }
}
