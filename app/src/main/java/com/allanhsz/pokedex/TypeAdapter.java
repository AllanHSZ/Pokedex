package com.allanhsz.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.allanhsz.pokedex.model.Type;

import java.util.ArrayList;

public class TypeAdapter extends ArrayAdapter<Type> {

    private ArrayList<Type> list;

    public TypeAdapter( Context context, ArrayList<Type> list) {
        super(context, 0, list);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_menu_popup_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.TextView);
        Type type = getItem(position);

        if (type != null) {
            textView.setText(type.getName());
        }
        return convertView;
    }

}
