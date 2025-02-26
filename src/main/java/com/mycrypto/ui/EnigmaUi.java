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
    private Button lastEncryptedButton = null;  // Référence pour le dernier bouton encrypté

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

        // Disposition générale de l'interface
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

        // Liste des lettres de l'alphabet (sans espace et sans retour arrière pour simplification)
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int row = 0, col = 0;

        for (char letter : alphabet.toCharArray()) {
            Button keyButton = new Button(String.valueOf(letter));
            keyButton.setMinSize(40, 40);
            keyButton.setId("btn" + letter);  // Ajouter un ID unique pour chaque bouton
            keyButton.setStyle("-fx-background-color: lightgray;");  // Style par défaut
            keyboard.add(keyButton, col, row);

            col++;
            if (col == 9) {  // Passer à la ligne suivante après 9 lettres
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

        // Liste des lettres de l'alphabet (sans espace et sans retour arrière pour simplification)
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int row = 0, col = 0;

        for (char letter : alphabet.toCharArray()) {
            Button keyButton = new Button(String.valueOf(letter));
            keyButton.setMinSize(40, 40);
            keyButton.setOnAction(e -> onKeyPress(letter));

            // Ajoute les boutons aux bonnes positions dans la grille
            keyboard.add(keyButton, col, row);

            col++;
            if (col == 9) {  // Passer à la ligne suivante après 9 lettres
                col = 0;
                row++;
            }
        }

        // Ajouter le bouton "Space"
        Button spaceButton = new Button("Space");
        spaceButton.setMinSize(40, 40);
        spaceButton.setOnAction(e -> onKeyPress(' '));
        keyboard.add(spaceButton, col, row);

        // Ajouter le bouton "Retour arrière"
        Button backspaceButton = new Button("←");
        backspaceButton.setMinSize(40, 40);
        backspaceButton.setOnAction(e -> onBackspace());
        keyboard.add(backspaceButton, col + 1, row);

        return keyboard;
    }

    // Action lors de la pression d'une touche du clavier
    private void onKeyPress(char letter) {
        if (letter == ' ') {
            currentInput.append(" ");
        } else {
            currentInput.append(letter);
        }

        // Mise à jour de la zone de texte originale
        originalTextArea.setText(currentInput.toString());

        // Encrypter uniquement la dernière lettre
        char lastChar = currentInput.charAt(currentInput.length() - 1);
        StringBuilder encryptedText = new StringBuilder(encryptedTextArea.getText());
        char encryptedLetter=' ';
        if (Character.isLetter(lastChar)) {
            encryptedLetter = enigmaMachine.encrypt(lastChar);
            encryptedText.append(encryptedLetter);// Encryption lettre par lettre
        } else {
            encryptedText.append(lastChar);
        }

        // Mise à jour de la zone de texte pour afficher le texte chiffré
        encryptedTextArea.setText(encryptedText.toString());


        // Si un bouton était déjà allumé, on l'éteint
        if (lastEncryptedButton != null) {
            lastEncryptedButton.setStyle("-fx-background-color: lightgray;");  // Réinitialiser l'ancien bouton
        }

        // Allumer le bouton correspondant à la lettre chiffrée
        Button encryptedButton = (Button) keyboardEncrypt.lookup("#btn" + encryptedLetter);
        if (encryptedButton != null) {
            encryptedButton.setStyle("-fx-background-color: yellow;");  // Allumer le bouton
        }

        // Mettre à jour la référence au dernier bouton chiffré
        lastEncryptedButton = encryptedButton;
    }

    // Action pour le bouton "Retour arrière"
    private void onBackspace() {
        if (!currentInput.isEmpty()) {
            // Supprimer la dernière lettre du texte original
            char removedChar = currentInput.charAt(currentInput.length() - 1);
            currentInput.deleteCharAt(currentInput.length() - 1);

            // Mise à jour du texte original
            originalTextArea.setText(currentInput.toString());

            // Mise à jour du texte chiffré après suppression
            StringBuilder encryptedText = new StringBuilder(encryptedTextArea.getText());

            // Retour à la position précédente des rotors
            enigmaMachine.revertRotorPositions();

            // Supprimer la dernière lettre du texte chiffré
            if (encryptedText.length() > 0) {
                encryptedText.deleteCharAt(encryptedText.length() - 1);
            }

            // Mise à jour du texte chiffré
            encryptedTextArea.setText(encryptedText.toString());

            // Réinitialiser l'affichage visuel : éteindre le dernier bouton allumé
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
