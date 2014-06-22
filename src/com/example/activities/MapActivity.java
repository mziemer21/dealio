package com.example.activities;


import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.dealio.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
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
    Map<Marker, ParseObject> theMap = new HashMap<Marker, ParseObject>();
    Button redoMapButton, filterMapButton;
    Integer day;
    String weekday;
	
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_map);
  
	redoMapButton = (Button) findViewById(R.id.redo_map_button);
	  
	  redoMapButton.setOnClickListener(new OnClickListener() {
		  
		  @Override
		  public void onClick(View arg0) {
			  new RemoteDataTask().execute();
				
			  }
	});
	  
	filterMapButton = (Button) findViewById(R.id.filter_map_button);
		  
		  filterMapButton.setOnClickListener(new OnClickListener() {
			  
			  @Override
			  public void onClick(View arg0) {
				  Toast.makeText(getApplicationContext(), "filter", Toast.LENGTH_SHORT).show();
					
				  }
		});
		  
		  /*Calendar calendar = Calendar.getInstance();
		  day = calendar.get(Calendar.DAY_OF_WEEK);
		  if(day == 1)
		  {
			  weekday = "Sunday";
		  } else if(day == 2)
		  {
			  weekday = "Monday";
		  } else if(day == 3)
		  {
			  weekday = "Tuesday";
		  } else if(day == 4)
		  {
			  weekday = "Wednesday";
		  } else if(day == 5)
		  {
			  weekday = "Thursday";
		  } else if(day == 6)
		  {
			  weekday = "Friday";
		  } else if(day == 7)
		  {
			  weekday = "Saturday";
		  }*/
  
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

          // Getting reference to the TextView to set latitude
          TextView markerName = (TextView) v.findViewById(R.id.markerName);
          
          markerName.setText(arg0.getTitle());

          myMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
              @Override
              public void onInfoWindowClick(Marker marker) {
            	  Intent i = new Intent(MapActivity.this,
                  		DetailsActivity.class);
                      // Pass data "name" followed by the position
            	  ParseObject establishment = theMap.get(marker);

                  	i.putExtra("establishment_id", establishment.getObjectId().toString());
                      i.putExtra("name", establishment.getString("name")
                              .toString());
                      i.putExtra("description", establishment.getString("description")
                              .toString());
                      i.putExtra("price", establishment.getInt("price"));
                      i.putExtra("rating", establishment.getInt("rating"));
                      i.putExtra("address", establishment.getString("address")
                              .toString());
                      // Open SingleItemView.java Activity
                      startActivity(i);

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
         //query.whereContains("day", weekday);
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
    	  
    	  myMap.getUiSettings().setZoomControlsEnabled(false);
    	  myMap.getUiSettings().setCompassEnabled(true);
    	  myMap.getUiSettings().setMyLocationButtonEnabled(true);
    	  myMap.getUiSettings().setTiltGesturesEnabled(false);
    	  
    	  for (ParseObject establishment : ob) {
    		  ParseGeoPoint tempLoc = (ParseGeoPoint) establishment.get("location");
    		
    		  Marker marker = myMap.addMarker(new MarkerOptions().position(new LatLng(tempLoc.getLatitude(), tempLoc.getLongitude())).title((String) establishment.get("name")));
    		  
    		  theMap.put(marker, establishment);
    		  // adding marker
    		  
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