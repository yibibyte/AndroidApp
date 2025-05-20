package presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import data.local.entities.Product;

import java.util.ArrayList;
import java.util.List;

import ai.j4app.androidapp.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products = new ArrayList<>(); // Инициализация списка
    private final OnProductClickListener listener;

    public interface OnProductClickListener {
        void onEditClick(Product product);
        void onDeleteClick(Product product);
    }

    public ProductAdapter(OnProductClickListener listener) {
        this.listener = listener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.format("%.2f руб.", product.getPrice()));

        // Проверка на null для безопасности
        if (holder.deleteButton != null) {
            holder.deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(product);
                }
            });
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        final TextView nameTextView;
        final TextView priceTextView;
        final Button deleteButton; // Тип должен соответствовать XML

        ProductViewHolder(View itemView) {
            super(itemView);
            if (itemView == null) throw new AssertionError("ItemView is null");
            nameTextView = itemView.findViewById(R.id.text_name);
            if (nameTextView == null) throw new AssertionError("text_name not found");
            priceTextView = itemView.findViewById(R.id.text_price);
            if (priceTextView == null) throw new AssertionError("text_price not found");
            deleteButton = itemView.findViewById(R.id.button_delete);
            if (deleteButton == null) throw new AssertionError("button_delete not found");
            // Проверка для отладки
            if (deleteButton == null) {
                throw new IllegalStateException("Кнопка удаления не найдена в макете");
            }
        }
    }
}