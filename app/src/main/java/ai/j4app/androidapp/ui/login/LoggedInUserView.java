package ai.j4app.androidapp.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView {
    private String displayName;
    private String role;
    private String email;

    // Конструктор с одним параметром
    public LoggedInUserView(String displayName) {
        this.displayName = displayName;
        this.role = "default"; // Задайте значение по умолчанию
        this.email = "default"; // Задайте значение по умолчанию
    }
    public LoggedInUserView(String displayName, String role) {
        this.displayName = displayName;
        this.role = role;
    }

    public LoggedInUserView(String displayName, String role, String email) {
        this.displayName = displayName;
        this.role = role;
        this.email = email;
    }
    public String getDisplayName() {
        return displayName;
    }

    public String getRole() {
        return role;
    }
    public String getEmail() {
        return email;
    }

}