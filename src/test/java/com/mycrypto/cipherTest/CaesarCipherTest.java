package com.mycrypto.cipherTest;

import com.mycrypto.cipher.CaesarCipher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaesarCipherTest {
    @Test
    public void testCaesarEncrypt(){
        String text = "HELLO WORLD";
        int key =3;
        String encryptedText = CaesarCipher.encrypt(text, key, false);
        assertEquals("KHOOR ZRUOG",encryptedText);
    }
}
