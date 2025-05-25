package ai.j4app.androidapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ai.j4app.androidapp.R;
import presentation.activities.MainActivity;

public class AdminActivity extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sessionManager = new SessionManager(this);

        // Проверка прав доступа
        if (!sessionManager.isLoggedIn() || !"admin".equals(sessionManager.getUserRole())) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // Настройка UI
        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText("Добро пожаловать, " + sessionManager.getUserName());

        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            sessionManager.logout();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}