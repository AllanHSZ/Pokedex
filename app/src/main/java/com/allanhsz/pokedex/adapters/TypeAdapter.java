package com.allanhsz.pokedex.adapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.allanhsz.pokedex.R;

public class TypeAdapter extends ArrayAdapter<String> {

    private final SparseArray<String> list;
    private final Context context;

    public TypeAdapter(Context context, SparseArray<String> list) {
        super(context, 0);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.dropdown_menu_popup_item, parent, false);

        ((TextView) view.findViewById(R.id.TextView)).setText(list.valueAt(position));

        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.valueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return list.keyAt(position);
    }

}