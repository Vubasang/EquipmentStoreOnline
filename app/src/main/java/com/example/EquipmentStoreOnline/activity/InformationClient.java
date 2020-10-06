package com.example.EquipmentStoreOnline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.EquipmentStoreOnline.R;
import com.example.EquipmentStoreOnline.ultil.CheckConnection;
import com.example.EquipmentStoreOnline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InformationClient extends AppCompatActivity {

    EditText editnameclient, editemail, editphonenumber;
    Button btnConfirm, btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_client);
        Mapping();
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(), "Пожалуйста, проверьте соединение!");
        }
    }

    private void EventButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editnameclient.getText().toString().trim();
                final String phonenumber = editphonenumber.getText().toString().trim();
                final String email = editemail.getText().toString().trim();
                if (name.length() > 0 && phonenumber.length() > 0 && email.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.PathOrder, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String codeorder) {
                            Log.d("codeorder", codeorder);
                            if (Integer.parseInt(codeorder) > 0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                final StringRequest request = new StringRequest(Request.Method.POST, Server.PathDetailOrder, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.arrayCart.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Вы успешно заказали товар!");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Приглашаем вас продолжить покупку");
                                        }else{
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Данные вашей корзины были повреждены");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0;i < MainActivity.arrayCart.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("codeorder", codeorder);
                                                jsonObject.put("codeproduct", MainActivity.arrayCart.get(i).getIdproduct());
                                                jsonObject.put("nameproduct", MainActivity.arrayCart.get(i).getNamrproduct());
                                                jsonObject.put("priceproduct", MainActivity.arrayCart.get(i).getPriceproduct());
                                                jsonObject.put("amountproduct", MainActivity.arrayCart.get(i).getAmountproduct());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("nameclient", name);
                            hashMap.put("phonenumber", phonenumber);
                            hashMap.put("email", email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Пожалуйста, проверьте соединение!");
                }
            }
        });
    }

    private void Mapping() {
        editnameclient = (EditText) findViewById(R.id.edittextnameclient);
        editemail = (EditText) findViewById(R.id.edittextemailclient);
        editphonenumber = (EditText) findViewById(R.id.edittextphoneclient);
        btnConfirm = (Button) findViewById(R.id.buttonConfirm);
        btnReturn = (Button) findViewById(R.id.buttonreturn);
    }
}