package com.allanhsz.pokedex;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allanhsz.pokedex.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private Context context;
    private ArrayList<Pokemon> pokemons;
    private ItemClickListener onClickListener;
    private int typeSize;

    public PokemonAdapter(Context context, ArrayList<Pokemon> pokemons) {
        this.context = context;
        this.pokemons = pokemons;

        typeSize = (int) context.getResources().getDimension(R.dimen.item_type);
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);

        holder.number.setText(String.valueOf(pokemon.getNumero()));
        holder.name.setText(pokemon.getNome());


        if (pokemon.getTipo() != null){
            for (int i=0; i < pokemon.getTipo().length; i++){
                if(pokemon.getTipo()[i] == 0)
                    continue;

                ImageView type = new ImageView(context);
                type.setLayoutParams(new LinearLayout.LayoutParams(typeSize, typeSize));
                int r = context.getResources().getIdentifier("ic_type"+pokemon.getTipo()[i], "drawable", context.getPackageName());
                type.setBackground(context.getDrawable(r));
                holder.typesContainer.addView(type);
            }
        }

        if (pokemon.getImagem() != null && !pokemon.getImagem().isEmpty()) {
            Picasso.get()
                    .load(pokemon.getImagem())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_error_outline)
                    .into (holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.onClickListener = itemClickListener;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView number, name;
        private LinearLayout typesContainer;

        PokemonViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.Img);
            number = itemView.findViewById(R.id.PokemonNumber);
            name = itemView.findViewById(R.id.Name);
            typesContainer = itemView.findViewById(R.id.TypesContainer);
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
