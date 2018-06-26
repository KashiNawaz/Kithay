package com.kithay;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    String DEFAULT="N/A";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        SharedPreferences sharedPreferences=getSharedPreferences("location", Context.MODE_PRIVATE);
        String langPref=sharedPreferences.getString("longitude",DEFAULT);
        String latPref=sharedPreferences.getString("latitude",DEFAULT);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(langPref),Double.parseDouble(latPref));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}




































//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    LocationManager locationManager;
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    Activity#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return;
//
//        }
//        //check the network provider is enable
//        if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)){
//
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    double latitude=location.getLatitude();
//                    double longitude=location.getLongitude();
//                    //instantiate the class LatLng
//                    LatLng latLng=new LatLng(latitude, longitude);
//                    //GeoCoder obj class
//                    Geocoder geocoder=new Geocoder(getApplicationContext());
//                    try {
//                        List<Address> addressList= geocoder.getFromLocation(latitude,longitude,1);
//
//                        String str=addressList.get(0).getLocality();
//                        str+=addressList.get(0).getCountryName();
//                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//            });
//
//        }
//        else if(locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)){
//            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    double latitude=location.getLatitude();
//                    double longitude=location.getLongitude();
//                    //instantiate the class LatLng
//                    LatLng latLng=new LatLng(latitude, longitude);
//                    //GeoCoder obj class
//                    Geocoder geocoder=new Geocoder(getApplicationContext());
//                    try {
//                        List<Address> addressList= geocoder.getFromLocation(latitude,longitude,1);
//
//                        String str=addressList.get(0).getLocality();
//                        str+=addressList.get(0).getCountryName();
//                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//            });
//        }
//
//    }
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        //   LatLng sydney = new LatLng(-34, 151);
//        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sy
//    }
//}