package com.kithay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private CardView locationCard,contactsCard,historyCard,dummy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        locationCard=(CardView) (findViewById(R.id.cardlocation));
        contactsCard=(CardView) (findViewById(R.id.cardaddcontact));
        historyCard=(CardView) (findViewById(R.id.cardhistory));
        dummy=(CardView) (findViewById(R.id.carddummyhistory));


        locationCard.setOnClickListener(this);
        contactsCard.setOnClickListener(this);
        historyCard.setOnClickListener(this);
        dummy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.cardaddcontact: intent=new Intent(getApplicationContext(),Contacts.class);
            startActivity(intent);
            break;
            case R.id.cardhistory: intent=new Intent(getApplicationContext(),Coordinates.class);
            startActivity(intent);
            break;
            case R.id.cardlocation: intent=new Intent(getApplicationContext(),myLocation.class);
            startActivity(intent);
            break;
            case R.id.carddummyhistory: intent=new Intent(getApplicationContext(),History.class);
            startActivity(intent);
            break;
            default: break;

        }
    }
}
