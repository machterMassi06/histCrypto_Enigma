package com.mycrypto.ui;

import com.mycrypto.cipher.CaesarCipher;
import com.mycrypto.cipher.ScytaleCipher;
import com.mycrypto.cipher.VigenereCipher;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CryptoUi extends Application {

    private ComboBox<String> methodChoice;
    private TextField inputText;
    private TextField keyText;
    private Label resultLabel;
    private ToggleGroup modeGroup;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Encryption/Decryption Application");

        methodChoice = new ComboBox<>();
        methodChoice.getItems().addAll("Caesar", "Vigenere", "Scytale", "Enigma");
        methodChoice.setValue("Caesar");

        inputText = new TextField();
        inputText.setPromptText("Enter the text");

        keyText = new TextField();
        keyText.setPromptText("Enter the key (if needed)");

        modeGroup = new ToggleGroup();
        RadioButton encryptMode = new RadioButton("Encrypt");
        encryptMode.setToggleGroup(modeGroup);
        encryptMode.setSelected(true);
        RadioButton decryptMode = new RadioButton("Decrypt");
        decryptMode.setToggleGroup(modeGroup);

        Button processButton = new Button("Process");
        processButton.setOnAction(e -> processText());

        resultLabel = new Label("Result: ");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(methodChoice, inputText, keyText, encryptMode, decryptMode, processButton, resultLabel);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void processText() {
        String method = methodChoice.getValue();
        String text = inputText.getText().toUpperCase();
        String key = keyText.getText().toUpperCase();
        boolean isEncrypting = ((RadioButton) modeGroup.getSelectedToggle()).getText().equals("Encrypt");
        String result = "";

        try {
            switch (method) {
                case "Caesar":
                    result = isEncrypting ? CaesarCipher.encrypt(text, Integer.parseInt(key), false) :
                            CaesarCipher.decrypt(text, Integer.parseInt(key), false);
                    break;
                case "Vigenere":
                    result = isEncrypting ? VigenereCipher.encrypt(text, key, false) :
                            VigenereCipher.decrypt(text, key, false);
                    break;
                case "Scytale":
                    result = isEncrypting ? ScytaleCipher.encrypt(text, Integer.parseInt(key), false) :
                            ScytaleCipher.decrypt(text, Integer.parseInt(key), false);
                    break;
                case "Enigma":
                    openEnigmaUI(); // Interface avancée pour Enigma
                    return;
            }
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }

        resultLabel.setText("Result: " + result);
    }

    private void openEnigmaUI() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Enigma Machine");
        alert.setHeaderText(null);
        alert.setContentText("Opening advanced Enigma UI...");
        alert.showAndWait();
        // Ici, on peut ouvrir une nouvelle fenêtre avec l'interface avancée d'Enigma
    }

    public static void main(String[] args) {
        launch(args);
    }
}
