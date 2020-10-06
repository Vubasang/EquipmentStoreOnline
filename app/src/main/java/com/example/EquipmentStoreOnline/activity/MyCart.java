package com.example.EquipmentStoreOnline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.EquipmentStoreOnline.R;
import com.example.EquipmentStoreOnline.adapter.cartAdapter;
import com.example.EquipmentStoreOnline.ultil.CheckConnection;

import java.text.DecimalFormat;

public class MyCart extends AppCompatActivity {

    ListView listviewcart;
    TextView txtnotification;
    static TextView txttotalMoney;
    Button btnpay, btnContinuetobuy;
    Toolbar toolbarcart;
    cartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        Mapping();
        ActionToolbar();
        CheckData();
        EvenUltil();
        CactchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnContinuetobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.arrayCart.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), InformationClient.class);
                    startActivity(intent);
                }
                else{
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Ваша корзина пустая");
                }
            }
        });
    }

    private void CactchOnItemListView() {
        listviewcart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyCart.this);
                builder.setTitle("Подтверждение");
                builder.setMessage("Вы уверены, что хотите удалить этот товар?");
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (MainActivity.arrayCart.size() <= 0){
                            txtnotification.setVisibility(view.VISIBLE);
                        }else {
                            MainActivity.arrayCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            EvenUltil();
                            if (MainActivity.arrayCart.size() <= 0){
                                txtnotification.setVisibility(view.VISIBLE);
                            }else {
                                txtnotification.setVisibility(view.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUltil() {
        long totalMoney = 0;
        for (int i = 0; i < MainActivity.arrayCart.size(); i++){
            totalMoney += MainActivity.arrayCart.get(i).getPriceproduct();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttotalMoney.setText(decimalFormat.format(totalMoney) + " р.");
    }

    private void CheckData() {
        if (MainActivity.arrayCart.size() <= 0){
            cartAdapter.notifyDataSetChanged();
            txtnotification.setVisibility(View.VISIBLE);
            listviewcart.setVisibility(View.INVISIBLE);
        }
        else{
            cartAdapter.notifyDataSetChanged();
            txtnotification.setVisibility(View.INVISIBLE);
            listviewcart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarcart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Mapping() {
        listviewcart = (ListView) findViewById(R.id.listviewcart);
        txtnotification = (TextView) findViewById(R.id.textviewnotification);
        txttotalMoney = (TextView) findViewById(R.id.textviewtotal);
        btnpay = (Button) findViewById(R.id.buttonpay);
        btnContinuetobuy = (Button) findViewById(R.id.buttonContinueshopping);
        toolbarcart = (Toolbar) findViewById(R.id.toolbarcart);
        cartAdapter = new cartAdapter(MyCart.this, MainActivity.arrayCart);
        listviewcart.setAdapter(cartAdapter);
    }
}