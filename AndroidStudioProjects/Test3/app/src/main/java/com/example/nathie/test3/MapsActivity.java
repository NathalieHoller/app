package com.example.nathie.test3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnPoiClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseHelper myDb;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        myDb = new DatabaseHelper(this);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            myDb.AddData();
            SharedPreferences.Editor editor = wmbPreference.edit();     // Insert data into table ONCE
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawMakers();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getLocationFromAddress(this, "Regensburg")));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0f));
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnPoiClickListener(this);

    }
    //asks the user for permissions
    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
                break;
        }
    }
    @Override
    public void onMyLocationClick(@NonNull Location location){
        Toast.makeText(this, "Current Location:\n" + location, Toast.LENGTH_LONG).show();

    }
    @Override
    public boolean onMyLocationButtonClick(){
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override
    public void onPoiClick(PointOfInterest poi) {
        Toast.makeText(getApplicationContext(), "Clicked: " +
                        poi.name + "\nPlace ID:" + poi.placeId +
                        "\nLatitude:" + poi.latLng.latitude +
                        " Longitude:" + poi.latLng.longitude,
                Toast.LENGTH_SHORT).show();
    }
    public LatLng getLocationFromAddress(Context context, String strAddress){
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try{
            address = coder.getFromLocationName(strAddress, 5);
            if(address == null){
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return p1;
    }
    public List getAllWochenmaerkte() {
        List WochenmarktListe = new ArrayList();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " ORDER BY " + DatabaseHelper.COL_1 + " ASC";
        SQLiteDatabase db = myDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Wochenmarkt woma = new Wochenmarkt();
                woma.set_id(cursor.getInt(0));
                woma.setTyp(cursor.getString(1));
                woma.setStadt(cursor.getString(2));
                woma.setAdresse(cursor.getString(3));
                woma.setÖffnungszeiten(cursor.getString(4));
                woma.setKontakt(cursor.getString(5));
                woma.setAngebot(cursor.getString(6)
                );
                WochenmarktListe.add(woma);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return WochenmarktListe;
    }
    public void drawMakers(){
        List<Wochenmarkt> WochenMarktListe = getAllWochenmaerkte();
        for (Wochenmarkt woma : WochenMarktListe){
            String womaId = ""+woma.get_id();
            String womaTyp = ""+woma.getTyp();
            String womaStadt = ""+woma.getStadt();
            String womaAdresse = ""+woma.getAdresse();
            String womaÖffnungszeiten = ""+woma.getÖffnungszeiten();
            String womaKontakt = ""+woma.getKontakt();
            String womaAngebot = ""+woma.getAngebot();
            // Log.d("Mytag", "\n"+womaId+", "+womaTyp+", "+womaStadt+", "+womaAdresse+", "+womaÖffnungszeiten+", "+womaKontakt+", "+womaAngebot+"\n");
            mMap.addMarker(new MarkerOptions().position(getLocationFromAddress(this, ""+womaAdresse))
                    .title(""+womaTyp)
                    .snippet(""+womaAdresse));
        }
    }
}

