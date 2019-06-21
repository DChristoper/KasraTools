package com.example.kasratools;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Listitem extends AppCompatActivity implements AdapterView.OnItemClickListener{


    ListView listView;
    SimpleAdapter adapter;
    ProgressDialog loading;
    EditText editTextSearchItem;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);


        listView = (ListView) findViewById(R.id.lv_items);

        listView.setOnItemClickListener(this);

        editTextSearchItem = (EditText) findViewById(R.id.et_search);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==fab){

                    Intent intent = new Intent(getApplicationContext(),Additem.class);
                    startActivity(intent);
                }

            }
        });

        getItems();

    }

    private void getItems() {

        loading = ProgressDialog.show(this, "Loading", "please wait", false, true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbz1jsHx8m3f57a_3YWHOWxS4C4RIjjs1125W26EzWyb73knpD4q/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String nama = jo.getString("nama");
                String nim = jo.getString("nim");
                String kamar = jo.getString("kamar");
                String lorong = jo.getString("lorong");


                HashMap<String, String> item = new HashMap<>();
                item.put("Nama", nama);
                item.put("NIM", nim);
                item.put("Kamar", kamar);
                item.put("Lorong", lorong);


                list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SimpleAdapter(this, list, R.layout.list_item_row,
                new String[]{"Nama", "NIM","Kamar", "Lorong"}, new int[]{R.id.absen_name,R.id.nim_no, R.id.kamar_no, R.id.lorong_no});

        listView.setAdapter(adapter);
        loading.dismiss();

        editTextSearchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Listitem.this.adapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, AbsenDetails.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String Nama = map.get("Nama").toString();
        String NIM = map.get("NIM").toString();
        String Kamar = map.get("Kamar").toString();
        String Lorong = map.get("Lorong").toString();


        // String sno = map.get("sno").toString();

        // Log.e("SNO test",sno);
        intent.putExtra("Nama",Nama);
        intent.putExtra("NIM",NIM);
        intent.putExtra("kamar",Kamar);
        intent.putExtra("lorong",Lorong);

        startActivity(intent);

    }

}