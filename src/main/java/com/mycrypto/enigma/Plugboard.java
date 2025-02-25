package com.mycrypto.enigma;

import java.util.HashMap;
import java.util.Map;

public class Plugboard {
    private final Map<Character, Character> connections;

    public Plugboard(String connectionsStr){
        this.connections=new HashMap<>();
        // Each pair of letters in connectionsStr must be of the form "AB,FZ,ED"
        String[] pairs=connectionsStr.split(",");
        for (String p:pairs){
            if (p.length()==2){
                char a =p.charAt(0);
                char b =p.charAt(1);
                connections.put(a,b);connections.put(b,a);
            }
        }
    }

    public char swap(char letter){
        // If the letter is connected to another, we exchange it
        return connections.getOrDefault(letter, letter);
    }
}
