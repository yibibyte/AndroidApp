package data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

import data.local.AppDatabase;
import data.local.dao.ProductDao;
import data.local.entities.Product;

public class ProductRepository {
    private final ProductDao productDao;
    private final LiveData<List<Product>> allProducts;

    public ProductRepository(Application app, LiveData<List<Product>> allProducts) {
        this.allProducts = allProducts;
        AppDatabase db = Room.databaseBuilder(app,
                AppDatabase.class, "products-db").build();
        productDao = db.productDao();
    }

    public LiveData<List<Product>> getAllProducts() {
        return productDao.getAllProducts();
    }

    public void insert(Product product) {
        new Thread(() -> productDao.insert(product)).start();
    }

    public void update(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> productDao.update(product));
    }

    public void delete(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> productDao.delete(product));
    }
}