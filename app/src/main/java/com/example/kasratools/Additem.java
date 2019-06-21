package com.example.kasratools;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Additem extends AppCompatActivity implements View.OnClickListener {


    EditText editTextNama,editTextNIM, editTextKamar, editTextLorong;
    Button buttonAddItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_item);

        editTextNama = (EditText)findViewById(R.id.nama);
        editTextNIM = ( EditText)findViewById(R.id.nim);
        editTextKamar = (EditText) findViewById(R.id.kamar);
        editTextLorong = (EditText) findViewById(R.id.lorong);

        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);


    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void   addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Person","Please wait");
        final String nama = editTextNama.getText().toString().trim();
        final String nim = editTextNIM.getText().toString().trim();
        final String kamar = editTextKamar.getText().toString().trim();
        final String lorong = editTextLorong.getText().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbz1jsHx8m3f57a_3YWHOWxS4C4RIjjs1125W26EzWyb73knpD4q/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(Additem.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
                parmas.put("Nama",nama);
                parmas.put("NIM",nim);
                parmas.put("Kamar", kamar);
                parmas.put("Lorong", lorong);

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

        if(v==buttonAddItem){
            addItemToSheet();

            //Define what to do when button is clicked
        }
    }
}

