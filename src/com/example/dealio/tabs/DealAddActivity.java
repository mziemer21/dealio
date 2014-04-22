package com.example.dealio.tabs;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.dealio.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class DealAddActivity extends Activity {

	private Button submitButton;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intent = getIntent();
		
		setContentView(R.layout.activity_add_deal);
		
		submitButton = (Button) this.findViewById(R.id.submitDealButton);
		
		submitButton.setOnClickListener(new OnClickListener () {
			
			@Override
			public void onClick(View arg0)
			{
				EditText mEdit;
				Spinner spinner;
				TimePicker tpResult;
				ParseObject establishment = null, deal_type = null;
				Calendar myCal = makeCalender();
				Date myDate;
				
				ParseObject deal = new ParseObject("Deal");
				mEdit   = (EditText)findViewById(R.id.edit_deal_title);
				deal.put("title", mEdit.getText().toString());
				mEdit   = (EditText)findViewById(R.id.edit_deal_details);
				deal.put("details", mEdit.getText().toString());
				mEdit   = (EditText)findViewById(R.id.edit_deal_restrictions);
				deal.put("restrictions", mEdit.getText().toString());
				tpResult = (TimePicker)findViewById(R.id.edit_deal_timePicker_start);
				myCal.set(Calendar.HOUR_OF_DAY, tpResult.getCurrentHour());
				myCal.set(Calendar.MINUTE, tpResult.getCurrentMinute());
				myDate = myCal.getTime();
				deal.put("time_start", myDate);
				tpResult = (TimePicker)findViewById(R.id.edit_deal_timePicker_stop);
				myCal = makeCalender();
				myCal.set(Calendar.HOUR_OF_DAY, tpResult.getCurrentHour());
				myCal.set(Calendar.MINUTE, tpResult.getCurrentMinute());
				myDate = myCal.getTime();
				deal.put("time_end", myDate);
				deal.put("up_votes", 0);
				deal.put("down_votes", 0);
				//dpResult = (DatePicker)findViewById(R.id.edit_deal_timePicker_start);
				deal.put("date_start", myDate);
				//dpResult = (DatePicker)findViewById(R.id.edit_deal_timePicker_stop);
				deal.put("date_end", myDate);
				spinner   = (Spinner)findViewById(R.id.deal_day_spinner);
				deal.put("day", spinner.getSelectedItem().toString());
				
				ParseQuery<ParseObject> queryEstablishment = ParseQuery.getQuery("Establishment");
				queryEstablishment.whereEqualTo("objectId", intent.getStringExtra("establishment_id"));
				try {
					establishment = queryEstablishment.getFirst();
					Log.d("establishment", establishment.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ParseQuery<ParseObject> queryDealType = ParseQuery.getQuery("deal_type");
				queryDealType.whereEqualTo("objectId", "YGWgOQjMKQ");
				try {
					deal_type = queryDealType.getFirst();
					Log.d("deal_type", deal_type.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				deal.put("establishment", establishment);
				//spinner = (Spinner)findViewById(R.id.deal_type_switch);
				deal.put("deal_type", deal_type);
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
	
	private Calendar makeCalender() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 0000);
		cal.set(Calendar.MONTH, 00);
		cal.set(Calendar.DATE, 00);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
	
}
