package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesImpl implements Preferences {

    private static final String PREF_NAME = "org.kaaproject.kaa.examples.gpiocontrol";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private static PreferencesImpl instance;
    private final SharedPreferences preferences;

    private PreferencesImpl(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = new PreferencesImpl(context);
        }
    }

    public static synchronized Preferences getInstance() {
        if (instance == null) {
            throw new IllegalStateException(Preferences.class.getSimpleName() +
                    " is not initialized, call init(..) method first.");
        }
        return instance;
    }

    @Override public void saveEmail(final String email) {
        preferences.edit()
                .putString(KEY_EMAIL, email)
                .apply();
    }

    @Override public String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    @Override public boolean isEmailExists() {
        return preferences.getString(KEY_EMAIL, null) != null;
    }

    @Override public void removeEmail() {
        preferences.edit().remove(KEY_EMAIL).apply();
    }

    @Override public void cleanUp() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().apply();
    }

    @Override public void savePassword(String password) {
        preferences.edit()
                .putString(KEY_EMAIL, password)
                .apply();
    }

    @Override public String getPassword() {
        return preferences.getString(KEY_PASSWORD, null);
    }

}
