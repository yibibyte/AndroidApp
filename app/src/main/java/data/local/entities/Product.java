package data.local.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public double price;
    public Product() {}

    @Ignore
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}