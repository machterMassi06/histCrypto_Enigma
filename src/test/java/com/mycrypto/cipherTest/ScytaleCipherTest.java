package com.mycrypto.cipherTest;

import com.mycrypto.cipher.ScytaleCipher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScytaleCipherTest {
    @Test
    public void testScytaleEncrypt(){
        String text = "les amis nous attaquons demain a l aube le chateau fort";
        int key =5;
        String encryptedText = ScytaleCipher.encrypt(text, key, false);
        assertEquals("lunlaessatsadueatebamtmeuiaalfsqieonuncrooaht",encryptedText);
    }

    @Test
    public void testScytaleDecrypt(){
        String encryptedText = "lunlaessatsadueatebamtmeuiaalfsqieonuncrooaht";
        int key =5;
        String decryptedText = ScytaleCipher.decrypt(encryptedText, key, false);
        assertEquals("lesamisnousattaquonsdemainalaubelechateaufort",decryptedText);
    }

}
