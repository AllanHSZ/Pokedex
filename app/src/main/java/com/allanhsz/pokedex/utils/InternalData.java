package com.allanhsz.pokedex.utils;

import android.content.Context;

public class InternalData {

    private final static String FILE = "INTERNAL_FILE";

    public static void setIP(Context context, String ip){
        context.getSharedPreferences(FILE, 0).edit().putString("IP", ip).apply();
    }
    public static void setPort(Context context, String port){
        context.getSharedPreferences(FILE, 0).edit().putString("PORT", port).apply();
    }

    public static String getIP(Context context){
        return context.getSharedPreferences(FILE, 0).getString("IP", "0");
    }

    public static String getPort(Context context){
        return context.getSharedPreferences(FILE, 0).getString("PORT", "0");
    }

}
