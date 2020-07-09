package com.allanhsz.pokedex.utils;

import com.google.android.material.textfield.TextInputEditText;

public final class Validation {

    private Validation() {
    }

    public static boolean isEmpty(TextInputEditText textInputEditText) {
        return ObjectUtils.isNotNull(textInputEditText.getText()) || StringUtils.isEmpty(textInputEditText.getText());
    }

}
