package data;
/**
 * Интерфейс для абстракции работы с данными
 * Позволяет легко подменить реализацию (Room, Firebase, etc)
 */

import data.local.entities.Product;
import java.util.List;
public interface DataManager {
    // CREATE
    void addProduct(Product product, DataCallback<Void> callback);

    // READ
    void getAllProducts(DataCallback<List<Product>> callback);

    // UPDATE
    void updateProduct(Product product, DataCallback<Void> callback);

    // DELETE
    void deleteProduct(Product product, DataCallback<Void> callback);

    interface DataCallback<T> {
        void onSuccess(T result);
        void onError(String error);
    }
}