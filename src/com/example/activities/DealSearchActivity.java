package com.example.activities;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.example.dealio.R;

public class DealSearchActivity extends FragmentActivity{

	private Button searchDealButton;
	private ToggleButton oneMi, threeMi, fiveMi, tenMi, twentyMi; 
	String distance = "3";
	Calendar calendar = Calendar.getInstance();
	int today = calendar.get(Calendar.DAY_OF_WEEK), search_type;
	Spinner day_of_week, search_type_spinner;
	ToggleButton food, drinks;
	EditText query;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.deal_sort);

        searchDealButton = (Button) findViewById(R.id.deal_filter_button);
		oneMi = (ToggleButton) findViewById(R.id.deal_filter_one_mile);
        threeMi = (ToggleButton) findViewById(R.id.deal_filter_three_miles);
        fiveMi = (ToggleButton) findViewById(R.id.deal_filter_five_miles);
        tenMi = (ToggleButton) findViewById(R.id.deal_filter_ten_miles);
        twentyMi = (ToggleButton) findViewById(R.id.deal_filter_twenty_miles);    	
    	search_type_spinner = (Spinner)findViewById(R.id.deal_filter_search_type);
    	day_of_week = (Spinner)findViewById(R.id.deal_filter_day_of_week);
    	food = (ToggleButton)findViewById(R.id.deal_filter_type_food);
    	drinks = (ToggleButton)findViewById(R.id.deal_filter_type_drinks);
    	query = (EditText)findViewById(R.id.deal_filter_keyword);
		
		setDate(today);
        
        threeMi.setChecked(true);

		searchDealButton.setOnClickListener(new OnClickListener() {
 
			  @Override
			  public void onClick(View arg0) {

		        	if(search_type_spinner.getSelectedItem().toString() == "Best Matched"){
					  search_type = 0;
				  } else if(search_type_spinner.getSelectedItem().toString() == "Distance"){
					  search_type = 1;
				  } else if(search_type_spinner.getSelectedItem().toString() == "Highest Rated"){
					  search_type = 2;
				  }

					Intent dealSearchActivity = new Intent(DealSearchActivity.this, DealActivity.class);
					dealSearchActivity.putExtra("day_of_week", day_of_week.getSelectedItem().toString());
					dealSearchActivity.putExtra("distance", distance);
					dealSearchActivity.putExtra("food", food.isChecked());
					dealSearchActivity.putExtra("drinks", drinks.isChecked());
					dealSearchActivity.putExtra("query", query.getText().toString());
					startActivity(dealSearchActivity);
				  }
		});
    oneMi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(oneMi.isChecked()){
					threeMi.setChecked(false);
					fiveMi.setChecked(false);
					tenMi.setChecked(false);
					twentyMi.setChecked(false);

					distance = "1";
				} else {
				distance = "3";
				}
			}
		});

		threeMi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(threeMi.isChecked()){
					oneMi.setChecked(false);
					fiveMi.setChecked(false);
					tenMi.setChecked(false);
					twentyMi.setChecked(false);
				}
				distance = "3";
			}
		});

		fiveMi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(fiveMi.isChecked()){
					threeMi.setChecked(false);
					oneMi.setChecked(false);
					tenMi.setChecked(false);
					twentyMi.setChecked(false);

					distance = "5";
				} else {
				distance = "3";
				}
			}
		});

		tenMi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(tenMi.isChecked()){
					threeMi.setChecked(false);
					fiveMi.setChecked(false);
					oneMi.setChecked(false);
					twentyMi.setChecked(false);

					distance = "10";
				} else {
				distance = "3";
				}
			}
		});

		twentyMi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(twentyMi.isChecked()){
					threeMi.setChecked(false);
					fiveMi.setChecked(false);
					tenMi.setChecked(false);
					oneMi.setChecked(false);

					distance = "20";
				} else {
				distance = "3";
				}
			}
		});
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_clear, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
    
    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.filter_clear:
        	threeMi.setChecked(true);
        	oneMi.setChecked(false);
			fiveMi.setChecked(false);
			tenMi.setChecked(false);
			twentyMi.setChecked(false);
        	setDate(today);
        	query.setText("");
        	food.setChecked(false);
        	drinks.setChecked(false);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void setDate(Integer day){
    	if(today == 1){
        	day_of_week.setSelection(0);
        } else if(today == 2){
        	day_of_week.setSelection(1);
        } else if(today == 3){
        	day_of_week.setSelection(2);
        } else if(today == 4){
        	day_of_week.setSelection(3);
        } else if(today == 5){
        	day_of_week.setSelection(4);
        } else if(today == 6){
        	day_of_week.setSelection(5);
        } else if(today == 7){
        	day_of_week.setSelection(6);
        }
    }
}