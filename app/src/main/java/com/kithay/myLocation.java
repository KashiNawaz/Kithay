package com.kithay;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class myLocation extends AppCompatActivity implements LocationListener {

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
            locationManager.requestLocationUpdates(mprovider, 15000, 10, this);

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
        final TextView editLocation = (TextView) findViewById(R.id.city);



        editLocation.setText("");
        Toast.makeText(getBaseContext(), "Location changed : Lat: " +
                        loc.getLatitude() + " Lng: " + loc.getLongitude(),
                Toast.LENGTH_LONG).show();
        String lang = Double.toString(loc.getLongitude());
        Log.v(TAG, lang);
        String lat = Double.toString(loc.getLatitude());
        Log.v(TAG, lat);

    /*----------to get City-Name from coordinates ------------- */
        final String[] address = {null};
        String city = null;
        String state = null;
        String country = null;
        String postalCode = null;
        String knownName = null;
        String fulladdress;
        Geocoder gcd = new Geocoder(getBaseContext(),
                Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lang), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address[0] = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            if (address[0] == null) {


                PlaceDetectionClient placeDetectionClient = Places.
                        getPlaceDetectionClient(this, null);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Task<PlaceLikelihoodBufferResponse> placeResult = placeDetectionClient.
                        getCurrentPlace(null);
                placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                        Log.d(TAG, "current location places info");
                        List<Place> placesList = new ArrayList<Place>();
                        PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                            placesList.add(placeLikelihood.getPlace().freeze());
                        }
                        address[0] = (String) placesList.get(0).getAddress();
                        editLocation.setText(address[0]);
                    }
                });
            }
            city = addresses.get(0).getLocality();
             state = addresses.get(0).getAdminArea();
             country = addresses.get(0).getCountryName();
             postalCode = addresses.get(0).getPostalCode();
             knownName = addresses.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            addresses = gcd.getFromLocation(loc.getLatitude(), loc
//                    .getLongitude(), 1);
//            if (addresses.size() > 0)
//                System.out.println(addresses.get(0).getLocality());
//            address=addresses.get(0).getAddressLine(0);
//            stateName=addresses.get(0).getAddressLine(1);
//            countryName=addresses.get(0).getAddressLine(2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        fulladdress= address[0];
        String s = "Address: "+ address[0] +"\n city: "+city+"\n state: "+state+"\n country: "+country+"\n postalCode: "+postalCode+"\n KnownName: "+knownName;
        editLocation.setText(s);

        longitude.setText("Current Longitude:" + loc.getLongitude());
        latitude.setText("Current Latitude:" + loc.getLatitude());

       // Shared Preferences

        SharedPreferences sharedPreferences=getSharedPreferences("location",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("longitude",lang);
        editor.putString("latitude",lat);
        editor.putString("address",fulladdress);

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