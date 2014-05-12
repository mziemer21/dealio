package com.example.dealio.tabs;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.dealio.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/***
 * Tab used by details fragment
 * It contains a grid of pictures
 * @author zieme_000
 *
 */
public class ReviewsTabFragment extends Fragment {

	private Button addReviewButton;
	Bundle extrasReview;
	ListView reviewListview;
    List<ParseObject> obReview;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> reviewAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		extrasReview = getArguments();
		
		View rootReviewView = inflater.inflate(R.layout.fragment_details_reviews, container, false);
		 
		addReviewButton = (Button) rootReviewView.findViewById(R.id.add_review);
		 
		addReviewButton.setOnClickListener(new OnClickListener() {
 
			  @Override
			  public void onClick(View arg0) {
 
				  Intent dealAddFragment = new Intent(getActivity(), ReviewAddActivity.class);
				// Pass data "name" followed by the position
              	dealAddFragment.putExtra("establishment_id", extrasReview.getString("establishment_id").toString());
                dealAddFragment.putExtra("name", extrasReview.getString("name")
                          .toString());
                dealAddFragment.putExtra("description", extrasReview.getString("description")
                          .toString());
                dealAddFragment.putExtra("price", extrasReview.getInt("price"));
                dealAddFragment.putExtra("rating", extrasReview.getInt("rating"));
                dealAddFragment.putExtra("address", extrasReview.getString("address")
                          .toString());
				  startActivity(dealAddFragment);
 
			  }
		});
		
		new RemoteDataTaskReview().execute();
		return rootReviewView;
	}
	
	// RemoteDataTask AsyncTask
    private class RemoteDataTaskReview extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
 
        @Override
        protected Void doInBackground(Void... params) {
        	ParseObject est = null;
        	ParseQuery<ParseObject> queryEstablishmentReview = ParseQuery.getQuery("Establishment");
			queryEstablishmentReview.whereEqualTo("objectId", extrasReview.getString("establishment_id"));
			try {
				est = queryEstablishmentReview.getFirst();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            // Locate the class table named "establishment" in Parse.com
            ParseQuery<ParseObject> queryReview = new ParseQuery<ParseObject>(
                    "Review").whereEqualTo("establishment", est);
            queryReview.orderByDescending("_created_at");
            try {
                obReview = queryReview.find();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            reviewListview = (ListView) getView().findViewById(R.id.review_tab_listview);
            // Pass the results into an ArrayAdapter
            reviewAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.listview_item_review);
            // Retrieve object "name" from Parse.com database
            for (ParseObject review : obReview) {
            	reviewAdapter.add((String) review.get("title"));
            }
            // Binds the Adapter to the ListView
            reviewListview.setAdapter(reviewAdapter);
            // Close the progressdialog
            //mProgressDialog.dismiss();
            // Capture button clicks on ListView items
            reviewListview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    // Send single item click data to SingleItemView Class
                 Intent iReview = new Intent(getActivity(),
                		ReviewsDetailsActivity.class);
                    // Pass data "name" followed by the position
                	iReview.putExtra("review_id", obReview.get(position).getObjectId().toString());
                    iReview.putExtra("review_details", obReview.get(position).getString("details").toString());
                    iReview.putExtra("review_title", obReview.get(position).getString("title").toString());
                    // Open SingleItemView.java Activity
                    startActivity(iReview);
                }
            });
        }
    }
}
