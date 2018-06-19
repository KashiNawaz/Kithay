package com.kithay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class History extends AppCompatActivity {

    public static final String DEFAULT="N/A";

    //For Battery info

    String batteryLevel="0";
    private BroadcastReceiver batteryInfo=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            batteryLevel=String.valueOf(level)+"%";
        }
    };


    EditText name,lang,lat,date,time,phone;
    Button write,read;
    DBController mydb;
    HistoryBO history;
    String currentDate,currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        name=(EditText) findViewById(R.id.etHName);
        phone=(EditText) findViewById(R.id.etHphone);
        lang=(EditText) findViewById(R.id.etHlong);
        lat=(EditText) findViewById(R.id.etHlat);
        date=(EditText) findViewById(R.id.etHDate);
        time=(EditText) findViewById(R.id.etHtime);
        write=(Button) findViewById(R.id.btnwrite);
        read=(Button) findViewById(R.id.btnread);

        Calendar calendar=Calendar.getInstance();
        currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        currentTime=format.format(calendar.getTime());

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get location through SharedPreferences

                SharedPreferences sharedPreferences=getSharedPreferences("location",Context.MODE_PRIVATE);
                String langPref=sharedPreferences.getString("langitude",DEFAULT);
                String latPref=sharedPreferences.getString("latitude",DEFAULT);
                String cityPref=sharedPreferences.getString("address",DEFAULT);

                if(langPref.equals(DEFAULT) || latPref.equals(DEFAULT)|| cityPref.equals(DEFAULT))
                {
                    Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
                }
                    mydb=new DBController(getApplicationContext());
                    history=new HistoryBO();
                    history.setName(name.getText().toString());
                    history.setPhone(phone.getText().toString());
                    history.setLang(langPref);
                    history.setLat(latPref);
                    history.setDate(currentDate);
                    history.settime(currentTime);
                    history.setBattery(batteryLevel);
                    history.setAddress(cityPref);
                    mydb.addHistory(history);



            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb=new DBController(getApplicationContext());
                history=new HistoryBO();
                history=mydb.getHistory(Integer.parseInt(name.getText().toString()));
                name.setText(history.getName());
                phone.setText(history.getPhone());
                lang.setText(history.getLang());
                lat.setText(history.getLat());
                date.setText(history.getDate());
                time.setText(history.gettime()+" Baatery "+history.getBattery()+" % " + "City:"+history.getAddress());
            }
        });
    }

}
