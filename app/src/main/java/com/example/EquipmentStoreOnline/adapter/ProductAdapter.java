package com.example.EquipmentStoreOnline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.EquipmentStoreOnline.R;
import com.example.EquipmentStoreOnline.activity.DetailsProduct;
import com.example.EquipmentStoreOnline.model.Product;
import com.example.EquipmentStoreOnline.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<Product> arrayproduct;

    public ProductAdapter(Context context, ArrayList<Product> arrayproduct) {
        this.context = context;
        this.arrayproduct = arrayproduct;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsproduct, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Product product = arrayproduct.get(position);
        holder.txtnameproduct.setText(product.getNameproduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtpriceproduct.setText("Цена: " + decimalFormat.format(product.getPriceproduct()) + " р.");
        Picasso.get().load(product.getImageproduct())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgimageproduct);
    }

    @Override
    public int getItemCount() {
        return arrayproduct.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgimageproduct;
        public TextView txtnameproduct, txtpriceproduct;

        public ItemHolder(View itemView) {
            super(itemView);
            imgimageproduct = (ImageView) itemView.findViewById(R.id.imageviewproduct);
            txtpriceproduct = (TextView) itemView.findViewById(R.id.textviewpriceproduct);
            txtnameproduct = (TextView) itemView.findViewById(R.id.textviewnameproduct);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailsProduct.class);
                    intent.putExtra("descriptionproduct", arrayproduct.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context, arrayproduct.get(getPosition()).getNameproduct());
                    context.startActivity(intent);
                }
            });
        }
    }
}
