package com.allanhsz.pokedex.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allanhsz.pokedex.PokemonService;
import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.adapters.PokemonAdapter;
import com.allanhsz.pokedex.components.LoadingFullScreen;
import com.allanhsz.pokedex.model.Pokemon;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  PokemonAdapter.OnItemListener{

    private ImageView loading;
    private RecyclerView rvPokemon;
    private PokemonAdapter adapter;
    private FloatingActionButton add;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ConstraintLayout errorLayout;
    private TextView errorTitle, errorMessage, notFound;

    private final ArrayList<Pokemon> pokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = findViewById(R.id.Loading);
        errorLayout = findViewById(R.id.ErrorLayout);
        errorTitle = findViewById(R.id.ErrorTitle);
        errorMessage = findViewById(R.id.ErrorMessage);

        rvPokemon = findViewById(R.id.RvPokemon);
        rvPokemon.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(recyclerView.getVisibility() == View.VISIBLE) {
                    int topRowVerticalPosition = recyclerView.getChildCount() == 0 ? 0 : recyclerView.getChildAt(0).getTop();
                    swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        rvPokemon.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.itemsPerRow)));
        rvPokemon.post(new Runnable() {
            @Override
            public void run() {
                int padding = (int)getResources().getDimension(R.dimen.spaceMD);
                rvPokemon.setPadding(padding, padding, padding,(int)(getResources().getDimension(R.dimen.spaceLG)*2+add.getHeight()));
            }
        });

        adapter = new PokemonAdapter(pokemons, this);
        rvPokemon.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPokemons();
            }
        });

        add = findViewById(R.id.Add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PokemonActivity.class));
            }
        });

        notFound = findViewById(R.id.NotFound);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPokemons();
    }

    public void getPokemons() {
        setLoading(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PokemonService.reference.list().enqueue(new Callback<List<Pokemon>>() {
                    @Override
                    public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                        setLoading(false);

                        if (response.isSuccessful()) {
                            pokemons.clear();
                            if (response.body().size() > 0) {
                                pokemons.addAll(response.body());
                                adapter.notifyDataSetChanged();
                            } else {
                                notFound.setVisibility(View.VISIBLE);
                            }
                            return;
                        }

                        setError(getString(R.string.erro) +": " + response.code()+ " !", getString(R.string.erro_inesparado));
                    }

                    @Override
                    public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                        setError(getString(R.string.sem_conexao), getString(R.string.verifique_a_conexao));
                    }
                });
            }
        }, 500);
    }

    public void setLoading(boolean load) {
        notFound.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        rvPokemon.setVisibility(load ? View.GONE : View.VISIBLE);
        loading.setVisibility(load ? View.VISIBLE : View.GONE);

        if (!load && swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    public void setError(String title, String message){
        rvPokemon.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        notFound.setVisibility(View.GONE);

        errorTitle.setText(title);
        errorMessage.setText(message);

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(Pokemon item) {
        startActivity(new Intent(MainActivity.this, PokemonActivity.class).putExtra("pokemon", item));
    }

    @Override
    public void onItemLongClick(final Pokemon pokemon) {
        new MaterialAlertDialogBuilder(MainActivity.this)
            .setTitle("Deletar pokémon ?")
            .setMessage("Não será possivel recuperá-lo.")
            .setPositiveButton("DELETAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final LoadingFullScreen loadingFullScreen = new LoadingFullScreen(MainActivity.this);
                    loadingFullScreen.setMessage("deletando");
                    loadingFullScreen.show();

                    PokemonService.reference.delete(pokemon.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(MainActivity.this, "Pokemon removido.", Toast.LENGTH_SHORT).show();
                            int index = pokemons.indexOf(pokemon);
                            pokemons.remove(index);
                            adapter.notifyItemRemoved(index);

                            loadingFullScreen.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            loadingFullScreen.dismiss();
                        }
                    });
                }
            })
            .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
    }
}
