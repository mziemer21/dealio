package com.example.dealio.tabs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.dealio.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ReviewAddActivity extends Activity {

	private Button submitButton;
	Intent intent;
	ProgressDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intent = getIntent();
		
		setContentView(R.layout.activity_add_review);
		
		submitButton = (Button) this.findViewById(R.id.review_submit_button);
		
		submitButton.setOnClickListener(new OnClickListener () {
			
			@Override
			public void onClick(View arg0)
			{
				// Create a progressdialog
	            mProgressDialog = new ProgressDialog(ReviewAddActivity.this);
	            // Set progressdialog message
	            mProgressDialog.setMessage("Saving...");
	            mProgressDialog.setIndeterminate(false);
	            // Show progressdialog
	            mProgressDialog.show();
				
	            new AsyncTask<Void, Void, Void>()
	            {
	            	@Override
	            	protected Void doInBackground(Void... params)
	            	{
						EditText mEdit;
						ParseObject establishment = null;
						
						ParseObject review = new ParseObject("Review");
						mEdit = (EditText)findViewById(R.id.review_title_input);
						review.put("title", mEdit.getText().toString());
						mEdit = (EditText)findViewById(R.id.review_details_input);
						review.put("details", mEdit.getText().toString());
						RatingBar rBar = (RatingBar) findViewById(R.id.review_rating_input);
						review.put("rating", rBar.getRating());
						review.put("helpful", 0);
						
						ParseQuery<ParseObject> queryEstablishment = ParseQuery.getQuery("Establishment");
						queryEstablishment.whereEqualTo("objectId", intent.getStringExtra("establishment_id"));
						try {
							establishment = queryEstablishment.getFirst();
							Log.d("establishment", establishment.toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						review.put("establishment", establishment);
						//spinner = (Spinner)findViewById(R.id.deal_type_switch);
						review.put("user", ParseUser.getCurrentUser());
						try {
							review.save();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
	            	}
	            	
	            	@Override
		            protected void onPostExecute(Void result)
		            {
						mProgressDialog.dismiss();
						ReviewAddActivity.this.finish();
		            }
	            }.execute();
			}
		});
	}	
}
