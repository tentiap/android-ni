package com.example.nutechwallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.nutechwallet.model.login.LoginData;

import java.util.HashMap;

public class SessionManager {
    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String TOKEN = "token";

    public SessionManager (Context context) {
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession (LoginData user) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(EMAIL, user.getEmail());
        editor.putString(FIRST_NAME, user.getFirstName());
        editor.putString(LAST_NAME, user.getLastName());
        editor.putString(TOKEN, user.getToken());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail() {
        HashMap<String,String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(FIRST_NAME, sharedPreferences.getString(FIRST_NAME, null));
        user.put(LAST_NAME, sharedPreferences.getString(LAST_NAME, null));
        user.put(TOKEN, sharedPreferences.getString(TOKEN, null));
        return user;
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }


}
