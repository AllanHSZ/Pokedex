package com.allanhsz.pokedex;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter( "app:loadImage" )
    public static void loadImage (ImageView ImageView, String url) {
        if (!url.isEmpty()) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_error_outline)
                    .into(ImageView);
        }
    }

    @BindingAdapter( "app:srcVector" )
    public static void srcVector (ImageView ImageView, int resource) {
        if (resource != 0)
            ImageView.setImageResource(resource);
    }
}
