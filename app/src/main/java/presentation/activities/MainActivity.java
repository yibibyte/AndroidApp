package presentation.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import presentation.adapters.ProductAdapter;
import ai.j4app.androidapp.R;
import presentation.viewmodels.ProductViewModel;
import data.local.entities.Product;

public class MainActivity extends AppCompatActivity {
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
        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Наблюдение за изменениями в базе данных
        productViewModel.getAllProducts().observe(this, products -> adapter.setProducts(products));

        // Добавление нового продукта
        findViewById(R.id.addButton).setOnClickListener(v -> {
            Product product = new Product("Новый продукт", 100.00);
            productViewModel.insert(product);
        });
    }
}