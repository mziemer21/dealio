package com.example.dealio.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dealio.R;

/***
 * Tab used by details fragment
 * It contains info about an establishment
 * @author zieme_000
 *
 */
public class DetailsTabFragment extends Fragment {

	//Declare Variables
	String name, description, address;
	Integer rating, price;
	TextView txtName, txtDescription, txtPrice, txtRating, txtAddress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_details, container, false);
		
		/* get arguments from activity */
		Bundle bundle = getArguments(); 
		name = bundle.getString("name");
		description = bundle.getString("description");
		rating = bundle.getInt("rating");
		price = bundle.getInt("price");
		address = bundle.getString("address");
		
		// Locate the TextView in xml
        txtName = (TextView) rootView.findViewById(R.id.name);
        txtDescription = (TextView) rootView.findViewById(R.id.description);
        txtPrice = (TextView) rootView.findViewById(R.id.price);
        txtRating = (TextView) rootView.findViewById(R.id.rating);
        txtAddress = (TextView) rootView.findViewById(R.id.address);
 
        // Load the text into the TextView
        txtName.setText(name);
        txtDescription.setText(description);
        txtPrice.setText(Integer.toString(price));
        txtRating.setText(Integer.toString(rating));
        txtAddress.setText(address);
		
		return rootView;
	}
}
