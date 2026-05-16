package APPP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginUI extends Application {

    private User loggedInUser = null;

    @Override
    public void start(Stage primaryStage) {
        showMainWindow(primaryStage);
    }

    public void showMainWindow(Stage primaryStage) {
        primaryStage.setTitle("Bag Store - Login");
        primaryStage.setResizable(false);

        Label label = new Label("Login to Bag Store");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        styleInput(emailField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        styleInput(passwordField);

        Button loginButton = createStyledButton("Login", "#007BFF");
        Button registerButton = createStyledButton("Register", "#2C2C2E");
        Button adminButton = createStyledButton("Admin Login", "#2C2C2E");

        loginButton.setTextFill(Color.WHITE);
        registerButton.setTextFill(Color.WHITE);
        adminButton.setTextFill(Color.WHITE);

        adminButton.setOnAction(e -> {
            primaryStage.close();
            new AdminLoginUI().showWindow();
        });

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            User user = UserManager.login(email, password);
            if (user != null) {
                loggedInUser = user;
                primaryStage.close();
                new ProductUI(loggedInUser).showWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed! Invalid email or password.");
            }
        });

        registerButton.setOnAction(e -> {
            primaryStage.close();
            new RegisterUI().showWindow();
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, emailField, passwordField, loginButton, registerButton, adminButton);
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

    public static void main(String[] args) {
        launch(args);
    }
}