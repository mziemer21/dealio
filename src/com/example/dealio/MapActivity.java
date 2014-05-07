package com.example.dealio;


import java.util.List;

import android.app.ProgressDialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MapActivity extends FragmentActivity implements LocationListener {

	private GoogleMap myMap;
	List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
    private Button markerButton;
	
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_map);
  
  // Getting reference to the SupportMapFragment of activity_main.xml
  SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

  // Getting GoogleMap object from the fragment
  myMap = fm.getMap();

  // Enabling MyLocation Layer of Google Map
  myMap.setMyLocationEnabled(true);

  // Getting LocationManager object from System Service LOCATION_SERVICE
  LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

  // Creating a criteria object to retrieve provider
  Criteria criteria = new Criteria();

  // Getting the name of the best provider
  String provider = locationManager.getBestProvider(criteria, true);

  // Getting Current Location
  Location location = locationManager.getLastKnownLocation(provider);

  if(location!=null){
	  loadMapOnUser(location);
  }
  locationManager.requestLocationUpdates(provider, 20000, 0, this);
  
//Setting a custom info window adapter for the google map
  myMap.setInfoWindowAdapter(new InfoWindowAdapter() {

      // Use default InfoWindow frame
      @Override
      public View getInfoWindow(Marker arg0) {
          return null;
      }

      // Defines the contents of the InfoWindow
      @Override
      public View getInfoContents(Marker arg0) {

          // Getting view from the layout file info_window_layout
          View v = getLayoutInflater().inflate(R.layout.map_marker_info, null);

          // Getting the position from the marker
          LatLng latLng = arg0.getPosition();

          // Getting reference to the TextView to set latitude
          TextView markerName = (TextView) v.findViewById(R.id.markerName);

          markerButton = (Button) v.findViewById(R.id.markerButton);

          // Setting the latitude
          markerName.setText(arg0.getTitle());

          // Setting the longitude
          markerButton .setOnClickListener(new OnClickListener() {
 
			  @Override
			  public void onClick(View arg0) {
				  
			  }
			  
          });

          // Returning the view containing InfoWindow contents
          return v;

      }
  });
  
//Execute RemoteDataTask AsyncTask
  new RemoteDataTask().execute();

 }
 
//RemoteDataTask AsyncTask
 private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
     @Override
     protected void onPreExecute() {
         super.onPreExecute();
         // Create a progressdialog
         mProgressDialog = new ProgressDialog(MapActivity.this);
         // Set progressdialog message
         mProgressDialog.setMessage("Loading...");
         mProgressDialog.setIndeterminate(false);
         // Show progressdialog
         mProgressDialog.show();
     }

     @Override
     protected Void doInBackground(Void... params) {
         // Locate the class table named "establishment" in Parse.com
         ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                 "Establishment");
         query.orderByDescending("_created_at");
         try {
             ob = query.find();
         } catch (Exception e) {
             Log.e("Error", e.getMessage());
             e.printStackTrace();
         }
         return null;
     }
     
     @Override
     protected void onPostExecute(Void result) {
    	 FragmentManager myFragmentManager = getSupportFragmentManager();
    	  SupportMapFragment mySupportMapFragment 
    	   = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.map);
    	  myMap = mySupportMapFragment.getMap();
    	  
    	  myMap.setMyLocationEnabled(true);
    	  
    	  myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    	  
    	  myMap.getUiSettings().setZoomControlsEnabled(true);
    	  myMap.getUiSettings().setCompassEnabled(true);
    	  myMap.getUiSettings().setMyLocationButtonEnabled(true);
    	  myMap.getUiSettings().setTiltGesturesEnabled(false);
    	  
    	  for (ParseObject establishment : ob) {
    		  ParseGeoPoint tempLoc = (ParseGeoPoint) establishment.get("location");
    		  MarkerOptions marker = new MarkerOptions().position(new LatLng(tempLoc.getLatitude(), tempLoc.getLongitude())).title((String) establishment.get("name"));
    		// adding marker
    		  myMap.addMarker(marker);
    	  }
    	  
    	  mProgressDialog.dismiss();
     }
     
     }
 
 @Override
 public void onLocationChanged(Location location) {

 }
 
 public void loadMapOnUser(Location location) {

     // Getting latitude of the current location
     double latitude = location.getLatitude();

     // Getting longitude of the current location
     double longitude = location.getLongitude();

     // Creating a LatLng object for the current location
     LatLng latLng = new LatLng(latitude, longitude);

     // Showing the current location in Google Map
     myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

     // Zoom in the Google Map
     myMap.animateCamera(CameraUpdateFactory.zoomTo(15));

 }

@Override
public void onProviderDisabled(String arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
	
}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
	
}

}