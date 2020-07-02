package com.allanhsz.pokedex.Components;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.allanhsz.pokedex.R;

import java.util.Objects;

public class LoadingFullScreen extends AlertDialog {

    public LoadingFullScreen(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setView(LayoutInflater.from(context).inflate(R.layout.loading_fullscreen, (ViewGroup) findViewById(android.R.id.content), false));
        create();
        setCancelable(false);

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setMessage(String text){
        ((TextView)findViewById(R.id.Message)).setText(text);
    }
}
