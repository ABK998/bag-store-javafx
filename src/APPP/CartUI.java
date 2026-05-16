package APPP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color; // Corrected JavaFX import

import java.util.List;

public class CartUI {
    private User currentUser;
    private ProductUI productUI;

    public CartUI(User user, ProductUI productUI) {
        this.currentUser = user;
        this.productUI = productUI;
    }

    public void showWindow() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Bag Store - Your Cart");

        VBox cartItems = new VBox(10);
        cartItems.setPadding(new Insets(10));

        List<CartItem> cart = CartManager.getUserCart(currentUser).getItems();

        for (CartItem cartItem : cart) {
            HBox itemBox = new HBox(10);
            itemBox.setAlignment(Pos.CENTER_LEFT);
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            Label productLabel = new Label(product.getName() + " x" + quantity + " - Rs" + (product.getPrice() * quantity));
            productLabel.setStyle("-fx-text-fill: white;");
            Button removeButton = new Button("Remove");

            removeButton.setOnAction(e -> {
                CartManager.removeFromCart(currentUser, product);
                primaryStage.close();
                this.showWindow();
            });

            itemBox.getChildren().addAll(productLabel, removeButton);
            cartItems.getChildren().add(itemBox);
        }

        Label totalLabel = new Label("Total: Rs" + CartManager.getCartTotal(currentUser));
        totalLabel.setStyle("-fx-text-fill: lightgreen;");

        Button checkoutButton = new Button("Proceed to Checkout");
        checkoutButton.setStyle("-fx-background-color: dodgerblue; -fx-text-fill: white;");
        checkoutButton.setOnAction(e -> {
            new CheckoutUI(currentUser, productUI).showWindow();
            primaryStage.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: black;");
        Label text2 = new Label("Your Shopping Cart");
        text2.setStyle("-fx-text-fill: white");
        layout.getChildren().addAll(text2, cartItems, totalLabel, checkoutButton);

        Scene scene = new Scene(layout, 500, 400);
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/bag stroe.jpg")));
        } catch (Exception ignored) {}
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}