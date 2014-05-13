package com.example.dealio;

import com.example.dealio.tabs.DealAddActivity;
import com.parse.ParseObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class DealActivity extends FragmentActivity{
	
	private Button searchDealButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.deal_sort);

        searchDealButton = (Button) findViewById(R.id.deal_filter_button);
		 
		searchDealButton.setOnClickListener(new OnClickListener() {
 
			  @Override
			  public void onClick(View arg0) {
				  EditText distance;
		        	Spinner day_of_week;
		        	ToggleButton food, drinks;
		        	
		        	distance  = (EditText)findViewById(R.id.deal_filter_distance_input);
		        	day_of_week = (Spinner)findViewById(R.id.deal_filter_day_of_week);
		        	food = (ToggleButton)findViewById(R.id.deal_filter_type_food);
		        	drinks = (ToggleButton)findViewById(R.id.deal_filter_type_drinks);
				  
				  Intent dealSearchActivity = new Intent(DealActivity.this, DealSearchActivity.class);
				  dealSearchActivity.putExtra("day_of_week", day_of_week.getSelectedItem().toString());
				  dealSearchActivity.putExtra("distance", distance.getText().toString());
				  dealSearchActivity.putExtra("food", food.isChecked());
				  dealSearchActivity.putExtra("drinks", drinks.isChecked());
				  startActivity(dealSearchActivity);
			  }
		});
    }

}
