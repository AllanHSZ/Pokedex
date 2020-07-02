package com.allanhsz.pokedex;

import android.content.Context;
import android.content.res.Resources;


import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.model.Pokemon;
import com.allanhsz.pokedex.model.Type;

import java.util.ArrayList;

public class Types {

    public static final int NORMAL   = 1;
    public static final int FIRE     = 2;
    public static final int FIGHTING = 3;
    public static final int WATER    = 4;
    public static final int FLYING   = 5;
    public static final int GRASS    = 6;
    public static final int POISON   = 7;
    public static final int ELECTRIC = 8;
    public static final int GROUND   = 9;
    public static final int PYSCHIC  = 10;
    public static final int ROCK     = 11;
    public static final int ICE      = 12;
    public static final int BUG      = 13;
    public static final int DRAGON   = 14;
    public static final int GHOST    = 15;
    public static final int DARK     = 16;
    public static final int STELL    = 17;
    public static final int FAIRY    = 18;


    public void getType(){

    }

//    public String getTypes(Context context, @Types int type) {
//        return  context.getString(r);
//    }

    public static ArrayList<Type> getTypes(Context context){
        Resources resources = context.getResources();
        ArrayList<Type> types = new ArrayList<>();
        types.add(new Type(resources.getString(R.string.type1), 1));
        types.add(new Type(resources.getString(R.string.type2), 2));
        types.add(new Type(resources.getString(R.string.type3), 3));
        types.add(new Type(resources.getString(R.string.type4), 4));
        types.add(new Type(resources.getString(R.string.type5), 5));
        types.add(new Type(resources.getString(R.string.type6), 6));
        types.add(new Type(resources.getString(R.string.type7), 7));
        types.add(new Type(resources.getString(R.string.type8), 8));
        types.add(new Type(resources.getString(R.string.type9), 9));
        types.add(new Type(resources.getString(R.string.type10), 10));
        types.add(new Type(resources.getString(R.string.type11), 11));
        types.add(new Type(resources.getString(R.string.type12), 12));
        types.add(new Type(resources.getString(R.string.type13), 13));
        types.add(new Type(resources.getString(R.string.type14), 14));
        types.add(new Type(resources.getString(R.string.type15), 15));
        types.add(new Type(resources.getString(R.string.type16), 16));
        types.add(new Type(resources.getString(R.string.type17), 17));
        types.add(new Type(resources.getString(R.string.type18), 18));
        return types;
    }

    public static int getTypeImage(Context context, int type){
        if (type > 0)
            return context.getResources().getIdentifier("ic_type"+type, "drawable", context.getPackageName());

        return 0;
    }
//    public static ArrayList<String> getSecondTypes(Context context){
//        ArrayList<String> secondType = getTypes(context);
//        secondType.add(0, context.getResources().getString(R.string.nenhum));
//        return secondType;
//    }
}