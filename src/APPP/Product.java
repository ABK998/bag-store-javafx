package APPP;

import javafx.beans.property.*;

public class Product {
    private final StringProperty id;  
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty stock;

    public Product(String id, String name, double price, int stock) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
    }

    public String getId() { return id.get(); }
    public void setId(String value) { this.id.set(value); }
    public StringProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public void setName(String value) { this.name.set(value); }
    public StringProperty nameProperty() { return name; }

    public double getPrice() { return price.get(); }
    public void setPrice(double value) { this.price.set(value); }
    public DoubleProperty priceProperty() { return price; }

    public int getStock() { return stock.get(); }
    public void setStock(int value) { this.stock.set(value); }
    public IntegerProperty stockProperty() { return stock; }
}