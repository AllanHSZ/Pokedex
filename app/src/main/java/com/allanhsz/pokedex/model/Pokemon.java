package com.allanhsz.pokedex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pokemon implements Parcelable {

    private ArrayList<String> habilidade;
    private String id ;
    private String nome;
    private int numero;
    private String img;
    private ArrayList<String> type;
    private String peso;

    public Pokemon(){

    }

    public ArrayList<String> getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(ArrayList<String> habilidade) {
        this.habilidade = habilidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    protected Pokemon(Parcel in) {
        habilidade = in.createStringArrayList();
        id = in.readString();
        nome = in.readString();
        numero = in.readInt();
        img = in.readString();
        type = in.createStringArrayList();
        peso = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(habilidade);
        dest.writeString(id);
        dest.writeString(nome);
        dest.writeInt(numero);
        dest.writeString(img);
        dest.writeStringList(type);
        dest.writeString(peso);
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
