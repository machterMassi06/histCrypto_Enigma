package com.mycrypto.enigma;

import java.util.Stack;

// version of 3 rotors among 5
public class EnigmaMachine {
    private final Rotor[] rotors ;
    private final Reflector reflector;
    private final Plugboard plugboard;
    private Stack<int[]> rotorPositionsStack;

    public EnigmaMachine(Rotor[] rotors,Reflector reflector,Plugboard plugboard){
        this.rotors=rotors;
        this.reflector=reflector;
        this.plugboard=plugboard;
        this.rotorPositionsStack =new Stack<>();
    }

    private void saveRotorPositions() {
        int[] positions = new int[rotors.length];
        for (int i = 0; i < rotors.length; i++) {
            positions[i] = rotors[i].getPosition();
        }
        rotorPositionsStack.push(positions);
    }

    // Return to the previous state of the rotors
    public void revertRotorPositions() {
        if (!rotorPositionsStack.isEmpty()) {
            int[] previousPositions = rotorPositionsStack.pop();
            for (int i = 0; i < rotors.length; i++) {
                rotors[i].setPosition(previousPositions[i]);
            }
        }
    }

    // Method to encrypt a letter (tape)
    public char encrypt(char letter){
        saveRotorPositions();
        letter =plugboard.swap(letter); //connection board

        //Go through each rotor from left to right
        for(Rotor r:rotors){
            letter=r.forward(letter);
        }

        // Reflector
        letter =reflector.reflect(letter);

        // inverse function (f-1)
        for(int i=rotors.length-1;i>=0;i--){
            letter=rotors[i].backward(letter);
        }

        //Go through the plugboard again after doing the reverse
        letter=plugboard.swap(letter);

        // rotates rotors
        rotateRotors();

        // Return encrypted letter
        return letter;

    }

    // The left rotor spins each time a key is pressed.
    // The rotor next to it turns one step each time the first rotor has made a complete revolution.
    // The right rotor also turns one notch when the second rotor has gone through its 26 positions
    private void rotateRotors(){
        rotors[0].rotate();
        if (rotors[0].atTrainingPos()){
            rotors[1].rotate();
        }
        if (rotors[1].atTrainingPos()){
            rotors[2].rotate();
        }
    }
}
