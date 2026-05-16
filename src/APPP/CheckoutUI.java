package APPP;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CheckoutUI {
    private User currentUser;
    private ProductUI productUI;

    public CheckoutUI(User user, ProductUI productUI) {
        this.currentUser = user;
        this.productUI = productUI;
    }

    public void showWindow() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Bag Store - Checkout");

        double total = CartManager.getCartTotal(currentUser);
        Label totalLabel = new Label("Total Amount: Rs" + total);
        Label balanceLabel = new Label("Your Balance: Rs" + currentUser.getBalance());

        totalLabel.setStyle("-fx-text-fill: white;");
        balanceLabel.setStyle("-fx-text-fill: white;");

        Label newBalanceLabel = new Label();
        newBalanceLabel.setStyle("-fx-text-fill: lightgreen;");

        Button confirmButton = new Button("Confirm Purchase");
        confirmButton.setStyle("-fx-background-color: forestgreen; -fx-text-fill: white;");
        confirmButton.setOnAction(e -> {
            if (currentUser.getBalance() >= total) {
                currentUser.setBalance(currentUser.getBalance() - total);
                CartManager.purchaseItems(currentUser); 
                showAlert(Alert.AlertType.INFORMATION, "Purchase Successful!");
                newBalanceLabel.setText("New Balance: Rs" + currentUser.getBalance());
                if (productUI != null) productUI.refreshUI();
                primaryStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Insufficient balance!");
            }
        });

        VBox layout = new VBox(15, new Label("Checkout"), totalLabel, balanceLabel, confirmButton, newBalanceLabel);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(layout, 400, 300);
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
}