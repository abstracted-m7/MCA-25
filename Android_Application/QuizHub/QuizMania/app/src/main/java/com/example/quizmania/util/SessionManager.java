package com.example.quizmania.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "quizhub_prefs";
    private static final String KEY_UID = "uid";
    private static final String KEY_ROLE = "role";
    private static final String KEY_NAME = "name";
    private static final String KEY_ROLL = "roll_number";
    private static final String KEY_EMAIL = "email";

    private static SessionManager instance;
    private final SharedPreferences prefs;

    private SessionManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void saveUser(String uid, String role) {
        prefs.edit().putString(KEY_UID, uid).putString(KEY_ROLE, role).apply();
    }

    public void saveUserProfile(String uid, String role, String name, String rollNumber, String email) {
        prefs.edit()
                .putString(KEY_UID, uid)
                .putString(KEY_ROLE, role)
                .putString(KEY_NAME, name)
                .putString(KEY_ROLL, rollNumber)
                .putString(KEY_EMAIL, email)
                .apply();
    }

    public String getUid() {
        return prefs.getString(KEY_UID, null);
    }

    public String getRole() {
        return prefs.getString(KEY_ROLE, "STUDENT");
    }

    public String getName() {
        return prefs.getString(KEY_NAME, "Student");
    }

    public String getRollNumber() {
        return prefs.getString(KEY_ROLL, "BWU/MCA/25/001");
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }

    public boolean isTeacher() {
        String role = getRole();
        return "TEACHER".equalsIgnoreCase(role) || "ADMIN".equalsIgnoreCase(role);
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
