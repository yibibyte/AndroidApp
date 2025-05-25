package ai.j4app.androidapp.ui.login;

import data.local.entities.UserRole;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private String error;

    private UserRole role;
    LoginResult(@Nullable String error) {
        this.error = error;
    }
    public LoginResult(LoggedInUserView success) {
        this.success = success;
    }

    public LoginResult(LoggedInUserView success, String error, UserRole role) {
        this.success = success;
        this.error = error;
        this.role = role;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    String getError() {
        return error;
    }

    public UserRole getRole() {
        return role;
    }
}