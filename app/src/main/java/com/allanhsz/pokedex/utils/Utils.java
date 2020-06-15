package com.allanhsz.pokedex.utils;

import android.content.Context;
import android.content.res.Resources;

import com.allanhsz.pokedex.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Utils {

    public static ArrayList<String> getTypes(Context context){
        Resources resources = context.getResources();
        ArrayList<String> types = new ArrayList<>();

        types.add(resources.getString(R.string.normal));
        types.add(resources.getString(R.string.fogo));
        types.add(resources.getString(R.string.lutador));
        types.add(resources.getString(R.string.agua));
        types.add(resources.getString(R.string.voador));
        types.add(resources.getString(R.string.planta));
        types.add(resources.getString(R.string.venenoso));
        types.add(resources.getString(R.string.eletrico));
        types.add(resources.getString(R.string.terreste));
        types.add(resources.getString(R.string.psiquico));
        types.add(resources.getString(R.string.pedra));
        types.add(resources.getString(R.string.gelo));
        types.add(resources.getString(R.string.inseto));
        types.add(resources.getString(R.string.dragao));
        types.add(resources.getString(R.string.fantasma));
        types.add(resources.getString(R.string.sombrio));
        types.add(resources.getString(R.string.aco));
        types.add(resources.getString(R.string.fada));
        return types;
    }

    public static ArrayList<String> getSecondTypes(Context context){
        ArrayList<String> secondType = getTypes(context);
        secondType.add(0, context.getResources().getString(R.string.nenhum));
        return secondType;
    }
}
