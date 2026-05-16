package APPP;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AdminBagUI {
    private TableView<Product> tableView = new TableView<>();
    private File selectedImageFile = null;
    private Label imageStatusLabel = new Label("No image selected");

    public void showWindow() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Bag Store - Admin Panel");
        primaryStage.setResizable(true);

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: black;");

        Label heading = new Label("Manage Bags");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(nameCol, priceCol, stockCol);
        refreshTable();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(200);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        TextField stockField = new TextField();
        stockField.setPromptText("Stock");

        nameField.setPrefWidth(140);
        priceField.setPrefWidth(100);
        stockField.setPrefWidth(100);

        imageStatusLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 12px;");
        Button browseImageButton = new Button("Choose Image...");
        browseImageButton.setStyle("-fx-background-color: #555; -fx-text-fill: white;");
        browseImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Product Image");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                selectedImageFile = file;
                imageStatusLabel.setText(file.getName());
                imageStatusLabel.setStyle("-fx-text-fill: lightgreen;");
            }
        });

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button logoutButton = new Button("Logout");

        String buttonStyle = "-fx-background-color: #444; -fx-text-fill: white; -fx-font-weight: bold;";
        addButton.setStyle(buttonStyle);
        editButton.setStyle(buttonStyle);
        deleteButton.setStyle(buttonStyle);
        logoutButton.setStyle(buttonStyle);

        addButton.setOnAction(e -> handleAdd(nameField, priceField, stockField));
        editButton.setOnAction(e -> handleEdit(nameField, priceField, stockField));
        deleteButton.setOnAction(e -> handleDelete());
        logoutButton.setOnAction(e -> {
            primaryStage.close();
            new LoginUI().showMainWindow(new Stage());
        });

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        Label projectLabel = new Label("Project Admin");
        projectLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(projectLabel, spacer, logoutButton);

        HBox inputBox = new HBox(10, nameField, priceField, stockField, browseImageButton, imageStatusLabel);
        inputBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(topBar, heading, tableView, inputBox, buttonBox);

        Scene scene = new Scene(mainLayout, 850, 450);
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/bag stroe.jpg")));
        } catch (Exception ignored) {}
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleAdd(TextField nameField, TextField priceField, TextField stockField) {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            String id = String.format("B%03d", StockManager.getAllProducts().size() + 1);

            if (selectedImageFile != null) {
                try {
                    File targetDir = new File("src");
                    if (!targetDir.exists()) targetDir.mkdirs();
                    File targetFile = new File(targetDir, id + ".jpg");
                    Files.copy(selectedImageFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    System.out.println("Could not save image to target workspace directory structure: " + ex.getMessage());
                }
            }

            
            StockManager.addProduct(new Product(id, name, price, stock));
            
            refreshTable();
            clearFields(nameField, priceField, stockField);
            selectedImageFile = null;
            imageStatusLabel.setText("No image selected");
            imageStatusLabel.setStyle("-fx-text-fill: gray;");
        } catch (NumberFormatException e) {
            showAlert("Invalid input. Price and stock must be numbers.");
        }
    }

    private void handleEdit(TextField nameField, TextField priceField, TextField stockField) {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No product selected.");
            return;
        }
        try {
            selected.setName(nameField.getText());
            selected.setPrice(Double.parseDouble(priceField.getText()));
            selected.setStock(Integer.parseInt(stockField.getText()));
            
            
            StockManager.saveAllProductsToFile();
            
            refreshTable();
            clearFields(nameField, priceField, stockField);
        } catch (NumberFormatException e) {
            showAlert("Invalid input.");
        }
    }

    private void handleDelete() {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            StockManager.getAllProducts().remove(selected);
            
            
            StockManager.saveAllProductsToFile();
            
            refreshTable();
        }
    }

    private void refreshTable() {
        tableView.getItems().clear();
        List<Product> allProducts = StockManager.getAllProducts();
        tableView.getItems().addAll(allProducts);
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}