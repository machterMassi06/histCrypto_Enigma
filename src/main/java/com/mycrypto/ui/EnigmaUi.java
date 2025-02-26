package com.mycrypto.ui;

import com.mycrypto.enigma.EnigmaMachine;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EnigmaUi extends Application {

    private final EnigmaMachine enigmaMachine;
    private TextArea originalTextArea;
    private TextArea encryptedTextArea;
    private GridPane keyboardEncrypt;
    private StringBuilder currentInput;
    private Button lastEncryptedButton = null;

    public EnigmaUi(EnigmaMachine enigmaMachine) {
        this.enigmaMachine = enigmaMachine;
    }

    @Override
    public void start(Stage stage) {
        // Init components
        stage.setTitle("Enigma Machine Encryption");

        currentInput = new StringBuilder();

        originalTextArea = new TextArea();
        originalTextArea.setEditable(false);
        originalTextArea.setPromptText("Original text...");
        originalTextArea.setWrapText(true);

        encryptedTextArea = new TextArea();
        encryptedTextArea.setEditable(false);
        encryptedTextArea.setPromptText("Encrypted text...");
        encryptedTextArea.setWrapText(true);

        HBox textBoxes = new HBox(10, originalTextArea, encryptedTextArea);
        textBoxes.setAlignment(Pos.CENTER);

        GridPane keyboardPane = createKeyboardPane();
        keyboardEncrypt = createKeyboardEncrypt();

        VBox layout = new VBox(20, keyboardPane, keyboardEncrypt, textBoxes);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createKeyboardEncrypt() {
        GridPane keyboard = new GridPane();
        keyboard.setHgap(10);
        keyboard.setVgap(10);
        keyboard.setAlignment(Pos.CENTER);

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int row = 0, col = 0;

        for (char letter : alphabet.toCharArray()) {
            Button keyButton = new Button(String.valueOf(letter));
            keyButton.setMinSize(40, 40);
            keyButton.setId("btn" + letter);
            keyButton.setStyle("-fx-background-color: lightgray;");
            keyboard.add(keyButton, col, row);

            col++;
            if (col == 9) {
                col = 0;
                row++;
            }
        }

        return keyboard;
    }

    private GridPane createKeyboardPane() {
        GridPane keyboard = new GridPane();
        keyboard.setHgap(10);
        keyboard.setVgap(10);
        keyboard.setAlignment(Pos.CENTER);

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int row = 0, col = 0;

        for (char letter : alphabet.toCharArray()) {
            Button keyButton = new Button(String.valueOf(letter));
            keyButton.setMinSize(40, 40);
            keyButton.setOnAction(e -> onKeyPress(letter));
            keyboard.add(keyButton, col, row);

            col++;
            if (col == 9) {
                col = 0;
                row++;
            }
        }


        Button spaceButton = new Button("Space");
        spaceButton.setMinSize(40, 40);
        spaceButton.setOnAction(e -> onKeyPress(' '));
        keyboard.add(spaceButton, col, row);


        Button backspaceButton = new Button("←");
        backspaceButton.setMinSize(40, 40);
        backspaceButton.setOnAction(e -> onBackspace());
        keyboard.add(backspaceButton, col + 1, row);

        return keyboard;
    }


    private void onKeyPress(char letter) {
        if (letter == ' ') {
            currentInput.append(" ");
        } else {
            currentInput.append(letter);
        }


        originalTextArea.setText(currentInput.toString());


        char lastChar = currentInput.charAt(currentInput.length() - 1);
        StringBuilder encryptedText = new StringBuilder(encryptedTextArea.getText());
        char encryptedLetter=' ';
        if (Character.isLetter(lastChar)) {
            encryptedLetter = enigmaMachine.encrypt(lastChar);
            encryptedText.append(encryptedLetter);
        } else {
            encryptedText.append(lastChar);
        }

        encryptedTextArea.setText(encryptedText.toString());

        if (lastEncryptedButton != null) {
            lastEncryptedButton.setStyle("-fx-background-color: lightgray;");
        }

        Button encryptedButton = (Button) keyboardEncrypt.lookup("#btn" + encryptedLetter);
        if (encryptedButton != null) {
            encryptedButton.setStyle("-fx-background-color: yellow;");
        }

        lastEncryptedButton = encryptedButton;
    }

    private void onBackspace() {
        if (!currentInput.isEmpty()) {
            char removedChar = currentInput.charAt(currentInput.length() - 1);
            currentInput.deleteCharAt(currentInput.length() - 1);

            originalTextArea.setText(currentInput.toString());

            StringBuilder encryptedText = new StringBuilder(encryptedTextArea.getText());

            enigmaMachine.revertRotorPositions();

            if (!encryptedText.isEmpty()) {
                encryptedText.deleteCharAt(encryptedText.length() - 1);
            }

            encryptedTextArea.setText(encryptedText.toString());

            if (lastEncryptedButton != null) {
                lastEncryptedButton.setStyle("-fx-background-color: lightgray;");
                lastEncryptedButton = null;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
