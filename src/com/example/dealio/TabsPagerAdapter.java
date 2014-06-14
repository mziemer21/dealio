package com.example.dealio;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fragments.DealsTabFragment;
import com.example.fragments.DetailsTabFragment;
import com.example.fragments.PicturesTabFragment;
import com.example.fragments.ReviewsTabFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Main fragment activity
			return new DetailsTabFragment();
		case 1:
			// Deals fragment activity
			return new DealsTabFragment();
		case 2:
			// Reviews fragment activity
			return new ReviewsTabFragment();
		case 3:
			// Pictures fragment activity
			return new PicturesTabFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
