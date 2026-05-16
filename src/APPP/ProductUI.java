package APPP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;

public class ProductUI {
    private static User currentUser;
    private Label balanceLabel;
    private FlowPane productPane;

    public ProductUI(User user) {
        currentUser = user;
    }

    public void showWindow() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Bag Store - Products");
        primaryStage.setResizable(true);

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: black;");

        Label title = new Label("Available Bags");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        balanceLabel = new Label("Balance: Rs" + currentUser.getBalance());
        balanceLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        productPane = new FlowPane();
        productPane.setHgap(20);
        productPane.setVgap(20);
        productPane.setPadding(new Insets(10));
        productPane.setStyle("-fx-background-color: dimgray; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        ScrollPane scrollPane = new ScrollPane(productPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: black;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        productPane.prefWrapLengthProperty().bind(mainLayout.widthProperty());

        loadProducts();

        Button viewCartButton = new Button("View Cart");
        viewCartButton.setStyle("-fx-background-color: dodgerblue; -fx-text-fill: white; -fx-font-weight: bold;");
        viewCartButton.setOnAction(e -> new CartUI(currentUser, this).showWindow());

        Button logout = new Button("Logout");
        logout.setStyle("-fx-background-color: crimson; -fx-text-fill: white; -fx-font-weight: bold;");
        logout.setOnAction(e -> {
            primaryStage.close();
            new LoginUI().showMainWindow(new Stage());
        });

        HBox actionButtons = new HBox(10, viewCartButton, logout);
        actionButtons.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(title, balanceLabel, scrollPane, actionButtons);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); 
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/bag stroe.jpg")));
        } catch (Exception ignored) {}
        primaryStage.show();
    }

    private void loadProducts() {
        productPane.getChildren().clear();
        List<Product> products = StockManager.getAllProducts();
        for (Product product : products) {
            VBox card = createProductCard(product);
            productPane.getChildren().add(card);
        }
    }

    private VBox createProductCard(Product product) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setPrefSize(200, 260);
        card.setStyle("-fx-background-color: darkslategray; -fx-border-color: gray; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        ImageView imageView = new ImageView();
        String filename = product.getId() + ".jpg";
        
        try {
           
            File diskFile = new File("src/" + filename);
            if (diskFile.exists()) {
                Image img = new Image(diskFile.toURI().toString());
                imageView.setImage(img);
            } else {
                
                java.net.URL resourceUrl = getClass().getResource("/" + filename);
                if (resourceUrl != null) {
                    imageView.setImage(new Image(resourceUrl.toExternalForm()));
                } else {
                   
                    java.net.URL placeholderUrl = getClass().getResource("/images/placeholder.jpg");
                    if (placeholderUrl != null) {
                        imageView.setImage(new Image(placeholderUrl.toExternalForm()));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading image for product " + product.getId() + ": " + e.getMessage());
        }

        imageView.setFitWidth(180);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        Label nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label priceLabel = new Label("Price: Rs" + product.getPrice());
        priceLabel.setStyle("-fx-text-fill: lightgreen;");

        Label stockLabel = new Label("In stock: " + product.getStock());
        stockLabel.setStyle("-fx-text-fill: white;");

        Button addButton = new Button("Add to Cart");
        addButton.setStyle("-fx-background-color: forestgreen; -fx-text-fill: white; -fx-font-weight: bold;");
        addButton.setOnAction(e -> {
            if (product.getStock() > 0) {
                CartManager.addToCart(currentUser, product, 1);
                showAlert(Alert.AlertType.INFORMATION, "Added to cart: " + product.getName());
            } else {
                showAlert(Alert.AlertType.WARNING, "Out of stock!");
            }
        });

        card.getChildren().addAll(imageView, nameLabel, priceLabel, stockLabel, addButton);
        card.setAlignment(Pos.CENTER);
        return card;
    }

    public void refreshUI() {
        balanceLabel.setText("Balance: Rs" + currentUser.getBalance());
        loadProducts();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}