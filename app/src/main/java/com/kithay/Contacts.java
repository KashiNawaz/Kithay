package com.kithay;

import android.Manifest;
import android.content.ClipData;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {
    ArrayList<ContactBO> listContacts;
    ListView lvContacts;

    DBController mydb;
    private static final int REQUEST_PERMISSIONS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        if (ContextCompat.checkSelfPermission(Contacts.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (Contacts.this, Manifest.permission.READ_CONTACTS)) {
                Snackbar.make(findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(Contacts.this,
                                        new String[]{Manifest.permission.READ_CONTACTS},
                                        REQUEST_PERMISSIONS);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(Contacts.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_PERMISSIONS);
            }
        } else {
            //Call whatever you want

            mydb=new DBController(getApplicationContext());
            listContacts = new ContactFetcher(this).fetchAll();
            lvContacts = (ListView) findViewById(R.id.lvContacts);
            ContactsAdapter adapterContacts = new ContactsAdapter(this, listContacts);
            lvContacts.setAdapter(adapterContacts);

            lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ContactBO  itemValue    = (ContactBO) lvContacts.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),
                            "Position :"+position+"  ListItem : " +itemValue.getName()+" "+itemValue.getPhone() , Toast.LENGTH_LONG)
                            .show();
                    mydb.addContact(itemValue);

                }
            });
        }


    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
