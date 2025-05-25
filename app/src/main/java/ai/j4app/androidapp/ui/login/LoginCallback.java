package ai.j4app.androidapp.ui.login;

import data.local.entities.LoggedInUser;

public interface LoginCallback {

    void onSuccess(LoggedInUser user);
//    void onSuccess(LoggedInUser user);

    void onError(String message);

    void onFormStateChanged(LoginFormState formState);
}
