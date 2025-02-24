package com.mycrypto.cipher;

import com.mycrypto.utils.Utils;

public class ScytaleCipher {
    // Encrypt text or file content using the Scytale cipher
    public static String encrypt(String input , int key,boolean isFilePath){
        String text = isFilePath? Utils.readFile(input) : input;
        return process(text,key);
    }

    // Decrypt text or file content using the Scytale cipher
    public static String decrypt(String input , int key,boolean isFilePath){
        String text = isFilePath? Utils.readFile(input) : input;
        return process(text,-key);// Reverse shift for decryption
    }

    // General fct to process text for encryption/decryption
    public static String process(String text,int key){
        int len = text.length();
        int rows =(int) Math.ceil(len/(double) key);
        char[] res =new char[len];
        int i=0;

        for (int c=0;c<key;c++){
            for(int r=0;r<rows;r++){
                if(r*key + c < len){
                    res[i++]=text.charAt(r*key + c);
                }
            }
        }
        return new String(res);
    }
}
