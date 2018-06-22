package com.example.nathie.test3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class HomeFragment extends Fragment
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnPoiClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback{

    private GoogleMap mMap;
    DatabaseHelper myDb;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkPermissions();
        myDb = new DatabaseHelper(getActivity());
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            myDb.AddData();
            SharedPreferences.Editor editor = wmbPreference.edit();     // Insert data into table ONCE
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }
        //getActivity().setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawMakers();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getLocationFromAddress(getActivity(), "Regensburg")));
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
            final int result = ContextCompat.checkSelfPermission(getActivity(), permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE_ASK_PERMISSIONS);
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
                        Toast.makeText(getActivity(), "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                        return;
                    }
                }
                // all permissions were granted
                break;
        }
    }
    @Override
    public void onMyLocationClick(@NonNull Location location){
        Toast.makeText(getActivity(), "Current Location:\n" + location, Toast.LENGTH_LONG).show();

    }
    @Override
    public boolean onMyLocationButtonClick(){
        Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override
    public void onPoiClick(PointOfInterest poi) {
        Toast.makeText(getActivity().getApplicationContext(), "Clicked: " +
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
            mMap.addMarker(new MarkerOptions().position(getLocationFromAddress(getActivity(), ""+womaAdresse))
                    .title(""+womaTyp)
                    .snippet(""+womaAdresse));
        }
    }
}