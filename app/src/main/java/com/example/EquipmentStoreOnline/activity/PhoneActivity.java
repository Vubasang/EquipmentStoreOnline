package com.example.EquipmentStoreOnline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.EquipmentStoreOnline.R;
import com.example.EquipmentStoreOnline.adapter.PhoneAdapter;
import com.example.EquipmentStoreOnline.model.Product;
import com.example.EquipmentStoreOnline.ultil.CheckConnection;
import com.example.EquipmentStoreOnline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneActivity extends AppCompatActivity {
    Toolbar toolbarPhone;
    ListView listViewphone;
    PhoneAdapter phoneAdapter;
    ArrayList<Product> arrayphone;
    int idphone = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Mapping();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdTypeproduct();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(), "Пожалуйста, проверьте интернет");
            finish();
        }
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

    private void LoadMoreData() {
        listViewphone.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsProduct.class);
                intent.putExtra("descriptionproduct", arrayphone.get(i));
                startActivity(intent);
            }
        }));
        listViewphone.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (TotalItem != 0 && FirstItem + VisibleItem == TotalItem && isLoading == false && limitdata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String path = Server.Pathphone + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Namephone = "";
                int Pricephone = 0;
                String Imagephone = "";
                String Descriptionphone = "";
                int Idproductphone = 0;
                if (response != null && response.length() != 2){
                    listViewphone.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Namephone = jsonObject.getString("nameproduct");
                            Pricephone = jsonObject.getInt("priceproduct");
                            Imagephone = jsonObject.getString("imageproduct");
                            Descriptionphone = jsonObject.getString("descriptionproduct");
                            Idproductphone = jsonObject.getInt("idproduct");
                            arrayphone.add(new Product(id, Namephone, Pricephone, Imagephone,Descriptionphone, Idproductphone));
                            phoneAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata = true;
                    listViewphone.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Больше нету продуктов");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idproduct", String.valueOf(idphone));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarPhone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPhone.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdTypeproduct() {
        idphone = getIntent().getIntExtra("idtypeproduct", -1);
        Log.d("valuetypeproduct", idphone + "");
    }

    private void Mapping() {
        toolbarPhone = (Toolbar) findViewById(R.id.toolbarphone);
        listViewphone = (ListView) findViewById(R.id.listviewphone);
        arrayphone = new ArrayList<>();
        phoneAdapter = new PhoneAdapter(getApplicationContext(), arrayphone);
        listViewphone.setAdapter(phoneAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage (Message msg){
            switch (msg.what){
                case 0:
                    listViewphone.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}