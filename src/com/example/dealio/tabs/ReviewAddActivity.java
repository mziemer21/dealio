package com.example.dealio.tabs;

import android.app.Activity;
import android.content.Intent;
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
				EditText mEdit;
				ParseObject establishment = null;
				
				ParseObject deal = new ParseObject("Review");
				mEdit = (EditText)findViewById(R.id.review_title_input);
				deal.put("title", mEdit.getText().toString());
				mEdit = (EditText)findViewById(R.id.review_details_input);
				deal.put("details", mEdit.getText().toString());
				RatingBar rBar = (RatingBar) findViewById(R.id.review_rating_input);
				deal.put("rating", rBar.getRating());
				deal.put("helpful", 0);
				
				ParseQuery<ParseObject> queryEstablishment = ParseQuery.getQuery("Establishment");
				queryEstablishment.whereEqualTo("objectId", intent.getStringExtra("establishment_id"));
				try {
					establishment = queryEstablishment.getFirst();
					Log.d("establishment", establishment.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				deal.put("establishment", establishment);
				//spinner = (Spinner)findViewById(R.id.deal_type_switch);
				deal.put("user", ParseUser.getCurrentUser());
				try {
					deal.save();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}	
}
