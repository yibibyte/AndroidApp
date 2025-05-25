package presentation.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ai.j4app.androidapp.R;
import ai.j4app.androidapp.ui.login.AdminActivity;
import ai.j4app.androidapp.ui.login.ClientActivity;
import ai.j4app.androidapp.ui.login.LoginFragment;
import ai.j4app.androidapp.ui.login.ManagerActivity;
import ai.j4app.androidapp.ui.login.SessionManager;
import data.local.entities.Product;
import presentation.adapters.ProductAdapter;
import presentation.viewmodels.ProductViewModel;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private ProductViewModel productViewModel;
    private ProductAdapter adapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager session = new SessionManager(this);
        if (!session.getUserRole().equals("admin")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_admin);
    }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_logout) {
        new SessionManager(this).logout();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu); // Замените на имя вашего файла
        return true;
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
    private void showLoginScreen() {
        setContentView(R.layout.activity_main);
        // Показываем LoginFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment())
                .commit();
    }
    public void redirectBasedOnRole() {
        String role = sessionManager.getUserRole();
        switch (role) {
            case "admin":
                startActivity(new Intent(this, AdminActivity.class));
                break;
            case "manager":
                startActivity(new Intent(this, ManagerActivity.class));
                break;
            // ... другие роли

            default:
                startActivity(new Intent(this, ClientActivity.class));
        }
        finish();
    }
}

