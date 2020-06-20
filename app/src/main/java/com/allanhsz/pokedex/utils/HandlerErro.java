package com.allanhsz.pokedex.utils;

import android.content.Context;
import android.widget.Toast;

import com.allanhsz.pokedex.model.Pokemon;

import java.util.List;

import retrofit2.Response;

public class HandlerErro {

    public HandlerErro(Context context, Throwable t) {
        show(context, t.getMessage());
    }

    public HandlerErro(Context context, Response<List<Pokemon>> response) {
        show(context, response.message());
    }

    private void show(Context context, String mensagem) {
        Toast.makeText(context, "Erro: " + mensagem, Toast.LENGTH_SHORT).show();
    }
}
