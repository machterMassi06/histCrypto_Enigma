package com.mycrypto.EnigmaTest;

import com.mycrypto.enigma.EnigmaMachine;
import com.mycrypto.enigma.Plugboard;
import com.mycrypto.enigma.Reflector;
import com.mycrypto.enigma.Rotor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnigmaMachineTest {
    @Test
    public void TestFunctEnigma(){
        Rotor rotor1 = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q', 0);  // Rotor I
        Rotor rotor2 = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E', 0);  // Rotor II
        Rotor rotor3 = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V', 0);  // Rotor III

        Rotor[] rotors={rotor1,rotor2,rotor3};
        Reflector reflector = new Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT");  // Reflecteur B
        Plugboard plugboard = new Plugboard("AC,RX"); // connect borad

        EnigmaMachine enigma = new EnigmaMachine(rotors,reflector,plugboard);

        String text = "HELLO";
        StringBuilder encryptedText = new StringBuilder();

        for(char c :text.toCharArray()){
            encryptedText.append(enigma.encrypt(c));
        }

        for(int i=0;i<text.length();i++){
            enigma.revertRotorPositions();
        }
        StringBuilder decryptedText = new StringBuilder();
        for (char c:encryptedText.toString().toCharArray()){
            decryptedText.append(enigma.encrypt(c));
        }
        assertEquals(text, decryptedText.toString());
    }
}
