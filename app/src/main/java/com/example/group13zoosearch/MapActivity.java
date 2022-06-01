package com.example.group13zoosearch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Pattern;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationSource.OnLocationChangedListener {

    TextInputEditText latEdit, longEdit;
    GoogleMap gMap;
    LatLng currLoc;
    Polyline polyline = null;
    Context context = this;
    //DirectionsBuilder db;
    SupportMapFragment supportMapFragment;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    FusedLocationProviderClient client;
    int red = 0, green = 0, blue = 0;

    //Runs Once the first time the activity is called and created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //We will intialize the values and our Directions Builder to receive our directions needed
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity);
        //db = new DirectionsBuilder();
        latEdit = (TextInputEditText)findViewById(R.id.lat_input);
        longEdit = (TextInputEditText)findViewById(R.id.long_input);
        //Our support map fragment is stored in a variable here
        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);

        //client will store the services to receive our current location
        client = LocationServices.getFusedLocationProviderClient(this);

        //We check permissions to access our current location for our activity
        //We will then run our functions to generate the routes
        if (ActivityCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                 getCurrentLocation();


        }else{
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }




    }
    @Override
    public void onResume() {

        super.onResume();
        if (ActivityCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //We will recalculate the location once we resume the activity
            clearPath();
            getCurrentLocation();


        }else{
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

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

                                 //MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                                 //latLngList.add(latLng);
                                 //Marker marker = gMap.addMarker(markerOptions);
                                 //markerList.add(marker);
                                 drawLines();
                             }
                         });
                }

            }
        });

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

    //Clears marker and paths before each calculation use when needed
    public void clearPath()
    {
        if(polyline != null) polyline.remove();
        for(Marker marker: markerList) marker.remove();
        latLngList.clear();
        markerList.clear();
    }
    public void drawLines()
    {
       ArrayList<AnimalNode> aR = AnimalRoute.animalRoute;
        clearPath();
        MarkerOptions markerOptions = new MarkerOptions()
                .position(currLoc).title("You");
        Marker marker = gMap.addMarker(markerOptions);
        latLngList.add((currLoc));
        markerList.add(marker);


        for(AnimalNode val : aR)
        {
            Log.d("Directions dg", val.toString());
            AnimalNode node = val;
            if(node.lat!= null)
            {
                Log.d("We Made it Here", node.toString());
                LatLng latLng = new LatLng(node.lat,node.lng);
                markerOptions = new MarkerOptions().position(latLng).title(node.name);
                //Create Marker
                marker = gMap.addMarker(markerOptions);
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
            Log.d("Markers",latLngList.toString());
            Log.d("LatLngList",latLngList.toString());



        }

    }
//    public void drawLines()
//    {
//        Log.d("ASERS","SPAGHETTI");
//        clearPath();
//        MarkerOptions markerOptions = new MarkerOptions()
//                .position(currLoc).title("You");
//        Marker marker = gMap.addMarker(markerOptions);
//        latLngList.add((currLoc));
//        markerList.add(marker);
//        if(db.nodes.size()>1)
//        {
//            db.nodes.remove(0);
//        }
//
//        for(String val : db.nodes)
//        {
//            Log.d("Directions String", val);
//            AnimalNode node = db.animalNodes.get(val);
//            if(node.lat!= null)
//            {
//
//                LatLng latLng = new LatLng(node.lat,node.lng);
//                markerOptions = new MarkerOptions().position(latLng).title(node.name);
//                //Create Marker
//                marker = gMap.addMarker(markerOptions);
//                //Add Latlng and Marker
//                latLngList.add(latLng);
//                markerList.add(marker);
//                if(polyline != null) polyline.remove();
//                //Create PolylineOptions
//                PolylineOptions polylineOptions = new PolylineOptions()
//                        .addAll(latLngList).clickable(true);
//                polyline = gMap.addPolyline(polylineOptions);
//
//                polyline.setColor(Color.rgb(red,green,blue));
//            }
//            Log.d("Markers",latLngList.toString());
//            Log.d("LatLngList",latLngList.toString());
//
//
//
//        }
//
//    }
    //Draws all locations on the map
//    public void generateMarkers()
//    {
//        clearPath();
//        MarkerOptions markerOptions = new MarkerOptions()
//                .position(currLoc).title("You");
//        Marker marker = gMap.addMarker(markerOptions);
//        latLngList.add((currLoc));
//        markerList.add(marker);
//        for(String x: db.animalNodes.keySet())
//        {
//            Log.d("Node String", db.animalNodes.get(x).toString());
//            if(db.animalNodes.get(x).lat!=null)
//            {
//                markerOptions = new MarkerOptions()
//                    .position(new LatLng(db.animalNodes.get(x).lat,db.animalNodes.get(x).lng))
//                        .title(db.animalNodes.get(x).name);
//                marker = gMap.addMarker(markerOptions);
//                latLngList.add(new LatLng(db.animalNodes.get(x).lat,db.animalNodes.get(x).lng));
//                markerList.add(marker);
//                if(polyline != null) polyline.remove();
//                //Create PolylineOptions
//                PolylineOptions polylineOptions = new PolylineOptions()
//                        .addAll(latLngList).clickable(true);
//                polyline = gMap.addPolyline(polylineOptions);
//
//                polyline.setColor(Color.rgb(red,green,blue));
//            }

            //Create Marker
            //
//        }
//    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
           @Override
            public void onMapClick(LatLng latLng)
           {
               //Create MarkerOptions
               currLoc = latLng;
//               db.generateDirections(latLng,context);
               drawLines();
               //db.updateLocations(latLng,context);
           }
        });

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Location cloc = location;
        LatLng cll = new LatLng(cloc.getLatitude(),cloc.getLongitude());
        //db.generateDirections(cll,this);
//        db.updateLocations(cll,this);
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
    /*Adapted from https://www.baeldung.com/java-check-string-number used regex to check if a string is numerical or not*/
    public boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    //Button Click Functions
    public void teleport(View view)
    {
        String lat = latEdit.getText().toString().replaceAll("\\s+","") ;
        String lng = longEdit.getText().toString().replaceAll("\\s+","") ;
        if(isNumeric(lat) && isNumeric(lng))
        {
            LatLng latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
            currLoc = latLng;
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//            db.generateDirections(latLng,context);
            drawLines();
            Toast.makeText(this, "Amenotejikara! Successfully Teleported, Omae Wa Mou Shindeiru ", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Invalid Inputs ", Toast.LENGTH_SHORT).show();
        }
    }

    public void calibrateBtn(View view){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markerList) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 0;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        gMap.moveCamera(cameraUpdate);
        gMap.animateCamera(cameraUpdate);

    }
    public void returnToHome(View view) {
        finish();
    }
    public void center(View view){}
    public void toDirections(View view) {
        Intent intent = new Intent(this, DirectionsActivity.class);
        startActivity(intent);
        finish();
    }
}
