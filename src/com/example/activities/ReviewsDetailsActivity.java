package com.example.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dealio.R;

public class ReviewsDetailsActivity extends Activity{
	//Declare Variables
	String review_id, review_title, review_details;
	Integer rating, price;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intent = getIntent();
		
		// Get the arguments from intent
        review_id = intent.getStringExtra("review_id");
        review_title = intent.getStringExtra("review_title");
        review_details = intent.getStringExtra("review_details");
        
        setContentView(R.layout.activity_review_details);
		
        TextView title = (TextView) findViewById(R.id.reviewTitle);
        title.setText(review_title);
        
		TextView details = (TextView) findViewById(R.id.reviewDetail);
		details.setText(review_details);	
	}
}
