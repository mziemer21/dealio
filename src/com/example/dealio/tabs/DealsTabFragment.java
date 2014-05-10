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
 * It is a list of deals
 * @author zieme_000
 *
 */
public class DealsTabFragment extends Fragment {

	private Button addButton;
	Bundle extras;
	// Declare Variables
    ListView dealListview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> dealAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		extras = getArguments();
		
		View rootView = inflater.inflate(R.layout.fragment_details_deals, container, false);
		
		addButton = (Button) rootView.findViewById(R.id.add_deal);
		 
		addButton.setOnClickListener(new OnClickListener() {
 
			  @Override
			  public void onClick(View arg0) {
 
				  Intent dealAddFragment = new Intent(getActivity(), DealAddActivity.class);
				// Pass data "name" followed by the position
              	dealAddFragment.putExtra("establishment_id", extras.getString("establishment_id").toString());
                dealAddFragment.putExtra("name", extras.getString("name")
                          .toString());
                dealAddFragment.putExtra("description", extras.getString("description")
                          .toString());
                dealAddFragment.putExtra("price", extras.getInt("price"));
                dealAddFragment.putExtra("rating", extras.getInt("rating"));
                dealAddFragment.putExtra("address", extras.getString("address")
                          .toString());
				  startActivity(dealAddFragment);
 
			  }
		});
		
		new RemoteDataTaskDeal().execute();
		return rootView;
	}
	
	// RemoteDataTask AsyncTask
    private class RemoteDataTaskDeal extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*// Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
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
            // Locate the class table named "establishment" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Deal").whereEqualTo("establishment", est);
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
            // Locate the listview in listview_main.xml
            dealListview = (ListView) getView().findViewById(R.id.deal_tab_listview);
            // Pass the results into an ArrayAdapter
            dealAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_item);
            // Retrieve object "name" from Parse.com database
            for (ParseObject deal : ob) {
            	dealAdapter.add((String) deal.get("title"));
            }
            // Binds the Adapter to the ListView
            dealListview.setAdapter(dealAdapter);
            // Close the progressdialog
            //mProgressDialog.dismiss();
            // Capture button clicks on ListView items
            dealListview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    // Send single item click data to SingleItemView Class
                 Intent i = new Intent(getActivity(),
                		DealsDetailsActivity.class);
                    // Pass data "name" followed by the position
                	i.putExtra("deal_id", ob.get(position).getObjectId().toString());
                    i.putExtra("deal_details", ob.get(position).getString("details").toString());
                    i.putExtra("deal_title", ob.get(position).getString("title").toString());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
        }
    }
}
