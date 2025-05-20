package presentation.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import data.DataManager;
import data.impl.RoomDataManager;
import data.local.entities.Product;
import data.repositories.ProductRepository;

/**
 * ViewModel для работы с продуктами
 * Связывает UI с DataManager
 */
public class ProductViewModel extends AndroidViewModel {
    private final DataManager dataManager;
    private final MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public ProductViewModel(@NonNull Application application) {
        super(application);
        this.dataManager = new RoomDataManager(application);
        loadProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return productsLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public void loadProducts() {
        dataManager.getAllProducts(new DataManager.DataCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> result) {
                productsLiveData.postValue(result);
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue(error);
            }
        });
    }

    public void addProduct(Product product) {
        dataManager.addProduct(product, new DataManager.DataCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                loadProducts();
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue("Ошибка добавления: " + error);
            }
        });
    }

    public void updateProduct(Product product) {
        dataManager.updateProduct(product, new DataManager.DataCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                loadProducts();
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue("Ошибка обновления: " + error);
            }
        });
    }

    public void deleteProduct(Product product) {
        dataManager.deleteProduct(product, new DataManager.DataCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                loadProducts();
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue("Ошибка удаления: " + error);
            }
        });
    }
}
