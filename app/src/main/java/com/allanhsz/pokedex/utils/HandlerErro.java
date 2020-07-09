package com.allanhsz.pokedex.utils;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Response;

public class HandlerErro {

    public HandlerErro(Context context, Throwable t) {
        show(context, t.getMessage());
    }

    public HandlerErro(Context context, Response<?> response) {
        show(context, response.message());
    }

    private void show(Context context, String mensagem) {
        Toast.makeText(context, "Erro: " + mensagem, Toast.LENGTH_SHORT).show();
    }
}
