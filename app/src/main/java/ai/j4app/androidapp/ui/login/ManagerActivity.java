package ai.j4app.androidapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ai.j4app.androidapp.R;
import presentation.activities.MainActivity;

public class ManagerActivity extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Проверка авторизации и роли
        sessionManager = new SessionManager(this);
        if (!sessionManager.isSessionValid() || !sessionManager.getUserRole().equals("manager")) {
            redirectToLogin();
            return;
        }

        setContentView(R.layout.activity_manager);

        // Приветствие пользователя
        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText("Добро пожаловать, менеджер " + sessionManager.getUserName());

        // Инициализация UI элементов
        setupManagerDashboard();
    }

    private void redirectToLogin() {
            // Создаем Intent для перехода на MainActivity
            Intent intent = new Intent(this, MainActivity.class);

            // Очищаем стек задач и запускаем новую задачу
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            // Проверяем, что Activity не завершается или не уничтожена
            if (!isFinishing() && !isDestroyed()) {
                startActivity(intent); // Запускаем новую Activity
            }

            finish(); // Завершаем текущую Activity
    }

    private void setupManagerDashboard() {
        // Реализация специфичных для менеджера функций
        Button viewReportsBtn = findViewById(R.id.btn_view_reports);
        viewReportsBtn.setOnClickListener(v -> showReports());

        // ... другие элементы управления
    }

    private void showReports() {
        // Логика показа отчетов
    }
}