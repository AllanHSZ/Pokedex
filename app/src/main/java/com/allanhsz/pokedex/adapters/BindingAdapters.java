package com.allanhsz.pokedex.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.utils.StringUtils;
import com.allanhsz.pokedex.utils.Types;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter("app:loadImage")
    public static void loadImage(ImageView imageView, String url) {
        if (StringUtils.isValidUrl(url)) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_error_outline)
                    .into(imageView);
            return;
        }

        imageView.setImageResource(R.drawable.ic_question);
    }

    @BindingAdapter("app:srcPokemonType")
    public static void srcPokemonType(ImageView imageView, int type) {
        imageView.setVisibility(type == 0 ? View.GONE : View.VISIBLE);
        imageView.setImageResource(Types.getTypeImage(imageView.getContext(), type));
        imageView.setContentDescription(imageView.getContext().getString(R.string.tipo_descricao).replace("#", Types.getTypeName(imageView.getContext(), type)));
    }

}
