package com.example.EquipmentStoreOnline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.EquipmentStoreOnline.R;
import com.example.EquipmentStoreOnline.model.MyCart;
import com.example.EquipmentStoreOnline.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailsProduct extends AppCompatActivity {

    Toolbar toolbarDetail;
    ImageView imgDetail;
    TextView txtname, txtprice, txtdescription;
    Spinner spinner;
    Button btnorder;
    int id = 0;
    String NameDetail = "";
    int PriceDetail = 0;
    String ImageDetail = "";
    String DescriptionDetail = "";
    int Idproduct = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        Mapping̣();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(), com.example.EquipmentStoreOnline.activity.MyCart.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void EventButton() {
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.arrayCart.size() > 0){
                    int amount = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.arrayCart.size(); i++){
                        if (MainActivity.arrayCart.get(i).getIdproduct() == id){
                            MainActivity.arrayCart.get(i).setAmountproduct(MainActivity.arrayCart.get(i).getAmountproduct() + amount);
                            if (MainActivity.arrayCart.get(i).getAmountproduct() >= 10){
                                MainActivity.arrayCart.get(i).setAmountproduct(10);
                            }
                            MainActivity.arrayCart.get(i).setPriceproduct(PriceDetail * MainActivity.arrayCart.get(i).getAmountproduct());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int newAmount = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Newprice = newAmount * PriceDetail;
                        MainActivity.arrayCart.add(new MyCart(id, NameDetail, Newprice, ImageDetail, newAmount));
                    }
                }
                else{
                    int AMOUNT = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Newprice = AMOUNT * PriceDetail;
                    MainActivity.arrayCart.add(new MyCart(id, NameDetail, Newprice, ImageDetail, AMOUNT));
                }
                Intent intent = new Intent(getApplicationContext(), com.example.EquipmentStoreOnline.activity.MyCart.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] amount = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayadapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, amount);
        spinner.setAdapter(arrayadapter);
    }

    private void GetInformation() {
        Product product = (Product) getIntent().getSerializableExtra("descriptionproduct");
        id = product.getID();
        NameDetail = product.getNameproduct();
        PriceDetail = product.getPriceproduct();
        ImageDetail = product.getImageproduct();
        DescriptionDetail = product.getDescriptionproduct();
        Idproduct = product.getIDProduct();
        txtname.setText(NameDetail);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtprice.setText("Цена: " + decimalFormat.format(PriceDetail) + " р.");
        txtdescription.setText(DescriptionDetail);
        Picasso.get().load(ImageDetail)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgDetail);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Mapping̣() {
        toolbarDetail = (Toolbar) findViewById(R.id.toolbardetailproduct);
        imgDetail = (ImageView) findViewById(R.id.imageviewdetailproduct);
        txtname = (TextView) findViewById(R.id.textviewnamedetailproduct);
        txtprice = (TextView) findViewById(R.id.textviewpricedetailproduct);
        txtdescription = (TextView) findViewById(R.id.textviewdescriptiondetailproduct);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnorder = (Button) findViewById(R.id.buttonAddToShoppingCart);
    }
}