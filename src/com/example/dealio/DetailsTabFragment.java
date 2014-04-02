package com.example.dealio;

import com.example.dealio.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***
 * Tab used by details fragment
 * It contains info about an establishment
 * @author zieme_000
 *
 */
public class DetailsTabFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
		
		return rootView;
	}
}
