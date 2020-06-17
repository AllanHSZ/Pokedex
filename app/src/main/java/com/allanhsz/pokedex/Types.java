package com.allanhsz.pokedex;

import android.content.Context;
import android.content.res.Resources;


import com.allanhsz.pokedex.R;

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

    public static ArrayList<String> getTypes(Context context){
        Resources resources = context.getResources();
        ArrayList<String> types = new ArrayList<>();
        types.add(resources.getString(R.string.type1));
        types.add(resources.getString(R.string.type2));
        types.add(resources.getString(R.string.type3));
        types.add(resources.getString(R.string.type4));
        types.add(resources.getString(R.string.type5));
        types.add(resources.getString(R.string.type6));
        types.add(resources.getString(R.string.type7));
        types.add(resources.getString(R.string.type8));
        types.add(resources.getString(R.string.type9));
        types.add(resources.getString(R.string.type10));
        types.add(resources.getString(R.string.type11));
        types.add(resources.getString(R.string.type12));
        types.add(resources.getString(R.string.type13));
        types.add(resources.getString(R.string.type14));
        types.add(resources.getString(R.string.type15));
        types.add(resources.getString(R.string.type16));
        types.add(resources.getString(R.string.type17));
        types.add(resources.getString(R.string.type18));
        return types;
    }

//    public static ArrayList<String> getSecondTypes(Context context){
//        ArrayList<String> secondType = getTypes(context);
//        secondType.add(0, context.getResources().getString(R.string.nenhum));
//        return secondType;
//    }
}