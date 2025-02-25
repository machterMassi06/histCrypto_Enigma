package com.mycrypto.cipherTest;

import com.mycrypto.cipher.VigenereCipher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VigenereCipherTest {
    @Test
    public void testVigenereEncrypt(){
        String text = "chiffredevigenere";
        String key ="KEY";
        String encryptedText = VigenereCipher.encrypt(text, key, false);
        assertEquals("mlgpjpohcfmeorcbi",encryptedText);
    }
    @Test
    public void testVigenereDecrypt(){
        String encryptedText = "mlgpjpohcfmeorcbi";
        String key ="KEY";
        String decryptedText = VigenereCipher.decrypt(encryptedText, key, false);
        assertEquals("chiffredevigenere",decryptedText);
    }
}
