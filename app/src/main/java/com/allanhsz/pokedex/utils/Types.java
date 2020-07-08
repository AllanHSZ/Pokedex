package com.allanhsz.pokedex.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.SparseArray;


import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.model.Pokemon;
import com.allanhsz.pokedex.model.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class Types {

    public static final int NENHUM   = 0;
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

    public static ArrayList<Type> getTypes(Context context){
        ArrayList<Type> types = new ArrayList<>();
        types.add(new Type(context.getString(R.string.type1), NORMAL));
        types.add(new Type(context.getString(R.string.type2), FIRE));
        types.add(new Type(context.getString(R.string.type3), FIGHTING));
        types.add(new Type(context.getString(R.string.type4), WATER));
        types.add(new Type(context.getString(R.string.type5), FLYING));
        types.add(new Type(context.getString(R.string.type6), GRASS));
        types.add(new Type(context.getString(R.string.type7), POISON));
        types.add(new Type(context.getString(R.string.type8), ELECTRIC));
        types.add(new Type(context.getString(R.string.type9), GROUND));
        types.add(new Type(context.getString(R.string.type10), PYSCHIC));
        types.add(new Type(context.getString(R.string.type11), ROCK));
        types.add(new Type(context.getString(R.string.type12), ICE));
        types.add(new Type(context.getString(R.string.type13), BUG));
        types.add(new Type(context.getString(R.string.type14), DRAGON));
        types.add(new Type(context.getString(R.string.type15), GHOST));
        types.add(new Type(context.getString(R.string.type16), DARK));
        types.add(new Type(context.getString(R.string.type17), STELL));
        types.add(new Type(context.getString(R.string.type18), FAIRY));
        return types;
    }

    public static SparseArray<String> getHashType(Context context){
        SparseArray<String> types = new SparseArray<>();
        types.put(NENHUM, context.getString(R.string.type0));
        types.put(NORMAL, context.getString(R.string.type1));
        types.put(FIRE, context.getString(R.string.type2));
        types.put(FIGHTING, context.getString(R.string.type3));
        types.put(WATER, context.getString(R.string.type4));
        types.put(FLYING, context.getString(R.string.type5));
        types.put(GRASS, context.getString(R.string.type6));
        types.put(POISON, context.getString(R.string.type7));
        types.put(ELECTRIC, context.getString(R.string.type8));
        types.put(GROUND, context.getString(R.string.type9));
        types.put(PYSCHIC, context.getString(R.string.type10));
        types.put(ROCK, context.getString(R.string.type11));
        types.put(ICE, context.getString(R.string.type12));
        types.put(BUG, context.getString(R.string.type13));
        types.put(DRAGON, context.getString(R.string.type14));
        types.put(GHOST, context.getString(R.string.type15));
        types.put(DARK, context.getString(R.string.type16));
        types.put(STELL, context.getString(R.string.type17));
        types.put(FAIRY, context.getString(R.string.type18));
        return types;
    }

    public static int getTypeImage(Context context, int type){
        if (type > 0)
            return context.getResources().getIdentifier("ic_type"+type, "drawable", context.getPackageName());

        return 0;
    }

    public static int getTypeColor(Context context, int type){
        return context.getColor(context.getResources().getIdentifier("type"+type, "color", context.getPackageName()));
    }

    public static String getTypeName(Context context, int type){
        return context.getString(context.getResources().getIdentifier("type"+type, "string", context.getPackageName()));
//        context.getString()
    }

//    public static ArrayList<String> getSecondTypes(Context context){
//        ArrayList<String> secondType = getTypes(context);
//        secondType.add(0, context.getResources().getString(R.string.nenhum));
//        return secondType;
//    }
}