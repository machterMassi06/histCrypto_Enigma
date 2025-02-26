package com.mycrypto.ui;

import com.mycrypto.enigma.EnigmaMachine;
import com.mycrypto.enigma.Plugboard;
import com.mycrypto.enigma.Reflector;
import com.mycrypto.enigma.Rotor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EnigmaConfigUI extends Application {

    private EnigmaMachine enigmaMachine;
    private TextField rotorPositionField, trainingPositionField, plugboardField;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Enigma Machine Configuration");

        rotorPositionField = createTextField("0,0,0");
        trainingPositionField = createTextField("Q,E,V");
        plugboardField = createTextField("");
        plugboardField.setPromptText("ex: AC,BZ");

        Button runButton = new Button("Run");
        runButton.setOnAction(e -> runEnigmaMachine());

        VBox configLayout = new VBox(10);
        configLayout.setPadding(new Insets(20));

        configLayout.getChildren().addAll(
                new Label("Rotor Positions (format: 0,0,0): "), rotorPositionField,
                new Label("Training Positions (format: Q,E,V): "), trainingPositionField,
                new Label("Plugboard Config (format: AC,BZ): "), plugboardField,
                runButton
        );

        Scene scene = new Scene(configLayout, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void runEnigmaMachine() {
        // read config from user input
        String[] rotorPositions = rotorPositionField.getText().split(",");
        int rotorPos1 = Integer.parseInt(rotorPositions[0].trim());
        int rotorPos2 = Integer.parseInt(rotorPositions[1].trim());
        int rotorPos3 = Integer.parseInt(rotorPositions[2].trim());

        String[] trainingPositions = trainingPositionField.getText().split(",");
        char trainingPos1 = trainingPositions[0].trim().charAt(0);
        char trainingPos2 = trainingPositions[1].trim().charAt(0);
        char trainingPos3 = trainingPositions[2].trim().charAt(0);

        String plugboardConfig = plugboardField.getText();

        // config
        Rotor rotor1 = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", trainingPos1, rotorPos1);
        Rotor rotor2 = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", trainingPos2, rotorPos2);
        Rotor rotor3 = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", trainingPos3, rotorPos3);

        // reflect B as default
        Reflector reflector = new Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT");

        // plugboard config
        Plugboard plugboard = new Plugboard(plugboardConfig);

        // init EnigmaMachine
        enigmaMachine = new EnigmaMachine(new Rotor[]{rotor1, rotor2, rotor3}, reflector, plugboard);

        // Open window
        openEnigmaEncryptionWindow();
    }

    private void openEnigmaEncryptionWindow() {
        Stage enigmaStage = new Stage();
        EnigmaUi enigmaUi = new EnigmaUi(enigmaMachine);
        enigmaUi.start(enigmaStage);
        enigmaStage.show();
    }

    private TextField createTextField(String defaultValue) {
        TextField textField = new TextField(defaultValue);
        textField.setPrefWidth(150);
        return textField;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
