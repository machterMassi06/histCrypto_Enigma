package com.mycrypto.main;
import com.mycrypto.cipher.CaesarCipher;
public class Main {
    public static void main(String[] args) {
        String text = "HELLO WORLD";
        int key =3;
        String encryptedText = CaesarCipher.encrypt(text, key, false);
        System.out.println("key= "+key+" encrypted text = "+encryptedText);
        System.out.println("origin text= "+CaesarCipher.decrypt(encryptedText, key, false));
    }
}
