package com.mycrypto.enigma;

// version of 3 rotors among 5
public class EnigmaMachine {
    private final Rotor[] rotors ;
    private final Reflector reflector;
    private final Plugboard plugboard;

    public EnigmaMachine(Rotor[] rotors,Reflector reflector,Plugboard plugboard){
        this.rotors=rotors;
        this.reflector=reflector;
        this.plugboard=plugboard;
    }

    // Method to encrypt a letter
    public char encrypt(char letter){
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
