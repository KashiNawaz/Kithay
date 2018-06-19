package com.kithay;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class myLocation extends AppCompatActivity implements LocationListener{

    LocationManager locationManager;
    String mprovider;

    private static final String TAG = "kashif";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylocation);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location loc) {
        TextView longitude = (TextView) findViewById(R.id.textView);
        TextView latitude = (TextView) findViewById(R.id.textView1);
        TextView editLocation=(TextView) findViewById(R.id.city);



        editLocation.setText("");
        //pb.setVisibility(View.INVISIBLE);
        Toast.makeText(getBaseContext(),"Location changed : Lat: " +
                        loc.getLatitude()+ " Lng: " + loc.getLongitude(),
                Toast.LENGTH_LONG).show();
        String lang =Double.toString(loc.getLongitude());
        Log.v(TAG, lang);
        String lat =Double.toString(loc.getLatitude());
        Log.v(TAG, lat);

    /*----------to get City-Name from coordinates ------------- */
        String cityName=null,stateName=null,countryName=null;
        Geocoder gcd = new Geocoder(getBaseContext(),
                Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(), loc
                    .getLongitude(), 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            cityName=addresses.get(0).getAddressLine(0);
            stateName=addresses.get(0).getAddressLine(1);
            countryName=addresses.get(0).getAddressLine(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = "My Currrent City is: "+cityName;
        editLocation.setText(s);

        longitude.setText("Current Longitude:" + loc.getLongitude());
        latitude.setText("Current Latitude:" + loc.getLatitude());

       // Shared Preferences

        SharedPreferences sharedPreferences=getSharedPreferences("location",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("longitude",lang);
        editor.putString("latitude",lat);
        editor.putString("address",cityName);

        editor.commit();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}