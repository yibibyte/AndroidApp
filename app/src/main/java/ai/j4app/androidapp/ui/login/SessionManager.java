package ai.j4app.androidapp.ui.login;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "AuthPref";
    protected static final String KEY_ROLE = "role";
    protected static final String KEY_EXPIRY = "expiry";
    private static final String KEY_USER_ID = "user_id";
    protected static final String KEY_USERNAME = "username";
    protected static SharedPreferences pref;
    private static final int SESSION_DURATION = 7 * 24 * 60 * 60 * 1000; // 7 дней

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public void createSession(String username, String role) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ROLE, role);
        editor.putLong(KEY_EXPIRY, System.currentTimeMillis() + SESSION_DURATION);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.contains(KEY_USERNAME) &&
                pref.contains(KEY_ROLE) &&
                pref.getLong(KEY_EXPIRY, 0) > System.currentTimeMillis();
    }
    public String getUserId() {
        return pref.getString(KEY_USER_ID, null);
    }

    public String getUserName() {
        return pref.getString(KEY_USERNAME, "Гость");
    }

    public boolean isSessionValid() {
        return pref.getLong(KEY_EXPIRY, 0) > System.currentTimeMillis();
    }

    public String getUserRole() {
        return pref.getString(KEY_ROLE, "guest");
    }

    public void logout() {
        pref.edit().clear().apply();
    }
}