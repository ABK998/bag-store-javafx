package APPP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdminLoginUI {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public void showWindow() {
        Stage stage = new Stage();
        stage.setTitle("Admin Login");
        stage.setResizable(false);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        styleInput(usernameField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        styleInput(passwordField);

        Button submitButton = createStyledButton("Submit", "#007BFF");
        Button userLoginBtn = createStyledButton("Login as User", "#2C2C2E");
        Button userRegisterBtn = createStyledButton("Register as User", "#2C2C2E");

        submitButton.setTextFill(Color.WHITE);
        userLoginBtn.setTextFill(Color.WHITE);
        userRegisterBtn.setTextFill(Color.WHITE);

        submitButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (validateAdmin(username, password)) {
                stage.close();
                new AdminBagUI().showWindow();
            } else {
                showAlert("Invalid admin credentials.");
            }
        });

        userLoginBtn.setOnAction(e -> {
            stage.close();
            new LoginUI().showMainWindow(new Stage());
        });

        userRegisterBtn.setOnAction(e -> {
            stage.close();
            new RegisterUI().showWindow();
        });

        Label adminLabel = new Label("Admin Login Portal");
        adminLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");

        VBox layout = new VBox(20, adminLabel, usernameField, passwordField, submitButton, userLoginBtn, userRegisterBtn);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #121212; -fx-font-size: 14px; -fx-font-family: 'Segoe UI';");

        Scene scene = new Scene(layout, 450, 600);
        stage.setScene(scene);
        stage.show();
    }

    private boolean validateAdmin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
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