package ai.j4app.androidapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ai.j4app.androidapp.R;
import presentation.activities.MainActivity;

public class ClientActivity extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Проверка авторизации
        sessionManager = new SessionManager(this);
        if (!sessionManager.isSessionValid() || sessionManager.getUserRole().equals("guest")) {
            redirectToLogin();
            return;
        }

        setContentView(R.layout.activity_client);

        // Настройка UI
        setupClientDashboard();
    }

    private void setupClientDashboard() {
        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText("Здравствуйте, " + sessionManager.getUserName());

        // Кнопка выхода
        findViewById(R.id.btn_logout).setOnClickListener(v -> logout());

        // Загрузка данных клиента
        loadClientData(sessionManager.getUserId());
    }

    private void loadClientData(String userId) {
        // Загрузка данных из API/БД
    }

    private void logout() {
        sessionManager.logout();
        redirectToLogin();
    }

    private void redirectToLogin() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}