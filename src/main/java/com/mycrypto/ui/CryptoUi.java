package com.mycrypto.ui;

import com.mycrypto.cipher.CaesarCipher;
import com.mycrypto.cipher.ScytaleCipher;
import com.mycrypto.cipher.VigenereCipher;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CryptoUi extends Application {

    private ComboBox<String> methodChoice;
    private TextField inputText;
    private TextField keyText;
    private TextArea resultTextArea;
    private ToggleGroup modeGroup;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Encryption/Decryption Application");

        Button EnigmaButton = new Button("Enigma Machine");
        EnigmaButton.setOnAction(e -> openEnigmaConfigUI());


        methodChoice = new ComboBox<>();
        methodChoice.getItems().addAll("Caesar", "Vigenere", "Scytale");
        methodChoice.setValue("Caesar");


        inputText = new TextField();
        inputText.setPromptText("Enter the text");


        keyText = new TextField();
        keyText.setPromptText("Enter the key (<integer> for scytale & Caesar) ,<TextKey> for Vigenere. ");

        modeGroup = new ToggleGroup();
        RadioButton encryptMode = new RadioButton("Encrypt");
        encryptMode.setToggleGroup(modeGroup);
        encryptMode.setSelected(true);
        RadioButton decryptMode = new RadioButton("Decrypt");
        decryptMode.setToggleGroup(modeGroup);

        Button processButton = new Button("Process");
        processButton.setOnAction(e -> processText());

        resultTextArea = new TextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setWrapText(true);
        resultTextArea.setPromptText("Encrypted/Decrypted text will appear here...");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(EnigmaButton,methodChoice, inputText, keyText, encryptMode, decryptMode, processButton, resultTextArea);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void processText() {
        String method = methodChoice.getValue();
        String text = inputText.getText();
        String key = keyText.getText().toUpperCase();
        boolean isEncrypting = ((RadioButton) modeGroup.getSelectedToggle()).getText().equals("Encrypt");
        String result = "";

        try {
            result = switch (method) {
                case "Caesar" -> isEncrypting ? CaesarCipher.encrypt(text, Integer.parseInt(key), false) :
                        CaesarCipher.decrypt(text, Integer.parseInt(key), false);
                case "Vigenere" -> isEncrypting ? VigenereCipher.encrypt(text, key, false) :
                        VigenereCipher.decrypt(text, key, false);
                case "Scytale" -> isEncrypting ? ScytaleCipher.encrypt(text, Integer.parseInt(key), false) :
                        ScytaleCipher.decrypt(text, Integer.parseInt(key), false);
                default -> result;
            };
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }

        resultTextArea.setText(result);
    }


    private void openEnigmaConfigUI() {
        Stage enigmaConfigStage = new Stage();
        EnigmaConfigUI enigmaConfigUI = new EnigmaConfigUI();
        enigmaConfigUI.start(enigmaConfigStage);
        enigmaConfigStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
