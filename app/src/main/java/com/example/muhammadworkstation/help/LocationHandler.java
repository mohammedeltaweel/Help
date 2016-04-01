package com.example.muhammadworkstation.help;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muhammad Workstation on 22/03/2016.
 */
public class LocationHandler  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleApiClient mGoogleApiClient;
    Location lastLocation;
    Context mContext;
    Firebase mRef;
    LocationRequest locationRequest;
    Activity compat;
    Firebase helpChild;
    static Location location;

    public LocationHandler(Context context,Activity compat) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        createLocationRequest();

        mContext = context;
        this.compat=compat;




    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(compat, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 1);
        }


        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        startLocationRequest();
        mRef=new Firebase("https://muhammadhelp.firebaseio.com/");
        uploadLocation();


    }


    private void startLocationRequest() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public void locationConnect(){
        mGoogleApiClient.connect();
    }

    public void locationDisconnect(){
        mGoogleApiClient.disconnect();
    }

    private void uploadLocation() {

        mRef=new Firebase("https://muhammadhelp.firebaseio.com/");
        Log.i("location", "uploadLocation called");
        Map<String ,Object> map =new HashMap<>();
        /*map.put("User name",ref.getAuth().*//*getProviderData().get("email")*//*getUid());*/
        /*Log.i("userId",ref.getAuth().getUid());*/
        map.put("longitude",String.valueOf(getLong()));
        map.put("latitude",String.valueOf(getLat()));



        /*helpChild.push().setValue(map);*/





    }


    public double getLat(){
        if (lastLocation!=null){
            return lastLocation.getLatitude();
        }else {
            return 0;
        }
    }

    public double getLong(){
        if (lastLocation!=null){
            return lastLocation.getLongitude();
        }else {
            return 0;
        }
    }



    @Override
    public void onLocationChanged(Location location) {

        Map<String,Object> map =new HashMap<>();
        String  url="user_"+mRef.getAuth().getUid();
/*        map.put("User name",ref.getAuth().getProviderData().get("email"));*/
        map.put("Longitude",String.valueOf(location.getLongitude()));
        map.put("Latitude",String.valueOf(location.getLatitude()));
        mRef.child("users").child(url).child("Location").updateChildren(map);
        Log.d("MY_LOCATION", "location changed and lat is " + location.getLatitude());

        this.location=location;
    }
}
