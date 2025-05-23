package data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import data.local.dao.ProductDao;
import data.local.entities.Product;

// AppDatabase.java
@Database(entities = {Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();

    private static volatile AppDatabase INSTANCE;

    // Добавляем Executor для выполнения операций в фоне
    public static final Executor databaseWriteExecutor =
            Executors.newFixedThreadPool(4); // Можно настроить количество потоков

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "product_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}