package com.flaxeninfosoft.sarwateAcademy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.flaxeninfosoft.sarwateAcademy.models.LoginModel;
import com.flaxeninfosoft.sarwateAcademy.models.User;

public class SharedPrefs {

    private SharedPreferences pref;
    private User currentUser;

    public static final String PREFERENCE_NAME = "SARWATE_ACADEMY";
    public static final int MODE = Context.MODE_PRIVATE;

    private static final String ID = "USER_ID";
    private static final String ROLE = "USER_ROLE";
    private static final String PHONE = "USER_PHONE";
    private static final String PASSWORD = "USER_PASSWORD";
    private static final String NAME = "USER_NAME";
    private static final String EMAIL = "USER_EMAIL";
    private static final String PROFILE_IMAGE = "USER_PROFILE_IMAGE";


    private static final String CITY = "USER_CITY";
    private static final String STATE = "USER_STATE";
    private static final String GENDER = "USER_GENDER";

    private static SharedPrefs instance;
    Context context;

    public SharedPrefs(Context context) {
        this.context = context;
    }

    public static SharedPrefs getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefs(context);
        }

        return instance;
    }

    public void clearCredentials() {
        pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().apply();
        currentUser = null;
    }

    public void setCredentials(User user) {
        pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PHONE, user.getPhone());
        editor.putString(PASSWORD, user.getPassword());
        editor.putLong(ID, user.getId());
        editor.putString(NAME, user.getName());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(ROLE, user.getRole());
        editor.putString(PROFILE_IMAGE, user.getProfileImg());
        editor.putString(CITY, user.getCity());
        editor.putString(STATE, user.getState());
        editor.putString(GENDER, user.getGender());
        editor.apply();
    }

    public User getCredentials() {
        pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        User user = new User();
        user.setPhone(pref.getString(PHONE, null));
        user.setPassword(pref.getString(PASSWORD, null));
        user.setId(pref.getLong(ID, 0));
        user.setName(pref.getString(NAME, null));
        user.setEmail(pref.getString(EMAIL, null));
        user.setRole(pref.getString(ROLE, null));
        user.setProfileImg(pref.getString(PROFILE_IMAGE, null));
        user.setCity(pref.getString(CITY, null));
        user.setState(pref.getString(STATE, null));
        user.setGender(pref.getString(GENDER, null));

        if (user.getPhone() == null || user.getPassword() == null || user.getRole() == null || user.getName() == null) {
            return null;
        }
        return user;
    }

    public boolean isLoggedIn() {
        pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        User user = new User();
        user.setPhone(pref.getString(PHONE, null));
        user.setPassword(pref.getString(PASSWORD, null));
        user.setId(pref.getLong(ID, 0));
        user.setName(pref.getString(NAME, null));
        user.setEmail(pref.getString(EMAIL, null));
        user.setRole(pref.getString(ROLE, null));
        user.setProfileImg(pref.getString(PROFILE_IMAGE, null));
        user.setCity(pref.getString(CITY, null));
        user.setState(pref.getString(STATE, null));
        user.setGender(pref.getString(GENDER, null));
        return user.getPhone() != null || user.getPassword() != null || user.getRole() != null || user.getName() != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }
}
