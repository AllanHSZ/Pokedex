package com.allanhsz.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allanhsz.pokedex.databinding.ItemPokemonBinding;
import com.allanhsz.pokedex.model.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private ArrayList<Pokemon> pokemons;
    private ItemClickListener onClickListener;

    public PokemonAdapter(ArrayList<Pokemon> pokemons) {
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
        holder.itemPokemonBinding.setPokemon(pokemons.get(position));
        holder.itemPokemonBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.onClickListener = itemClickListener;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemPokemonBinding itemPokemonBinding;

        PokemonViewHolder(ItemPokemonBinding binding) {
            super(binding.getRoot());
            this.itemPokemonBinding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) onClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
