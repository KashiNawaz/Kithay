package com.kithay;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class History extends AppCompatActivity {

    String latPref,langPref,cityPref;
    public static final String DEFAULT="N/A";

    CheckInternet checkInternet;

    //For Battery info

    String batteryLevel="0";
    private BroadcastReceiver batteryInfo=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            batteryLevel=String.valueOf(level)+"%";
        }
    };

    private static final int REQUEST_SMS = 0;
    EditText name,lang,lat,date,time,phone;
    Button write,read,check,sms;
    DBController mydb;
    HistoryBO history;
    String currentDate,currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        name = (EditText) findViewById(R.id.etHName);
        phone = (EditText) findViewById(R.id.etHphone);
        lang = (EditText) findViewById(R.id.etHlong);
        lat = (EditText) findViewById(R.id.etHlat);
        date = (EditText) findViewById(R.id.etHDate);
        time = (EditText) findViewById(R.id.etHtime);
        write = (Button) findViewById(R.id.btnwrite);
        read = (Button) findViewById(R.id.btnread);
        check = (Button) findViewById(R.id.btnchkInternet);
        sms = (Button) findViewById(R.id.sendsms);

        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        currentTime = format.format(calendar.getTime());

        //Register Battery Receiver
        this.registerReceiver(batteryInfo, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get location through SharedPreferences

                SharedPreferences sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
                 langPref = sharedPreferences.getString("longitude", DEFAULT);
                 latPref = sharedPreferences.getString("latitude", DEFAULT);
                 cityPref = sharedPreferences.getString("address", DEFAULT);

                //Toast.makeText(getApplicationContext(),"Lat:"+latPref+" long:"+langPref+" Address: "+cityPref,Toast.LENGTH_LONG);


                if (langPref.equals(DEFAULT) || latPref.equals(DEFAULT)) {
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
                }
                mydb = new DBController(getApplicationContext());
                history = new HistoryBO();
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
                mydb = new DBController(getApplicationContext());
                history = new HistoryBO();
                history = mydb.getHistory(Integer.parseInt(name.getText().toString()));
                name.setText(history.getName());
                phone.setText(history.getPhone());
                lang.setText(history.getLang());
                lat.setText(history.getLat());
                date.setText(history.getDate());
                time.setText("Time: " + history.gettime() + " Battery " + history.getBattery() + "City:" + history.getAddress());
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                SharedPreferences sharedPreferences=getSharedPreferences("location", Context.MODE_PRIVATE);
//                String langPref=sharedPreferences.getString("longitude",DEFAULT);
//                String latPref=sharedPreferences.getString("latitude",DEFAULT);
//
//                String coord="geo:"+latPref+","+langPref;
//
//                Uri gmmIntentUri = Uri.parse(coord);
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
                //checkInternet.execute();

                mydb = new DBController(getApplicationContext());
                String contact = phone.getText().toString();
                PhoneStateReceiver phoneStateReceiver = new PhoneStateReceiver();
                String number = phoneStateReceiver.savedNumber;
                int state = phoneStateReceiver.savedState;
                if (mydb.contactExists(number) == true && state == 0) {
                    Toast.makeText(getApplicationContext(), number + " Exists", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), number + " Not Exists", Toast.LENGTH_LONG).show();

                }
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int hasSMSPermission = checkSelfPermission(Manifest.permission.SEND_SMS);
                    if (hasSMSPermission != PackageManager.PERMISSION_GRANTED) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                            showMessageOKCancel("You need to allow access to Send SMS",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                                        REQUEST_SMS);
                                            }
                                        }
                                    });
                            return;
                        }
                        requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                REQUEST_SMS);
                        return;
                    }
            }
            mydb=new DBController(getApplicationContext());
                history=new HistoryBO();
                String contactNumber=PhoneStateReceiver.savedNumber;
                history=mydb.getLastHistory();
                String address=history.getAddress();
                sendSMS(contactNumber,address);
            }
        });
    }



    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(History.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
