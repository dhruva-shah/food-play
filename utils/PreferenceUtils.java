package com.restaurantsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.restaurantsapp.R;

public class PreferenceUtils {

    private Context mContext;
    private SharedPreferences sharedPreferences;

    public static final String TOKEN = "TOKEN";
    public static final String USER_ID = "USER_ID";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String PROFILE_URL = "PROFILE_URL";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_CONTACT_NUMBER = "USER_CONTACT_NUMBER";
    public static final String EMAIL = "EMAIL";


    public PreferenceUtils(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = (SharedPreferences) mContext.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void setString(final String key, final String value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(final String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setInt(final String key, final int value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(final String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void clearAllPreferenceData() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


}
