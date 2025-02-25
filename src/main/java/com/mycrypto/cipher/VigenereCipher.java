package com.mycrypto.cipher;

import com.mycrypto.utils.Utils;

enum Mode {
    ENCRYPT,
    DECRYPT,
}
public class VigenereCipher {
    // Encrypt text or file content using the vigenre Cipher
    public static String encrypt(String input , String key,boolean isFilePath){
        String text = isFilePath? Utils.readFile(input) : input;
        return process(text,key,Mode.ENCRYPT);
    }

    // Decrypt text or file content using the vigenre Cipher
    public static String decrypt(String input , String key,boolean isFilePath){
        String text = isFilePath? Utils.readFile(input) : input;
        return process(text,key,Mode.DECRYPT);// Reverse shift for decryption
    }

    // General fct to process text for encryption/decryption
    private static String process(String text,String key,Mode mode){
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int kIndex=0;
        for (char c :text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shift = key.charAt(kIndex % key.length())-'A';
                if (mode==Mode.DECRYPT){
                    shift=-shift;
                }
                result.append((char) ((c - base + shift + 26) % 26 + base));
                kIndex++;
            } else {
                result.append(c); // Keep non-alphabetic(blanc)characters unchanged
            }
        }
        return result.toString();
    }


}
