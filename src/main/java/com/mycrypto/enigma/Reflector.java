package com.mycrypto.enigma;

public class Reflector {
    private final String wiring;

    public Reflector(String wiring){
        this.wiring=wiring;
    }

    public char reflect(char letter){
        int idx = letter - 'A';
        return wiring.charAt(idx);
    }
}
