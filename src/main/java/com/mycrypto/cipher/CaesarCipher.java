package com.mycrypto.cipher;

import com.mycrypto.utils.Utils;

public class CaesarCipher {
    // Encrypt text or file content using the Caesar cipher
    public static String encrypt(String input , int key,boolean isFilePath){
        String text = isFilePath? Utils.readFile(input) : input;
        return process(text,key);
    }

    // Decrypt text or file content using the Caesar cipher
    public static String decrypt(String input , int key,boolean isFilePath){
        String text = isFilePath? Utils.readFile(input) : input;
        return process(text,key-(key%26));// Reverse shift for decryption
    }

    // General fct to process text for encryption/decryption
    private static String process(String text,int key){
        StringBuilder result = new StringBuilder();
        key = key % 26; // Keep key within valid range

        for (char c :text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) ((c - base + key) % 26 + base));
            } else {
                result.append(c); // Keep non-alphabetic(blanc)characters unchanged
            }
        }
        return result.toString();
    }
}
