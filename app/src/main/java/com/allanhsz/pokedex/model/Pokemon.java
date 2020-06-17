package com.allanhsz.pokedex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pokemon implements Parcelable {

    private String id ;
    private String nome;
    private String imagem;
    private int numero;
    private double peso;
    private int[] tipo = new int[2];
    private ArrayList<String> habilidade;

    public Pokemon(){}

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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String img) {
        this.imagem = img;
    }

    public int[] getTipo() {
        return tipo;
    }

    public void setTipo(int[] tipo) {
        this.tipo = tipo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    protected Pokemon(Parcel in) {
        habilidade = in.createStringArrayList();
        id = in.readString();
        nome = in.readString();
        numero = in.readInt();
        imagem = in.readString();
//        type = in.createIntArray();
        peso = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(habilidade);
        dest.writeString(id);
        dest.writeString(nome);
        dest.writeInt(numero);
        dest.writeString(imagem);
//        dest.writeIntArray(type);
        dest.writeDouble(peso);
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
