package com.example.dealio;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dealio.tabs.DealsDetailsActivity;
import com.example.dealio.tabs.DetailsActivity;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class RandomActivity extends Activity {
    // Declare Variables
    List<ParseObject> ob;
    Integer obCount, position;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
    }
	// RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(RandomActivity.this);
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... params) {
        	// Locate the class table named "establishment" in Parse.com
            ParseQuery<ParseObject> queryCount = new ParseQuery<ParseObject>(
                    "Deal");
            try {
                obCount = queryCount.count();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        	
        	// Locate the class table named "establishment" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Deal");
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
        	Random random = new Random();
        	position = random.nextInt(obCount+1);
        	Intent i = new Intent(RandomActivity.this,
            		DealsDetailsActivity.class);
                // Pass data "name" followed by the position
            	i.putExtra("deal_id", ob.get(position).getObjectId().toString());
                i.putExtra("deal_details", ob.get(position).getString("details").toString());
                i.putExtra("deal_title", ob.get(position).getString("title").toString());
                // Open SingleItemView.java Activity
                startActivity(i);
                RandomActivity.this.finish();
        }
    }
}
