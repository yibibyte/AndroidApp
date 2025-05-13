package dao;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import model.Product;

public class ProductRepository {
    private final ProductDao productDao;
    private final LiveData<List<Product>> allProducts;

    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insert(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.insert(product);
        });
    }

    public void update(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.update(product);
        });
    }

    public void delete(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.delete(product);
        });
    }
}