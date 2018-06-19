package com.kithay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    EditText name,email,password,phone;
    Button signup;
    TextView signin;

    UserBO user;
    DBController mydb;

    private String _name=null,_email=null,_phone=null,_password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=(EditText) findViewById(R.id.etName);
        email=(EditText) findViewById(R.id.etEmail_signup);
        password=(EditText) findViewById(R.id.etPassword_signup);
        phone=(EditText) findViewById(R.id.etphone);
        signup=(Button) findViewById(R.id.btnSignup);
        signin=(TextView) findViewById(R.id.txtSignin);

        mydb=new DBController(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _name=name.getText().toString();
                _email=email.getText().toString();
                _password=password.getText().toString();
                _phone=phone.getText().toString();

                if(_name==null || TextUtils.isEmpty(_name)){
                    name.setError("Name field is empty/not valid");
                    return;
                }
                if(_email==null || TextUtils.isEmpty(_email)){
                    email.setError("Email field is empty/not valid");
                    return;
                }
                if(_password==null || TextUtils.isEmpty(_password)){
                    password.setError("Password field is empty/not valid");
                    return;
                }
                if(_phone==null || TextUtils.isEmpty(_phone)){
                    phone.setError("Phone field is empty/not valid");
                    return;
                }

                UserBO user=new UserBO(_name,_email,_phone,_password);
                mydb.addUser(user);
                Toast.makeText(getApplicationContext(), "Registered Successfully ", Toast.LENGTH_SHORT).show();

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Signin.class);
                startActivity(i);
            }
        });
    }
}
