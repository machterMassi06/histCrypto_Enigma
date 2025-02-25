package com.mycrypto.enigma;

public class Rotor {
    private final String wiring;
    private final char trainingPos; // enigma training position
    private int pos;

    public Rotor(String wiring,char trainingPos,int initPos){
        this.wiring=wiring;
        this.trainingPos=trainingPos;
        this.pos=initPos;
    }

    public void rotate(){
        pos=pos+1 %26;
    }

    public char forward(char letter){
        int idx = (letter -'A' + pos)%26;
        return wiring.charAt(idx);
    }

    public char backward(char letter){
        int idx = wiring.indexOf(letter) - pos;
        int shiftedIdx = (idx - pos + 26) % 26;
        return (char) ('A'+shiftedIdx);
    }

    public boolean atTrainingPos(){
        return wiring.charAt(pos)==trainingPos;
    }

}
