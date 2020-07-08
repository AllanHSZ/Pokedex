package com.allanhsz.pokedex.utils;

import android.util.SparseArray;
import android.widget.BaseAdapter;

public abstract class SparseArrayAdapter<E> extends BaseAdapter {

    private SparseArray<E> data;
    public void setData(SparseArray<E> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public E getItem(int position) {
        return data.valueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return data.keyAt(position);
    }
}