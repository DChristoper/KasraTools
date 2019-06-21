package com.example.kasratools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView Copyright;
    Button buttonAddItem, buttonListItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonListItem = (Button)findViewById(R.id.btn_list_item);
        Copyright = (TextView)findViewById(R.id.copyright);
        buttonAddItem.setOnClickListener(this);
        buttonListItem.setOnClickListener(this);
        Copyright.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){

            Intent intent = new Intent(getApplicationContext(),Listitem.class);
            startActivity(intent);
        }
        if(v==buttonListItem){

            Intent intent = new Intent(getApplicationContext(),AbsenPerson.class);
            startActivity(intent);
        }
        if (v==Copyright) {

            Intent intent = new Intent(getApplicationContext(),Copyright.class);
            startActivity(intent);
        }

    }
}
