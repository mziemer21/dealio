package com.example.dealio.tabs;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.dealio.R;
import com.example.dealio.navDrawer.NavDrawerTabs;

/****
 * This shows the details of a establishment
 * It has 4 tabs: main, deals, reviews, pictures
 * @author zieme_000
 *
 */
public class DetailsActivity extends NavDrawerTabs implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.activity_details);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		tabs = getResources().getStringArray(R.array.detailsTabs);

		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	
	@Override
	public void onBackPressed() {
		if(getSupportFragmentManager().getBackStackEntryCount() == 1)
		{
			
			viewPager.setVisibility(0);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		}
			super.onBackPressed();
	}

}
