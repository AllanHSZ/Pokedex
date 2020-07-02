package com.allanhsz.pokedex.utils;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class Validation {

    public static boolean isEmpty(TextInputEditText textInputEditText){
        return textInputEditText.getText() == null || textInputEditText.getText().toString().trim().isEmpty();
    }

}