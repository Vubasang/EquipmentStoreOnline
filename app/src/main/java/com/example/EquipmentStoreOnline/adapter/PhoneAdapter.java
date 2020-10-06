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

public class PhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayphone;

    public PhoneAdapter(Context context, ArrayList<Product> arrayphone) {
        this.context = context;
        this.arrayphone = arrayphone;
    }

    @Override
    public int getCount() {
        return arrayphone.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayphone.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtnamephone, txtpricephone, txtdescriptionphone;
        public ImageView imgphone;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.phone, null);
            viewHolder.txtnamephone = view.findViewById(R.id.textviewphone);
            viewHolder.txtpricephone = view.findViewById(R.id.textviewpricephone);
            viewHolder.txtdescriptionphone = view.findViewById(R.id.textviewdescriptionphone);
            viewHolder.imgphone = view.findViewById(R.id.imageviewphone);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHolder.txtnamephone.setText(product.getNameproduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtpricephone.setText("Цена: " + decimalFormat.format(product.getPriceproduct()) + " р.");
        viewHolder.txtdescriptionphone.setMaxLines(2);
        viewHolder.txtdescriptionphone.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtdescriptionphone.setText(product.getDescriptionproduct());
        Picasso.get().load(product.getImageproduct())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgphone);
        return view;
    }
}
