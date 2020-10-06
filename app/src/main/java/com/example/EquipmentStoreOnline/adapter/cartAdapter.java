package com.example.EquipmentStoreOnline.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.EquipmentStoreOnline.R;
import com.example.EquipmentStoreOnline.activity.MainActivity;
import com.example.EquipmentStoreOnline.model.MyCart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class cartAdapter extends BaseAdapter{
    Context context;
    ArrayList<MyCart> arraycart;

    public cartAdapter(Context context, ArrayList<MyCart> arraycart) {
        this.context = context;
        this.arraycart = arraycart;
    }

    @Override
    public int getCount() {
        return arraycart.size();
    }

    @Override
    public Object getItem(int i) {
        return arraycart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txtnamecart, txtpricecart;
        public ImageView imgcart;
        public Button btnminus, btnvalues, btnplus;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cart, null);
            viewHolder.txtnamecart = (TextView) view.findViewById(R.id.textviewnamecart);
            viewHolder.txtpricecart = (TextView) view.findViewById(R.id.textviewpricecart);
            viewHolder.imgcart = (ImageView) view.findViewById(R.id.imageviewcart);
            viewHolder.btnminus = (Button) view.findViewById(R.id.buttonminus);
            viewHolder.btnvalues = (Button) view.findViewById(R.id.buttonvalues);
            viewHolder.btnplus = (Button) view.findViewById(R.id.buttonplus);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        MyCart myCart = (MyCart) getItem(i);
        viewHolder.txtnamecart.setText(myCart.getNamrproduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtpricecart.setText(decimalFormat.format(myCart.getPriceproduct()) + " р.");
        Picasso.get().load(myCart.getImageproduct())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgcart);
        viewHolder.btnvalues.setText(myCart.getAmountproduct() + "");
        int amount = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (amount >= 10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        else if(amount <= 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        else if(amount >= 1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolderplus = viewHolder;
        final ViewHolder finalViewHolderminus = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newAmount = Integer.parseInt(finalViewHolderplus.btnvalues.getText().toString()) + 1;
                int presentAmount = MainActivity.arrayCart.get(i).getAmountproduct();
                long pricePresent = MainActivity.arrayCart.get(i).getPriceproduct();
                MainActivity.arrayCart.get(i).setAmountproduct((newAmount));
                long priceNew = (pricePresent * newAmount) / presentAmount;
                MainActivity.arrayCart.get(i).setPriceproduct(priceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolderplus.txtpricecart.setText(decimalFormat.format(priceNew) + " р.");
                com.example.EquipmentStoreOnline.activity.MyCart.EvenUltil();
                if (newAmount > 9) {
                    finalViewHolderplus.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolderplus.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolderplus.btnvalues.setText(String.valueOf(newAmount));
                }
                else{
                    finalViewHolderplus.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolderplus.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolderplus.btnvalues.setText(String.valueOf(newAmount));
                }
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newAmount = Integer.parseInt(finalViewHolderminus.btnvalues.getText().toString()) - 1;
                int presentAmount = MainActivity.arrayCart.get(i).getAmountproduct();
                long pricePresent = MainActivity.arrayCart.get(i).getPriceproduct();
                MainActivity.arrayCart.get(i).setAmountproduct((newAmount));
                long priceNew = (pricePresent * newAmount) / presentAmount;
                MainActivity.arrayCart.get(i).setPriceproduct(priceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolderminus.txtpricecart.setText(decimalFormat.format(priceNew) + " р.");
                com.example.EquipmentStoreOnline.activity.MyCart.EvenUltil();
                if (newAmount < 2) {
                    finalViewHolderminus.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolderminus.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolderminus.btnvalues.setText(String.valueOf(newAmount));
                }
                else{
                    finalViewHolderminus.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolderminus.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolderminus.btnvalues.setText(String.valueOf(newAmount));
                }
            }
        });
        return view;
    }
}
