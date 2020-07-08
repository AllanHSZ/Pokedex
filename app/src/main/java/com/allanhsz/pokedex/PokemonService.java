package com.allanhsz.pokedex;

import com.allanhsz.pokedex.model.Pokemon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PokemonService {

    public final String BASE_URL = "http://167.71.255.6:8080/";

    @GET("pokemons")
    Call<ArrayList<Pokemon>> list();

    @POST("pokemons")
    Call<Void> insert(@Body Pokemon pokemon);

    @PUT("pokemons/{id}")
    Call<Void> update(@Path("id") String id, @Body Pokemon pokemon);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(PokemonService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PokemonService reference  = retrofit.create(PokemonService.class);
}
