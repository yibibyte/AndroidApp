package data.local.entities;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;         // Уникальный идентификатор пользователя
    private String displayName;    // Имя пользователя
    private String role;           // Роль пользователя
    private String email;          // Email пользователя

    // Конструктор
    public LoggedInUser(String userId, String displayName, String role, String email) {
        this.userId = userId;
        this.displayName = displayName;
        this.role = role;
        this.email = email; // Добавлено
    }

    // Геттеры
    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() { // Добавлен геттер для email
        return email;
    }

}