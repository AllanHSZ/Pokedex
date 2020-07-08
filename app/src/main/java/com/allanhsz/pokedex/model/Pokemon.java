package com.allanhsz.pokedex.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.allanhsz.pokedex.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class Pokemon implements Parcelable {

    private String id = "";
    private String name = "";
    private String image = "";
    private int number = 0;
    private int[] types = new int[2];

    public Pokemon(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }
    public String getFormattedNumber() {
        return String.format(Locale.US, "%03d", number);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int[] getTypes() {
        return types;
    }

    public int getType(int position) {
        if (types.length > position)
            return types[position];

        return 0;
    }

    public void setTypes(int[] types) {
        this.types = types;
    }

    protected Pokemon(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        number = in.readInt();
        types = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeInt(number);
        dest.writeIntArray(types);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

}
