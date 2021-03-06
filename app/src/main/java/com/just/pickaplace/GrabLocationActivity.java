package com.just.pickaplace;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;



import java.util.concurrent.TimeUnit;

public class GrabLocationActivity extends AppCompatActivity {

    double longitude;
    double latitude;
    Button nextButton;
    String location;
    private FusedLocationProviderClient mFusedLocationClient;
    private int callbackResult = 1;
    boolean permission = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_location);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, callbackResult);
        int x = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        System.out.println("" + callbackResult);
        Log.i("Call Back Result: ", "" + callbackResult);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if(mFusedLocationClient == null) {
            System.out.println("\nfused location client is null\n");
            longitude = 0.0;
            latitude = 0.0;
        } else {
            Log.i("Hello!!!", "hello!!");
            mFusedLocationClient.getLastLocation()
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("failure");
                            Log.i("Inside Failure Handler", "Hello");
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            System.out.println("On Complelte");
                            Log.i("Inside On Complete", "hello");
                        }
                    })
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {

                        public void onSuccess(Location location) {
                            if (location == null) {
                                // Logic to handle location object
                                System.out.println("cant find userlocation");
                                Log.i("Inside Null Handler", "Hello");
                            } else {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.i("Inside Long Lat setter", "Hello");
                                System.out.println("Lat: " + latitude + "\nLong: " + longitude);
                                Log.i("Long and Lat", "" + longitude + " " + latitude);
                                Intent intent = new Intent( GrabLocationActivity.this, GetWalkingDistance.class);

                                Bundle extras = new Bundle();
                                extras.putString("latitude", "" + latitude);
                                extras.putString("longitude", "" + longitude);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        }
                    });
        }
        LocationRequest locationRequest = LocationRequest.create().setInterval(1)
                .setFastestInterval(1)
                .setExpirationDuration(TimeUnit.SECONDS.toMillis(1000))
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        client.requestLocationUpdates(locationRequest, new LocationCallback(){
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                System.out.println("GOT LOCATION RESULT");
//                Toast.makeText(GrabLocationActivity.this, "Got Location", Toast.LENGTH_LONG).show();
//                Log.i("location result", "-------------------");
//                super.onLocationResult(locationResult);
//
//                Intent intent = new Intent( GrabLocationActivity.this, GetWalkingDistance.class);
//
//                Bundle extras = new Bundle();
//                extras.putString("latitude", "" + latitude);
//                extras.putString("longitude", "" + longitude);
//                extras.putString("location", location);
//                intent.putExtras(extras);
//                startActivity(intent);
//            }
//        }, null);

    }
}
