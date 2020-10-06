package com.example.EquipmentStoreOnline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
//import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.EquipmentStoreOnline.R;
import com.example.EquipmentStoreOnline.adapter.TypeproductAdapter;
import com.example.EquipmentStoreOnline.adapter.ProductAdapter;
import com.example.EquipmentStoreOnline.model.MyCart;
import com.example.EquipmentStoreOnline.model.Product;
import com.example.EquipmentStoreOnline.model.Typeproduct;
import com.example.EquipmentStoreOnline.ultil.CheckConnection;
import com.example.EquipmentStoreOnline.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmainscreen;
    NavigationView navigationView;
    ListView listViewmainscreen;
    DrawerLayout drawerLayout;
    ArrayList<Typeproduct> arraytypeproduct;
    TypeproductAdapter typeproductAdapter;
    int id = 0;
    String nametypeproduct = "";
    String imagetypeproduct = "";
    ArrayList<Product> arrayproduct;
    ProductAdapter productAdapter;
    public static ArrayList<MyCart> arrayCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            GetDataTypeproduct();
            GetDataNewsproduct();
            CatchOnItemListView();
        }
        else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Пожалуйста, проверьте соединение");
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

    private void CatchOnItemListView() {
        listViewmainscreen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Пожалуйста, проверьте соединение");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                            intent.putExtra("idtypeproduct", arraytypeproduct.get(i).getId());
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Пожалуйста, проверьте соединение");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LapTopActivity.class);
                            intent.putExtra("idtypeproduct", arraytypeproduct.get(i).getId());
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Пожалуйста, проверьте соединение");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Пожалуйста, проверьте соединение");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Пожалуйста, проверьте соединение");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDataNewsproduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Pathnewsproduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int ID = 0;
                    String Nameproduct = "";
                    Integer Priceproduct = 0;
                    String Imageproduct = "";
                    String Descriptionproduct	 = "";
                    int IDproduct = 0;
                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Nameproduct = jsonObject.getString("nameproduct");
                            Priceproduct = jsonObject.getInt("priceproduct");
                            Imageproduct = jsonObject.getString("imageproduct");
                            Descriptionproduct = jsonObject.getString("descriptionproduct");
                            IDproduct = jsonObject.getInt("idproduct");
                            arrayproduct.add(new Product(ID, Nameproduct, Priceproduct, Imageproduct, Descriptionproduct, IDproduct));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataTypeproduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Pathtypeproduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            nametypeproduct = jsonObject.getString("nametypeproduct");
                            imagetypeproduct = jsonObject.getString("imagetypeproduct");
                            arraytypeproduct.add(new Typeproduct(id, nametypeproduct, imagetypeproduct));
                            typeproductAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arraytypeproduct.add(3, new Typeproduct(0, "Связаться с нами", "https://epay.ibt.tj/epay/img/ngn.png"));
                    arraytypeproduct.add(4, new Typeproduct(0, "О нас", "https://iconarchive.com/icons/custom-icon-design/pretty-office-2/256/personal-information-icon.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> advertisingSegment = new ArrayList<>();
        advertisingSegment.add("https://trashbox.ru/ifiles/1160629_0f2b3d_screenshot_00.png-orig/rabota.ru-android-6.jpg");
        advertisingSegment.add("https://itstart2020.ru/storage/trynews/March2020/SyEehISRuLLmM6hZHCpR.jpg");
        advertisingSegment.add("https://getmimo.com/assets/link_image_twitter.png");
        advertisingSegment.add("https://cs9.pikabu.ru/post_img/big/2017/07/13/6/1499936095124785347.jpg");
        for (int i = 0; i < advertisingSegment.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(advertisingSegment.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Mapping() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmainscreen);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewLipper);
        recyclerViewmainscreen = (RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewmainscreen = (ListView) findViewById(R.id.listviewmainscreen);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        arraytypeproduct = new ArrayList<>();
        arraytypeproduct.add(0, new Typeproduct(0, "Главная страница", "https://icons.iconarchive.com/icons/kyo-tux/phuzion/256/System-Home-icon.png"));

        typeproductAdapter = new TypeproductAdapter(arraytypeproduct, getApplicationContext());
        listViewmainscreen.setAdapter(typeproductAdapter);
        arrayproduct = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), arrayproduct);
        recyclerViewmainscreen.setHasFixedSize(true);
        recyclerViewmainscreen.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewmainscreen.setAdapter(productAdapter);
        if (arrayCart != null) {

        }
        else {
            arrayCart = new ArrayList<>();
        }
    }
}