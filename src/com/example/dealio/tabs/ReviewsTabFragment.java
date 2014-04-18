package com.example.dealio.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dealio.R;

/***
 * Tab used by details fragment
 * It contains a grid of pictures
 * @author zieme_000
 *
 */
public class ReviewsTabFragment extends Fragment {

	private Button button;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View rootView = inflater.inflate(R.layout.fragment_details_reviews, container, false);
		
		button = (Button) rootView.findViewById(R.id.add_review);
		 
		button.setOnClickListener(new OnClickListener() {
 
			  @Override
			  public void onClick(View arg0) {
 
			     Toast.makeText(getActivity(), "Button is clicked", Toast.LENGTH_LONG).show();
 
			  }
		});
		
		return rootView;
	}
}
