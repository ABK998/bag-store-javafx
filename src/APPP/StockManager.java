package APPP;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StockManager {
    private static List<Product> products = new ArrayList<>();
    private static final String FILE_NAME = "products.txt";

    static {
        loadProductsFromFile();
    }

    public static List<Product> getAllProducts() {
        return products;
    }

    public static void addProduct(Product product) {
        products.add(product);
        saveAllProductsToFile(); 
    }

    public static void updateStock(String productId, int quantityChange) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                product.setStock(product.getStock() + quantityChange);
                break;
            }
        }
        saveAllProductsToFile(); 
    }


    public static void saveAllProductsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (Product p : products) {
             
                writer.write(p.getId() + "," + p.getName() + "," + p.getPrice() + "," + p.getStock());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving products to file: " + e.getMessage());
        }
    }

    private static void loadProductsFromFile() {
        File file = new File(FILE_NAME);
        
        
        if (!file.exists()) {
            try {
                file.createNewFile(); 
            } catch (IOException e) {
                System.out.println("Error creating empty products file: " + e.getMessage());
            }
            return; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int stock = Integer.parseInt(parts[3]);
                    products.add(new Product(id, name, price, stock));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
        }

}
}