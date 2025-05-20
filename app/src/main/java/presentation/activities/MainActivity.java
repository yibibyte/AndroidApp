package presentation.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import presentation.adapters.ProductAdapter;
import ai.j4app.androidapp.R;
import presentation.viewmodels.ProductViewModel;
import data.local.entities.Product;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private ProductViewModel productViewModel;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Настройка RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new ProductAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Подписка на изменения данных
        productViewModel.getProducts().observe(this, products -> {
            if (products != null) {
                adapter.setProducts(products);
            }
        });

        // Подписка на ошибки
        productViewModel.getError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });

        // Кнопка добавления
        FloatingActionButton fab = findViewById(R.id.button_delete);
        fab.setOnClickListener(v -> showProductDialog(null));
    }

    private void showProductDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(product == null ? "Добавить продукт" : "Редактировать продукт");

        View view = getLayoutInflater().inflate(R.layout.dialog_product, null);
        builder.setView(view);

        EditText nameInput = view.findViewById(R.id.editTextName);
        EditText priceInput = view.findViewById(R.id.editTextPrice);

        if (product != null) {
            nameInput.setText(product.getName());
            priceInput.setText(String.valueOf(product.getPrice()));
        }

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String name = nameInput.getText().toString();
            String priceStr = priceInput.getText().toString();

            if (!name.isEmpty() && !priceStr.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceStr);
                    if (product == null) {
                        // Добавление нового продукта
                        productViewModel.addProduct(new Product(name, price));
                    } else {
                        // Обновление существующего
                        product.setName(name);
                        product.setPrice(price);
                        productViewModel.updateProduct(product);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Некорректная цена", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    @Override
    public void onEditClick(Product product) {
        showProductDialog(product);
    }

    @Override
    public void onDeleteClick(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Удаление")
                .setMessage("Удалить " + product.getName() + "?")
                .setPositiveButton("Удалить", (dialog, which) -> {
                    productViewModel.deleteProduct(product);
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}