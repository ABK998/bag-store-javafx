package APPP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegisterUI {

    public void showWindow() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Bag Store - Register");
        primaryStage.setResizable(false);

        Label label = new Label("Register for Bag Store");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        styleInput(nameField);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        styleInput(emailField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        styleInput(passwordField);

        TextField balanceField = new TextField();
        balanceField.setPromptText("Initial Balance");
        styleInput(balanceField);

        Button registerButton = createStyledButton("Register", "#007BFF");
        Button adminButton = createStyledButton("Admin Login", "#2C2C2E");
        Button userLoginBtn = createStyledButton("Login as User", "#2C2C2E");

        registerButton.setTextFill(Color.WHITE);
        adminButton.setTextFill(Color.WHITE);
        userLoginBtn.setTextFill(Color.WHITE);

        userLoginBtn.setOnAction(e -> {
            primaryStage.close();
            new LoginUI().showMainWindow(new Stage());
        });

        adminButton.setOnAction(e -> {
            primaryStage.close();
            new AdminLoginUI().showWindow();
        });

        registerButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String balanceText = balanceField.getText();
            double balance;

            try {
                balance = Double.parseDouble(balanceText);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Balance! Enter a valid number.");
                return;
            }

            boolean success = UserManager.registerUser(name, email, password, balance);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful! You can now log in.");
                primaryStage.close();
                new LoginUI().showMainWindow(new Stage());
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed! Check your details or email already exists.");
            }
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, nameField, emailField, passwordField, balanceField,
                registerButton, adminButton, userLoginBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-background-color: #121212; -fx-font-family: 'Segoe UI';");

        Scene scene = new Scene(layout, 450, 600);
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/bag stroe.jpg")));
        } catch (Exception ignored) {}

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void styleInput(TextField field) {
        field.setMaxWidth(300);
        field.setStyle(
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-padding: 10;" +
            "-fx-font-size: 14;" +
            "-fx-background-color: #1E1E1E;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: gray;"
        );
    }

    private Button createStyledButton(String text, String bgColor) {
        Button button = new Button(text);
        button.setMaxWidth(300);
        button.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-background-radius: 20;" +
            "-fx-font-size: 14;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 12 24;"
        );
        return button;
    }
}