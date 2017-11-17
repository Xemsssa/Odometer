package com.xemss.odometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class OdometerService extends Service {
    private final IBinder binder = new OdometerBinder();
    private double distance;
    private Location oldLocation;

    public class OdometerBinder extends Binder {
        OdometerService getOdometer() {
            return OdometerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return binder;
    }

    // TODO: 15.11.2017 create listener to get location
    @Override
    public void onCreate() {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                Location oldLocation;
                if (null == oldLocation) {
                    oldLocation = location;
                }

                distance += location.distanceTo(oldLocation);

                oldLocation = location;
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
        };

        // TODO: 15.11.2017 get user location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
                1000,
                1,
                locationListener);

//        super.onCreate();
    }

    public OdometerService() {
    }

    // TODO: 15.11.2017 method return distance
    public double getMetres() {
        return distance;
    }
}
