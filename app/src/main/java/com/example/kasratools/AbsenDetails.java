package com.example.kasratools;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

public class AbsenDetails extends AppCompatActivity{


    TextView textViewNama, textViewNIM, textViewKamar, textViewLorong, textCall;
    String Nama, NIM, Kamar, Lorong;
    private Object myHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.absen_details);

        Intent intent = getIntent();
        Nama = intent.getStringExtra("Nama");
        NIM = intent.getStringExtra("NIM");
        Kamar = intent.getStringExtra("kamar");
        Lorong = intent.getStringExtra("lorong");

        textViewNama = (TextView) findViewById(R.id.details_namad);
        textViewNIM = (TextView) findViewById(R.id.details_nimd);
        textViewKamar = (TextView) findViewById(R.id.details_kamard);
        textViewLorong = (TextView) findViewById(R.id.details_lorongd);

        textViewNama.setText(Nama);
        textViewNIM.setText(NIM);
        textViewKamar.setText(Kamar);
        textViewLorong.setText(Lorong);

        textCall = (TextView) findViewById(R.id.textType);
        textCall.setMovementMethod(new ScrollingMovementMethod());
        textCall.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        textCall.setText("+62-853-8706-3244");





    }
}