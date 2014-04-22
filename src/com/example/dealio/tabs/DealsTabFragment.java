package com.example.dealio.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dealio.R;

/***
 * Tab used by details fragment
 * It is a list of deals
 * @author zieme_000
 *
 */
public class DealsTabFragment extends Fragment {

	private Button addButton;
	Bundle extras;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		extras = getArguments();
		
		View rootView = inflater.inflate(R.layout.fragment_details_deals, container, false);
		
		addButton = (Button) rootView.findViewById(R.id.add_deal);
		 
		addButton.setOnClickListener(new OnClickListener() {
 
			  @Override
			  public void onClick(View arg0) {
 
				  Intent dealAddFragment = new Intent(getActivity(), DealAddActivity.class);
				// Pass data "name" followed by the position
              	dealAddFragment.putExtra("establishment_id", extras.getString("establishment_id").toString());
                dealAddFragment.putExtra("name", extras.getString("name")
                          .toString());
                dealAddFragment.putExtra("description", extras.getString("description")
                          .toString());
                dealAddFragment.putExtra("price", extras.getInt("price"));
                dealAddFragment.putExtra("rating", extras.getInt("rating"));
                dealAddFragment.putExtra("address", extras.getString("address")
                          .toString());
				  startActivity(dealAddFragment);
 
			  }
		});
		
		return rootView;
	}
}
