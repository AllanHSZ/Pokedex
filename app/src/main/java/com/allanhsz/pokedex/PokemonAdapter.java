package com.allanhsz.pokedex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allanhsz.pokedex.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private ArrayList<Pokemon> pokemon;
    private ItemClickListener onClickListener;

    public PokemonAdapter(ArrayList<Pokemon> pokemons) {
        this.pokemon = pokemons;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.number.setText(String.valueOf(pokemon.get(position).getNumero()));
        holder.name.setText(pokemon.get(position).getNome());

        if (pokemon.get(position).getImg() != null && !pokemon.get(position).getImg().isEmpty()) {
            Picasso.get()
                    .load(pokemon.get(position).getImg())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_error_outline)
                    .into (holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return pokemon.size();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.onClickListener = itemClickListener;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView number, name;

        PokemonViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.Img);
            number = itemView.findViewById(R.id.PokemonNumber);
            name = itemView.findViewById(R.id.Name);
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