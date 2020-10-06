package com.example.EquipmentStoreOnline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.EquipmentStoreOnline.R;
import com.example.EquipmentStoreOnline.model.Typeproduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TypeproductAdapter extends BaseAdapter {
    ArrayList<Typeproduct> arraylisttypeproduct;
    Context context;

    public TypeproductAdapter(ArrayList<Typeproduct> arraylisttypeproduct, Context context) {
        this.arraylisttypeproduct = arraylisttypeproduct;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylisttypeproduct.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylisttypeproduct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtnametypeproduct;
        ImageView imgtypeproduct;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if ( view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_typeproduct, null);
            viewHolder.txtnametypeproduct = (TextView) view.findViewById(R.id.textviewtypeproduct);
            viewHolder.imgtypeproduct = (ImageView) view.findViewById(R.id.imageviewtypeproduct);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        Typeproduct typeproduct = (Typeproduct) getItem(i);
        viewHolder.txtnametypeproduct.setText(typeproduct.getNametypeproduct());
        Picasso.get().load(typeproduct.getImagetypeproduct())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgtypeproduct);

        return view;
    }
}
