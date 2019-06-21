package com.example.kasratools;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ItemDetails extends AppCompatActivity implements View.OnClickListener {


    TextView textViewNama, textViewNIM, textViewKamar, textViewLorong;
    Button buttonUpdateItem;
    String Nama, NIM, Kamar, Lorong;
    private Spinner spinner_absensi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_details);

        spinner_absensi = (Spinner) findViewById(R.id.spinner_absen);

        buttonUpdateItem = (Button) findViewById(R.id.bt_check);
        buttonUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ItemDetails.this, "Selected " + spinner_absensi.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        buttonUpdateItem.setOnClickListener(this);

        Intent intent = getIntent();
        Nama = intent.getStringExtra("Nama");
        NIM = intent.getStringExtra("NIM");
        Kamar = intent.getStringExtra("kamar");
        Lorong = intent.getStringExtra("lorong");

        textViewNama = (TextView) findViewById(R.id.details_nama);
        textViewNIM = (TextView) findViewById(R.id.details_nim);
        textViewKamar = (TextView) findViewById(R.id.details_kamar);
        textViewLorong = (TextView) findViewById(R.id.details_lorong);

        textViewNama.setText(Nama);
        textViewNIM.setText(NIM);
        textViewKamar.setText(Kamar);
        textViewLorong.setText(Lorong);
    }

    private void   addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
        final String absen = spinner_absensi.getSelectedItem().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbz1jsHx8m3f57a_3YWHOWxS4C4RIjjs1125W26EzWyb73knpD4q/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(ItemDetails.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),AbsenPerson.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("Nama",Nama);
                parmas.put("NIM",NIM);
                parmas.put("Kamar",Kamar);
                parmas.put("Lorong",Lorong);
                parmas.put("Keterangan",absen);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }

    @Override
    public void onClick(View v) {
        if (v == buttonUpdateItem) {
            addItemToSheet();
        }
    }
}