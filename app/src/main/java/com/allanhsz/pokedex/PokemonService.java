package com.allanhsz.pokedex;

import com.allanhsz.pokedex.model.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PokemonService {

    String BASE_URL = "http://192.241.152.103:8080/";

    @GET("pokemons")
    Call<List<Pokemon>> list();

    @POST("pokemons")
    Call<Void> insert(@Body Pokemon pokemon);

    @PUT("pokemons/{id}")
    Call<Void> update(@Path("id") String id, @Body Pokemon pokemon);

    @DELETE("pokemons/{id}")
    Call<Void> delete(@Path("id") String id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(PokemonService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PokemonService reference  = retrofit.create(PokemonService.class);
}
