package data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import data.local.entities.Product;

// ProductDao.java
@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM products")
    List<Product> getAllProductsSync(); // Синхронная версия для RoomDataManager

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts(); // Асинхронная версия для LiveData
}