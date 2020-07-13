package com.allanhsz.pokedex.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allanhsz.pokedex.PokemonService;
import com.allanhsz.pokedex.activities.PokemonActivity;
import com.allanhsz.pokedex.components.LoadingFullScreen;
import com.allanhsz.pokedex.databinding.ItemPokemonBinding;
import com.allanhsz.pokedex.model.Pokemon;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private final Context context;
    private final List<Pokemon> pokemons;

    public PokemonAdapter(Context context, List<Pokemon> pokemons) {
        this.context = context;
        this.pokemons = pokemons;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPokemonBinding item = ItemPokemonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokemonViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.position = position;
        holder.itemPokemonBinding.setPokemon(pokemons.get(position));
        holder.itemPokemonBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int position;
        private final ItemPokemonBinding itemPokemonBinding;

        PokemonViewHolder(ItemPokemonBinding binding) {
            super(binding.getRoot());
            this.itemPokemonBinding = binding;
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    new MaterialAlertDialogBuilder(context)
                            .setTitle("Deletar pokémon ?")
                            .setMessage("Não será possivel recuperá-lo.")
                            .setPositiveButton("DELETAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    final LoadingFullScreen loadingFullScreen = new LoadingFullScreen(context);
                                    loadingFullScreen.setMessage("deletando");
                                    loadingFullScreen.show();

                                    PokemonService.reference.delete(itemPokemonBinding.getPokemon().getId()).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            Toast.makeText(context, "Pokemon removido.", Toast.LENGTH_SHORT).show();
                                            pokemons.remove(position);
                                            notifyItemRemoved(position);

                                            loadingFullScreen.dismiss();
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    return false;
                }
            });
        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, PokemonActivity.class).putExtra("pokemon", itemPokemonBinding.getPokemon()));
        }

    }

}
