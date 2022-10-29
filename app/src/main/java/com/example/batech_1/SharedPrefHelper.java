package com.example.batech_1;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.batech_1.ModelClasses.UserClass;
import com.google.gson.Gson;

public class SharedPrefHelper {
    public static void setSharedPrefrences(Context context, String key, Object Value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Value);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    public static Object getSharedPrefrences(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
//        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
//        Object obj = gson.fromJson(json, Object.class);
        return new Gson().fromJson(json, UserClass.class);
    }
}
