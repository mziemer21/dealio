package com.example.dealio.tabs;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.dealio.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;





/***
 * Tab used by details fragment
 * It contains a grid of pictures
 * @author zieme_000
 *
 */
public class PicturesTabFragment extends Fragment {

	// Declare Variables
    GridView gridview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    GridViewAdapter adapter;
    private List<ImageList> picarraylist = null;
	Button addButton;
	Bundle extras;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		extras = getArguments();
		
		View rootView = inflater.inflate(R.layout.fragment_details_pictures, container, false);

		addButton = (Button) rootView.findViewById(R.id.add_picture);
		
		addButton.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View arg0) {

				  Intent pictureAddActivity = new Intent(getActivity(), PictureAddActivity.class);
				  pictureAddActivity.putExtra("establishment_id", extras.getString("establishment_id").toString());
				  
				  startActivity(pictureAddActivity);

			  }
		});
		new RemoteDataTaskPicture().execute();
		return rootView;
	}
	
	// RemoteDataTask AsyncTask
    private class RemoteDataTaskPicture extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*// Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Parse.com GridView Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();*/
        }
 
        @Override
        protected Void doInBackground(Void... params) {
        	ParseObject est = null;
        	ParseQuery<ParseObject> queryEstablishment = ParseQuery.getQuery("Establishment");
			queryEstablishment.whereEqualTo("objectId", extras.getString("establishment_id"));
			try {
				est = queryEstablishment.getFirst();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            // Create the array
            picarraylist = new ArrayList<ImageList>();
            try {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Image").whereEqualTo("establishment", est);
                // Locate the column named "position" in Parse.com and order list
                // by ascending
                query.orderByAscending("position");
                ob = query.find();
                for (ParseObject pic : ob) {
                    ParseFile image = (ParseFile) pic.get("image");
                    ImageList map = new ImageList();
                    map.setPicture(image.getUrl());
                    picarraylist.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            // Locate the gridview in gridview_main.xml
            gridview = (GridView) getActivity().findViewById(R.id.picture_grid_view);
            // Pass the results into ListViewAdapter.java
            adapter = new GridViewAdapter(getActivity(), picarraylist);
            // Binds the Adapter to the ListView
            gridview.setAdapter(adapter);
            // Close the progressdialog
            //mProgressDialog.dismiss();
        }
    }
}
