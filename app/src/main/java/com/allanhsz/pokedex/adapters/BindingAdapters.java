package com.allanhsz.pokedex.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.utils.StringUtils;
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

    @BindingAdapter("app:srcVector")
    public static void srcVector(ImageView ImageView, int resource) {
        if (resource != 0)
            ImageView.setImageResource(resource);
    }

}
