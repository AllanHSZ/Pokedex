package com.allanhsz.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.allanhsz.pokedex.utils.HandlerErro;
import com.allanhsz.pokedex.model.Pokemon;
import com.allanhsz.pokedex.PokemonAdapter;
import com.allanhsz.pokedex.PokemonService;
import com.allanhsz.pokedex.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.ItemClickListener {

    private ArrayList<Pokemon> pokemons = new ArrayList<>();
    private ImageView loading;
    private RecyclerView rvPokemon;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = findViewById(R.id.Loading);
        rvPokemon = findViewById(R.id.RvPokemon);
        rvPokemon.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PokemonAdapter(pokemons);
        rvPokemon.setAdapter(adapter);

        findViewById(R.id.Add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PokemonActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        getPokemons();
    }

    public void getPokemons(){
        setLoading(true);

        PokemonService.reference.list().enqueue(new Callback<ArrayList<Pokemon>>() {
            @Override
            public void onResponse(Call<ArrayList<Pokemon>> call, Response<ArrayList<Pokemon>> response) {
                setLoading(false);
                if(response.isSuccessful()){
                    pokemons.clear();
                    pokemons.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    new HandlerErro(MainActivity.this, response);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Pokemon>> call, Throwable t) {
                setLoading(false);
                new HandlerErro(MainActivity.this, t);
            }
        });
    }

    public void setLoading(boolean visible){
        rvPokemon.setVisibility(visible ? View.GONE : View.VISIBLE);
        loading.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
