package com.example.dealio.adapter;

import com.example.dealio.DealsTabFragment;
import com.example.dealio.ReviewsTabFragment;
import com.example.dealio.PicturesTabFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new PicturesTabFragment();
		case 1:
			// Games fragment activity
			return new DealsTabFragment();
		case 2:
			// Movies fragment activity
			return new ReviewsTabFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
