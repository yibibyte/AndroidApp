package ai.j4app.androidapp.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

import ai.j4app.androidapp.R;
import data.local.entities.LoggedInUser;

public class LoginViewModelImpl extends ViewModel {
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final MutableLiveData<String> userRole = new MutableLiveData<>();

    private final LoginCallback loginCallback = new LoginCallback() {
        @Override
        public void onSuccess(LoggedInUser user) {

            LoggedInUserView userView = new LoggedInUserView(
                    user.getDisplayName(),
                    user.getRole(),
                    user.getEmail()
            );
            loginResult.postValue(new LoginResult(userView));

            if (user.getEmail() != null && user.getEmail().equals("admin@example.com")) {
                userRole.postValue("admin");
            }
        }

        @Override
        public void onError(String message) {
            loginResult.postValue(new LoginResult(message));
        }

        @Override
        public void onFormStateChanged(LoginFormState formState) {
            loginFormState.postValue(formState);
        }
    };

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public LiveData<String> getUserRole() {
        return userRole;
    }

    public void loginDataChanged(String username, String password) {
        Integer usernameError = isUserNameValid(username) ? null : R.string.invalid_username;
        Integer passwordError = isPasswordValid(password) ? null : R.string.invalid_password;

        loginCallback.onFormStateChanged(
                new LoginFormState(usernameError, passwordError)
        );
    }

    public void login(String username, String password) {
        // Можно запускать в корутине или фоновом потоке
        new Thread(() -> {
            try {
                // Имитация сетевого запроса
                Thread.sleep(1000);

                if (isUserNameValid(username) && isPasswordValid(password)) {
                    LoggedInUser fakeUser = new LoggedInUser(
                            UUID.randomUUID().toString(),
                            "Jane Doe",
                            "GOD",
                            "jane.doe@god.com" // Пример email
                    );
                    loginCallback.onSuccess(fakeUser);
                } else {
                    loginCallback.onError("Invalid credentials");
                }
            } catch (InterruptedException e) {
                loginCallback.onError("Login failed");
            }
        }).start();
    }

    private boolean isUserNameValid(String username) {
        if (username == null) return false;

        return username.contains("@")
                ? Patterns.EMAIL_ADDRESS.matcher(username).matches()
                : !username.trim().isEmpty();
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}