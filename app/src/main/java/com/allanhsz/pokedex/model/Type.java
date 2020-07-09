package com.allanhsz.pokedex.model;

import com.allanhsz.pokedex.utils.StringUtils;

public class Type {

    private String name;
    private int type;

    public Type(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return StringUtils.valueOrEmpty(name);
    }
}
