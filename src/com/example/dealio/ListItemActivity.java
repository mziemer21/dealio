package com.example.dealio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
 
public class ListItemActivity extends Activity {
    // Declare Variables
    TextView txtName, txtDescription, txtPrice, txtRating, txtAddress;
    String name, description, rating, price, address;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.single_list_item);
 
        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();
 
        // Get the name
        name = i.getStringExtra("name");
        description = i.getStringExtra("description");
        rating = Integer.toString(i.getIntExtra("rating", 0));
        price = Integer.toString(i.getIntExtra("price", 0));
        address = i.getStringExtra("address");
 
        // Locate the TextView in singleitemview.xml
        txtName = (TextView) findViewById(R.id.name);
        txtDescription = (TextView) findViewById(R.id.description);
        txtPrice = (TextView) findViewById(R.id.price);
        txtRating = (TextView) findViewById(R.id.rating);
        txtAddress = (TextView) findViewById(R.id.address);
 
        // Load the text into the TextView
        txtName.setText(name);
        txtDescription.setText(description);
        txtPrice.setText(price);
        txtRating.setText(rating);
        txtAddress.setText(address);
 
    }
}
