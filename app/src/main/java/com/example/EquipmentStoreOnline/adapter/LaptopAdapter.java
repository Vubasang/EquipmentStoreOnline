package com.example.EquipmentStoreOnline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.EquipmentStoreOnline.R;
import com.example.EquipmentStoreOnline.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arraylaptop;

    public LaptopAdapter(Context context, ArrayList<Product> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
    }

    @Override
    public int getCount() {
        return arraylaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtnamelaptop, txtpricelaptop, txtdescriptionlaptop;
        public ImageView imglaptop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.laptop, null);
            viewHolder.txtnamelaptop = view.findViewById(R.id.textviewlaptop);
            viewHolder.txtpricelaptop = view.findViewById(R.id.textviewpricelaptop);
            viewHolder.txtdescriptionlaptop = view.findViewById(R.id.textviewdescriptionlaptop);
            viewHolder.imglaptop = view.findViewById(R.id.imageviewlaptop);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHolder.txtnamelaptop.setText(product.getNameproduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtpricelaptop.setText("Цена: " + decimalFormat.format(product.getPriceproduct()) + " р.");
        viewHolder.txtdescriptionlaptop.setMaxLines(2);
        viewHolder.txtdescriptionlaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtdescriptionlaptop.setText(product.getDescriptionproduct());
        Picasso.get().load(product.getImageproduct())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imglaptop);
        return view;
    }
}
