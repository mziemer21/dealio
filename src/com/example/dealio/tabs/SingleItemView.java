package com.example.dealio.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.example.dealio.R;

public class SingleItemView extends FragmentActivity {
	String picture;
    ImageLoader imageLoader = new ImageLoader(this);
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);
 
        Intent i = getIntent();
        // Get the intent from ListViewAdapter
        picture = i.getStringExtra("picture");
 
        // Locate the ImageView in singleitemview.xml
        ImageView img = (ImageView) findViewById(R.id.pic);
 
        // Load image into the ImageView
        imageLoader.DisplayImage(picture, img);
    }
}
