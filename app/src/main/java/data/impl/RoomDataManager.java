package data.impl;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import data.DataManager;
import data.local.AppDatabase;
import data.local.dao.ProductDao;
import data.local.entities.Product;

/**
 * Реализация DataManager с использованием Room Database
 */
public class RoomDataManager implements DataManager {
    private final ProductDao productDao;
    private final Executor executor;

    public RoomDataManager(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.productDao = db.productDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void addProduct(Product product, DataCallback<Void> callback) {
        executor.execute(() -> {
            try {
                productDao.insert(product);
                callback.onSuccess(null);
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        });
    }

    @Override
    public void getAllProducts(DataCallback<List<Product>> callback) {
        executor.execute(() -> {
            try {
                List<Product> products = productDao.getAllProductsSync();
                callback.onSuccess(products);
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        });
    }

    @Override
    public void updateProduct(Product product, DataCallback<Void> callback) {
        executor.execute(() -> {
            try {
                productDao.update(product);
                callback.onSuccess(null);
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        });
    }

    @Override
    public void deleteProduct(Product product, DataCallback<Void> callback) {
        executor.execute(() -> {
            try {
                productDao.delete(product);
                callback.onSuccess(null);
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        });
    }
}
