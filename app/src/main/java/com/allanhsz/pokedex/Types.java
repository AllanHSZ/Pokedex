package com.allanhsz.pokedex;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Types {

    public static final PokemonType NORMAL = getType(1, R.string.type1);
    public static final PokemonType FIRE = getType(2, R.string.type2);
    public static final PokemonType FIGHTING = getType(3, R.string.type3);
    public static final PokemonType WATER = getType(4, R.string.type4);
    public static final PokemonType FLYING = getType(5, R.string.type5);
    public static final PokemonType GRASS = getType(6, R.string.type6);
    public static final PokemonType POISON = getType(7, R.string.type7);
    public static final PokemonType ELECTRIC = getType(8, R.string.type8);
    public static final PokemonType GROUND = getType(9, R.string.type9);
    public static final PokemonType PYSCHIC = getType(10, R.string.type10);
    public static final PokemonType ROCK = getType(11, R.string.type11);
    public static final PokemonType ICE = getType(12, R.string.type12);
    public static final PokemonType BUG = getType(13, R.string.type13);
    public static final PokemonType DRAGON = getType(14, R.string.type14);
    public static final PokemonType GHOST = getType(15, R.string.type15);
    public static final PokemonType DARK = getType(16, R.string.type16);
    public static final PokemonType STELL = getType(17, R.string.type17);
    public static final PokemonType FAIRY = getType(18, R.string.type18);

    private static final List<PokemonType> typeList = Arrays.asList(
            NORMAL, FIRE, FIGHTING, WATER, FLYING, GRASS, POISON, ELECTRIC,
            GROUND, PYSCHIC, ROCK, ICE, BUG, DRAGON, GHOST, DARK, STELL, FAIRY
    );

    private static PokemonType getType(int number, int nameId) {
        return new PokemonType(number, nameId);
    }

    public static List<String> getTypeNames(Context context) {
        Resources resources = context.getResources();

        List<String> types = new ArrayList<>();

        for (PokemonType type : typeList) {
            types.add(resources.getString(type.nameId));
        }

        return types;
    }

    private static class PokemonType {
        public int number;
        public int nameId;

        public PokemonType(int number, int nameId) {
            this.number = number;
            this.nameId = nameId;
        }
    }
}