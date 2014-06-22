package com.example.activities;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dealio.R;
import com.example.yelp.API_Static_Stuff;
import com.example.yelp.Yelp;
import com.example.yelp.YelpParser;
import com.parse.ParseObject;
import com.parse.ParseQuery;
 
public class ListActivity extends FragmentActivity {
    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
    String establishment_id, mobile_url, rating, name, yelp_id, address, city, state, zip, display_phone, phone, distance;
    Object loc;
    YelpParser yParser;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
    }
 
    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ListActivity.this);
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... params) {
        	
        	
        	 API_Static_Stuff api_keys = new API_Static_Stuff();
             
        	    Yelp yelp = new Yelp(api_keys.getYelpConsumerKey(), api_keys.getYelpConsumerSecret(), 
        	            api_keys.getYelpToken(), api_keys.getYelpTokenSecret());
        	    String response = yelp.search("wandos", 43.0667, -89.395971);
        	 
        	    yParser = new YelpParser();
        	    yParser.setResponse(response);
        	    try {
        	        yParser.parseBusiness();
        	    } catch (JSONException e) {
        	        // TODO Auto-generated catch block
        	        e.printStackTrace();
        	        //Do whatever you want with the error, like throw a Toast error report
        	    }
					
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(ListActivity.this,
                    R.layout.listview_item_list);
            // Retrieve object "name" from Parse.com database
            for (int i =0; yParser.getJSONSize() > i; i++) {
                try {
					adapter.add(yParser.getBusinessName(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
                
            	    try {
    					mobile_url = yParser.getBusinessMobileURL(position);
            	    } catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} try {
    	        	    rating = yParser.getBusinessRating(position);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} try {
    	        	    name = yParser.getBusinessName(position);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} try {
    	        	    yelp_id = yParser.getBusinessId(position);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} try {
    	        	    phone = yParser.getBusinessPhone(position);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}try {
    	        	    display_phone = yParser.getBusinessDisplayPhone(position);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} try {
    	        	    distance = yParser.getBusinessDistance(position);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}try {
    					loc = yParser.getBusinessLocation(position);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} try { 
    	        	    address = yParser.getBusinessAddress((JSONObject) loc);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} try { 
    	        	    city = yParser.getBusinessCity((JSONObject) loc);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} try { 
    	        	    state = yParser.getBusinessState((JSONObject) loc);
    				} catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} try { 
    	        	    zip = yParser.getBusinessZipcode((JSONObject) loc);
            	    } catch (JSONException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} 
    				
    				// Locate the class table named "establishment" in Parse.com
    	            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
    	                    "Establishment");
    	            query.whereEqualTo("yelp_id", yelp_id);
    	            try {
    	                ob = query.find();
    	            } catch (Exception e) {
    	                Log.e("Error", e.getMessage());
    	                e.printStackTrace();
    	            }
    	            
    	            if(ob.size() == 0){
    	            	establishment_id = "empty";
    	            } else {
    	            	establishment_id = ob.get(0).getObjectId().toString();
    	            }
    	           
    				
                    // Send single item click data to SingleItemView Class
                Intent i = new Intent(ListActivity.this,
                		DetailsActivity.class);
                    // Pass data "name" followed by the position
                	i.putExtra("establishment_id", establishment_id);
                	i.putExtra("yelp_id", yelp_id);
                    i.putExtra("name", name);
                    i.putExtra("rating", rating);
                    i.putExtra("address", address);
                    i.putExtra("city", city);
                    i.putExtra("state", state);
                    i.putExtra("zip", zip);
                    i.putExtra("phone", phone);
                    i.putExtra("display_phone", display_phone);
                    i.putExtra("distance", distance);
                    i.putExtra("mobile_url", mobile_url);
                    
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
        }
    }
}
