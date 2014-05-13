package com.example.dealio;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dealio.tabs.DealsDetailsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class DealSearchActivity extends FragmentActivity implements LocationListener,
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{
	// Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
    private Location currentLocation = null;
    Intent intent;

    // Stores the current instantiation of the location client in this object
    private LocationClient locationClient;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.deal_listview);
        
        intent = getIntent();
        locationClient = new LocationClient(this, this, this);
        
        
        // Execute RemoteDataTask AsyncTask
        
    }
    
 // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DealSearchActivity.this);
            // Set progressdialog message
            mProgressDialog.setMessage("Searching...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... params) {
        	String distance, day_of_week;
        	Boolean food, drinks;
        	ParseObject deal_type = null;
        	
        	currentLocation = getLocation();
        	distance  = intent.getStringExtra("distance");
        	day_of_week = intent.getStringExtra("day_of_week");
        	food = intent.getBooleanExtra("food", true);
        	drinks = intent.getBooleanExtra("drinks", true);
        	
            // Locate the class table named "establishment" in Parse.com
            ParseQuery<ParseObject> queryDealSearch = new ParseQuery<ParseObject>("Deal");
            if(day_of_week != null)
            {
            	queryDealSearch.whereContains("day", day_of_week);
            }
            if(distance != null)
            {
            	queryDealSearch.whereWithinMiles("location", geoPointFromLocation(currentLocation), Double.parseDouble(distance));
            }
            if(food == false)
            {
            	ParseQuery<ParseObject> queryDealType = ParseQuery.getQuery("deal_type");
				queryDealType.whereEqualTo("name", "Drinks");
				try {
					deal_type = queryDealType.getFirst();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				queryDealSearch.whereEqualTo("deal_type", deal_type);
            } 
            else if(drinks == false) {
            	ParseQuery<ParseObject> queryDealType = ParseQuery.getQuery("deal_type");
				queryDealType.whereEqualTo("name", "Drinks");
				try {
					deal_type = queryDealType.getFirst();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				queryDealSearch.whereEqualTo("deal_type", deal_type);
            }
            try {
                ob = queryDealSearch.find();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.deal_listview);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(DealSearchActivity.this,
                    R.layout.listview_item_deal);
            // Retrieve object "name" from Parse.com database
            for (ParseObject establishment : ob) {
                adapter.add((String) establishment.get("title"));
            }
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Capture button clicks on ListView items
            listview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    // Send single item click data to SingleItemView Class
                Intent i = new Intent(DealSearchActivity.this,
                		DealsDetailsActivity.class);
                    // Pass data "name" followed by the position
                	ParseObject establishment = (ParseObject) ob.get(position).get("establishment");
                	i.putExtra("deal_id", ob.get(position).getObjectId().toString());
                    i.putExtra("deal_title", ob.get(position).getString("title")
                            .toString());
                    i.putExtra("deal_details", ob.get(position).getString("details")
                            .toString());
                    i.putExtra("deal_restrictions", ob.get(position).getInt("description"));
                    i.putExtra("establishment_id", establishment.getObjectId());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
        }
    }

@Override
public void onLocationChanged(Location arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onProviderDisabled(String arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onProviderEnabled(String arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	// TODO Auto-generated method stub
	
}

private ParseGeoPoint geoPointFromLocation(Location loc) {
    return new ParseGeoPoint(loc.getLatitude(), loc.getLongitude());
  }

private Location getLocation() {
      return locationClient.getLastLocation();
  }

@Override
public void onConnectionFailed(ConnectionResult result) {
	// TODO Auto-generated method stub
	
}

@Override
public void onConnected(Bundle connectionHint) {
	// TODO Auto-generated method stub
	new RemoteDataTask().execute();
	
}

@Override
public void onDisconnected() {
	// TODO Auto-generated method stub
	
}
@Override
public void onStop() {
  // After disconnect() is called, the client is considered "dead".
  locationClient.disconnect();

  super.onStop();
}

/*
 * Called when the Activity is restarted, even before it becomes visible.
 */
@Override
public void onStart() {
  super.onStart();

  // Connect to the location services client
  locationClient.connect();
}
}
