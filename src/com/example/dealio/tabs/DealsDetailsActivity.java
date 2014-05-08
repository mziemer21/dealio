package com.example.dealio.tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dealio.R;

public class DealsDetailsActivity extends Activity{
	//Declare Variables
	String deal_id, deal_title, deal_details;
	Integer rating, price;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intent = getIntent();
		
		// Get the arguments from intent
        deal_id = intent.getStringExtra("deal_id");
        deal_title = intent.getStringExtra("deal_title");
        deal_details = intent.getStringExtra("deal_details");
        
        setContentView(R.layout.activity_deal_details);
		
        TextView title = (TextView) findViewById(R.id.dealTitle);
        title.setText(deal_title);
        
		TextView details = (TextView) findViewById(R.id.dealDetail);
		details.setText(deal_details);
		
		
		
	}
}
