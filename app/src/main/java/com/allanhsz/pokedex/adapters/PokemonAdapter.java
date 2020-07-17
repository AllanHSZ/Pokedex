package com.allanhsz.pokedex.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.allanhsz.pokedex.databinding.ItemPokemonBinding;
import com.allanhsz.pokedex.model.Pokemon;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private final List<Pokemon> pokemons;
    private OnItemListener listener;

    public PokemonAdapter(List<Pokemon> pokemons, OnItemListener listener) {
        this.pokemons = pokemons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPokemonBinding item = ItemPokemonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokemonViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.itemPokemonBinding.setPokemon(pokemons.get(position));
        holder.itemPokemonBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }


    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private final ItemPokemonBinding itemPokemonBinding;

        PokemonViewHolder(ItemPokemonBinding binding) {
            super(binding.getRoot());
            this.itemPokemonBinding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onItemClick(itemPokemonBinding.getPokemon());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null)
                        listener.onItemLongClick(itemPokemonBinding.getPokemon());
                    return false;
                }
            });
        }

    }

    public interface OnItemListener {
        void onItemClick(Pokemon item);
        void onItemLongClick(Pokemon item);
    }
}
