package com.allanhsz.pokedex.utils;

import android.view.View;
import android.view.ViewGroup;

public class Utils {

    public static void setViewHeight(View view, int height){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }
}
