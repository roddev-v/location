package com.example.locationdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationProvider implements LocationListener {
    private FusedLocationProviderClient fusedLocationClient;
    private LocationManager locationManager;
    public Location currentLocation;

    public Location getLocation() {
        return currentLocation;
    }

    @SuppressLint("MissingPermission")
    public void requestLocation(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                50,
                100,
                this);
        updateLocation();
    }

    private void updateLocation() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            currentLocation = null;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = new Location(location);
            } else {
                currentLocation = null;
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        updateLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
