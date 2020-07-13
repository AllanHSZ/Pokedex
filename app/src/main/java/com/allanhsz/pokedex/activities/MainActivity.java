package com.allanhsz.pokedex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allanhsz.pokedex.PokemonService;
import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.adapters.PokemonAdapter;
import com.allanhsz.pokedex.model.Pokemon;
import com.allanhsz.pokedex.utils.HandlerErro;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView loading;
    private RecyclerView rvPokemon;
    private PokemonAdapter adapter;
    private FloatingActionButton add;

    private final ArrayList<Pokemon> pokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = findViewById(R.id.Loading);

        rvPokemon = findViewById(R.id.RvPokemon);
        rvPokemon.setLayoutManager(new GridLayoutManager(this, 1));
        rvPokemon.post(new Runnable() {
            @Override
            public void run() {
                rvPokemon.setPadding(0 , (int)getResources().getDimension(R.dimen.spaceMD),0,(int)(getResources().getDimension(R.dimen.spaceLG)*2+add.getHeight()));
            }
        });


        adapter = new PokemonAdapter(this, pokemons);
        rvPokemon.setAdapter(adapter);

        add = findViewById(R.id.Add);

        add.setOnClickListener(new View.OnClickListener() {
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

    public void getPokemons() {
        setLoading(true);

        PokemonService.reference.list().enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                setLoading(false);

                if (response.isSuccessful()) {
                    pokemons.clear();
                    pokemons.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    return;
                }

                new HandlerErro(MainActivity.this, response);
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                setLoading(false);
                new HandlerErro(MainActivity.this, t);
            }
        });
    }

    public void setLoading(boolean visible) {
        rvPokemon.setVisibility(visible ? View.GONE : View.VISIBLE);
        loading.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

}
