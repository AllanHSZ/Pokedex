package com.allanhsz.pokedex;

import com.allanhsz.pokedex.model.Pokemon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PokemonService {

    public final String BASE_URL = "http://167.71.255.6:8080/";

    @GET("pokemons")
    Call<ArrayList<Pokemon>> listPokemon();

    @POST("pokemons")
    Call<Void> insertPokemon(@Body Pokemon pokemon);
    
    public static PokemonService build() {
        return retrofit.create(PokemonService.class);
    }

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(PokemonService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    
}
