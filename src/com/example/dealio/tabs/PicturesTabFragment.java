package com.example.dealio.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.dealio.R;

/***
 * Tab used by details fragment
 * It contains a grid of pictures
 * @author zieme_000
 *
 */
public class PicturesTabFragment extends Fragment {

	
	Button addButton;
	Bundle extras;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		extras = getArguments();
		
		View rootView = inflater.inflate(R.layout.fragment_details_pictures, container, false);

		addButton = (Button) rootView.findViewById(R.id.add_picture);
		
		addButton.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View arg0) {

				  Intent pictureAddActivity = new Intent(getActivity(), PictureAddActivity.class);
				  pictureAddActivity.putExtra("establishment_id", extras.getString("establishment_id").toString());
				  
				  startActivity(pictureAddActivity);

			  }
		});
		
		return rootView;
	}
}
