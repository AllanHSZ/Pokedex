package com.allanhsz.pokedex.service;

import com.allanhsz.pokedex.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PokemonService {

    String BASE_URL = "http://167.71.255.6:8080/";

    @GET("pokemons")
    Call<List<Pokemon>> list();

    @POST("pokemons")
    Call<Void> insert(@Body Pokemon pokemon);

    PokemonService reference = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService.class);

}
