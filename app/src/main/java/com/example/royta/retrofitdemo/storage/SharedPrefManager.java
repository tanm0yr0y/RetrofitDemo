package com.example.royta.retrofitdemo.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.royta.retrofitdemo.ModelClass.User;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private Context mContext;
    private static final String SHRD_PREF_NAME = "user_data";

    private SharedPrefManager(Context context) {
        mContext = context;
    }
    public static synchronized SharedPrefManager getSharedPrefManager(Context context) {
        if(mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveUsers(User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHRD_PREF_NAME, Context.MODE_PRIVATE);   // SharedPreferences return instance of the existing file otherwise create new file and return its instance
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("name", user.getName());
        editor.putString("school", user.getSchool());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHRD_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getInt("id", -1) != -1) {
            return true;
        }
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHRD_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("school", null));

    }
    public void clear() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHRD_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
