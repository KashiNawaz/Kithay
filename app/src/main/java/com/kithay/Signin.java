package com.kithay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signin extends AppCompatActivity {

    EditText email,password;
    Button signin;
    TextView signup;

    UserBO user;
    DBController mydb;

    //For Battery info
    private TextView battery;

    private BroadcastReceiver batteryInfo=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            battery.setText(String.valueOf(level)+"%");
        }
    };

    private String _email=null,_password=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email=(EditText) findViewById(R.id.etEmail_signin);
        password=(EditText) findViewById(R.id.etPassword_signin);
        signin=(Button) findViewById(R.id.btnSignin);
        signup=(TextView) findViewById(R.id.txtSignup);

        //Battery Info
        battery=(TextView) findViewById(R.id.txtbatry);
        this.registerReceiver(batteryInfo,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        mydb=new DBController(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _email=email.getText().toString();
                _password=password.getText().toString();

              if(mydb.userExists(_email,_password)){
                  Intent i=new Intent(getApplicationContext(),HomeScreen.class);
                  startActivity(i);
              }
              else {
                  Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

              }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Signup.class);
                startActivity(i);
            }
        });
    }
}
