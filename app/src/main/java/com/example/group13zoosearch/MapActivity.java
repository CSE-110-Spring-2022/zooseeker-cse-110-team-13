package com.example.group13zoosearch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationSource.OnLocationChangedListener {
    GoogleMap gMap;
    LatLng currLoc;
    Polyline polyline = null;
    Context context = this;
    DirectionsBuilder db;
    SupportMapFragment supportMapFragment;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    FusedLocationProviderClient client;
    int red = 0, green = 0, blue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity);
        db = new DirectionsBuilder();


        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();


        }else{
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }




    }
    private void updatePosition(LatLng latLng)
    {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        //Create Marker
        Marker marker = gMap.addMarker(markerOptions);
        //Add Latlng and Marker
        if(latLngList.isEmpty())
        {
            latLngList.add(latLng);
            markerList.add(marker);
            if(polyline != null) polyline.remove();
            //Create PolylineOptions
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(latLngList).clickable(true);
            polyline = gMap.addPolyline(polylineOptions);

            polyline.setColor(Color.rgb(red,green,blue));
            return;
        }
        latLngList.set(0,latLng);
        markerList.set(0,marker);
        if(polyline != null) polyline.remove();
        //Create PolylineOptions
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(latLngList).clickable(true);
        polyline = gMap.addPolyline(polylineOptions);

        polyline.setColor(Color.rgb(red,green,blue));
    }
    private void drawPosition(LatLng latLng)
    {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        //Create Marker
        Marker marker = gMap.addMarker(markerOptions);
        //Add Latlng and Marker
        latLngList.add(latLng);
        markerList.add(marker);
        if(polyline != null) polyline.remove();
        //Create PolylineOptions
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(latLngList).clickable(true);
        polyline = gMap.addPolyline(polylineOptions);

        polyline.setColor(Color.rgb(red,green,blue));
    }
    private void getCurrentLocation(){
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                         supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                             @Override
                             public void onMapReady(@NonNull GoogleMap googleMap) {
                                 LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

                                 MarkerOptions Options = new MarkerOptions().position(latLng).title("Your position");
                                 currLoc = latLng;
                                 updatePosition(latLng);
                                 googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                                 googleMap.addMarker(Options);
                                 db.generateDirections(currLoc,context);
                                 MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                                 latLngList.add(latLng);
                                 Marker marker = gMap.addMarker(markerOptions);
                                 markerList.add(marker);
                                 //drawLines();
                                 testDraw();
                                 generateMarkers();
                             }
                         });
                }

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode  == 44)
        {
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

    public void clearPath()
    {
        if(polyline != null) polyline.remove();
        for(Marker marker: markerList) marker.remove();
        latLngList.clear();
        markerList.clear();
    }
    //Test draws line between two points
    public void testDraw()
    {
        LatLng ln1 = new LatLng(0,0);
        LatLng ln2 = new LatLng( 0.01,0.01);
        List<LatLng> lnl = new ArrayList<LatLng>();
        List<Marker> ml = new ArrayList<>();
        MarkerOptions markerOptions = new MarkerOptions().position(ln1).title("First");
        MarkerOptions markerOptions1 = new MarkerOptions().position(ln2).title("Second");
        Marker marker = gMap.addMarker(markerOptions);
        Marker marker2 = gMap.addMarker(markerOptions1);
        if(polyline != null) polyline.remove();
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(lnl).clickable(true);
        polyline = gMap.addPolyline(polylineOptions);
        polyline.setColor(Color.rgb(0,0,0));

    }
    public void drawLines()
    {
        for(String val : db.directions)
        {
            Log.d("Directions String", val);
            AnimalNode node = db.animalNodes.get(val);
            try {
                LatLng latLng = new LatLng(node.lat,node.lng);
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(node.name);
                //Create Marker
                Marker marker = gMap.addMarker(markerOptions);
                //Add Latlng and Marker
                latLngList.add(latLng);
                markerList.add(marker);
                if(polyline != null) polyline.remove();
                //Create PolylineOptions
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(latLngList).clickable(true);
                polyline = gMap.addPolyline(polylineOptions);

                polyline.setColor(Color.rgb(red,green,blue));
            } catch (Exception e) {
                System.out.println("Oops!");
            }
            Log.d("Markers",latLngList.toString());
            Log.d("LatLngList",latLngList.toString());



        }

    }
    public void generateMarkers()
    {
        for(String x: db.animalNodes.keySet())
        {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(db.animalNodes.get(x).lat,db.animalNodes.get(x).lng));
            //Create Marker
            Marker marker = gMap.addMarker(markerOptions);
        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
           @Override
            public void onMapClick(LatLng latLng)
           {
               //Create MarkerOptions
               MarkerOptions markerOptions = new MarkerOptions().position(latLng);
               //Create Marker
               Marker marker = gMap.addMarker(markerOptions);
               //Add Latlng and Marker
               latLngList.add(latLng);
               markerList.add(marker);
               if(polyline != null) polyline.remove();
               //Create PolylineOptions
               PolylineOptions polylineOptions = new PolylineOptions()
                       .addAll(latLngList).clickable(true);
               polyline = gMap.addPolyline(polylineOptions);

               polyline.setColor(Color.rgb(red,green,blue));
           }
        });

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Location cloc = location;
        LatLng cll = new LatLng(cloc.getLatitude(),cloc.getLongitude());
        db.generateDirections(cll,this);
        // use latitude and longitude given by
        // location.getLatitude(), location.getLongitude()
        // for updated location marker
        Log.d("aaaaaaaa===>", "" + cloc.getLatitude() + "\n" + cloc.getLongitude());
        // displayLocation();

        // to remove old markers
        gMap.clear();
        final LatLng loc = new LatLng(location.getLongitude(), location.getLongitude());

        Marker ham = gMap.addMarker(new MarkerOptions().position(loc).title("This is Me"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
    }
}
