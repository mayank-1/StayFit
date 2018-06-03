package com.stayfit.app.stayfit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000; //Radius given 10 KM . Result will be shown within this range.
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //Permission is granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else //Permission Denied
                {
                    Toast.makeText(this, "Permission Denied!",Toast.LENGTH_LONG).show();
                }
                return;
        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Called whenever the map is ready to be used.
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiClient(); //Calling the method
            mMap.setMyLocationEnabled(true);

        }

    }

    public void onClick (View view) {

        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        switch(view.getId()) {
            case R.id.B_search: {
                mMap.clear();
                EditText tf_location = (EditText) findViewById(R.id.TF_location);
                String location = tf_location.getText().toString();
                List<Address> addressList = null; //This will store the list of locations the user had searched for.
                MarkerOptions mo = new MarkerOptions();

                //Check if the location Field is Blank or not
                if(!location.equals("")){

                    //Not Blank then we will create the object of Geocoder and use its methods to search the locations
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        //Location search results are store here.
                        addressList = geocoder.getFromLocationName(location, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0;i<addressList.size();i++) {

                        Address myAddress = addressList.get(i);
                        LatLng latLng = new LatLng(myAddress.getLatitude(),myAddress.getLongitude());
                        mo.position(latLng);
                        mo.title("Your Search Result");
                        mMap.addMarker(mo);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }

            }
            break;
            case R.id.B_hospital:
                mMap.clear();
                String hospital = "hospital";
                String url = getUrl(latitude, longitude, hospital);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_LONG).show();
                break;
            case R.id.B_restaurants:
                mMap.clear();
                String restaurant = "restaurant";
                url = getUrl(latitude, longitude, restaurant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Restaurant", Toast.LENGTH_LONG).show();
                break;
            case R.id.B_school:
                mMap.clear();
                String school = "school";
                url = getUrl(latitude, longitude, school);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Schools", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        // NOTE :- We are use Google Places API Web Service to Search for Nearby Places.

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS); //Passing the Radius within which you want to get the result Maximim 50000 meters.
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=AIzaSyBZT3eozazxIRFPLV5lPxmx9lR69R5-6Do"); //Create a different key

        return googlePlaceUrl.toString(); //Because it is an object of StringBuilder so we have to convert it toString.
    }

    protected synchronized void buildGoogleApiClient() {
        //we have created the Google API Client
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //now just connect the client to the Google API
        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        //LocationListner Class is used

        lastLocation = location;

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        if(currentLocationMarker != null) {
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude()); //First argurement for Latitude and Second For Longitude

        //MarkerOptions are used to set the properties of the marker
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng); //Marker Postion on Map
        markerOptions.title("Current Location"); //Marker Title
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)); //Also set the icon color to BLUE.

        currentLocationMarker = mMap.addMarker(markerOptions); //Now this currentLocationMarker will have the values of the marker we just set in obove lines.

        //Now we want the camera to move and show us the current location on button click.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if (client != null) {
            //If equals null then there is no location set currently

            LocationServices.FusedLocationApi.removeLocationUpdates(client, (com.google.android.gms.location.LocationListener) this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //GoogleApiClient.ConnectionCallbacks is used
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest, (com.google.android.gms.location.LocationListener) this); //
        }
    }

    //Check Location Permission
    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //If the permission is not granted it will come over here.

            //We have to ask for the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            }
            return false;
        }
        else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public interface OnFragmentInteractionListener {
    }
}
