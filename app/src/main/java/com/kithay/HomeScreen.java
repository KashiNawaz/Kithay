package com.kithay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = HomeScreen.this;
    private CardView locationCard,contactsCard,historyCard,dummy,mapCard;
    DBController mydb;
    HistoryBO historyBO;
    String DEFAULT="N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        locationCard=(CardView) (findViewById(R.id.cardlocation));
        contactsCard=(CardView) (findViewById(R.id.cardaddcontact));
        historyCard=(CardView) (findViewById(R.id.cardhistory));
        dummy=(CardView) (findViewById(R.id.carddummyhistory));
        mapCard=(CardView) (findViewById(R.id.cardfrnd));


        locationCard.setOnClickListener(this);
        contactsCard.setOnClickListener(this);
        historyCard.setOnClickListener(this);
        dummy.setOnClickListener(this);
        mapCard.setOnClickListener(this);
        mydb=new DBController(activity);
        historyBO=new HistoryBO();

    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.cardaddcontact: intent=new Intent(getApplicationContext(),Contacts.class);
            startActivity(intent);
            break;
            case R.id.cardhistory: intent=new Intent(getApplicationContext(),Friend_History.class);
            startActivity(intent);
            break;
            case R.id.cardlocation: intent=new Intent(getApplicationContext(),myLocation.class);
            startActivity(intent);
            break;
            case R.id.carddummyhistory: intent=new Intent(getApplicationContext(),History.class);
            startActivity(intent);
            break;
            case R.id.cardfrnd:
                SharedPreferences sharedPreferences=getSharedPreferences("location", Context.MODE_PRIVATE);
                String langPref=sharedPreferences.getString("longitude",DEFAULT);
                String latPref=sharedPreferences.getString("latitude",DEFAULT);

                String coord="geo:"+latPref+","+langPref;

                Uri gmmIntentUri = Uri.parse(coord);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                break;
            default: break;

        }
    }

}

