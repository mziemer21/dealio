package com.example.activities;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.dealio.R;
import com.example.fragments.DealsTabFragment;
import com.example.fragments.DetailsTabFragment;
import com.example.fragments.PicturesTabFragment;
import com.example.fragments.ReviewsTabFragment;
import com.example.navigation.NavDrawer;

/****
 * This shows the details of a establishment
 * It has 4 tabs: main, deals, reviews, pictures
 * @author zieme_000
 *
 */
public class DetailsActivity extends NavDrawer implements
		ActionBar.TabListener {
	
	private ViewPager viewPager;
	private AppSectionsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs;
	// Used to pass args to tab fragments
	private static Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        		
		setContentView(R.layout.activity_details);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
		tabs = getResources().getStringArray(R.array.detailsTabs);
		viewPager.setAdapter(mAdapter);
		viewPager.setOffscreenPageLimit(2);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		
		
		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
		}
		
		// Add activity's intent to class var
		intent = getIntent();

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

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
	
	/* Overridden so back button from tabs activity works correctly */
	@Override
	public void onBackPressed() {
		if(getSupportFragmentManager().getBackStackEntryCount() == 1)
		{
			viewPager.setVisibility(0);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		}
			super.onBackPressed();
	}
	
	/**
     * A  FragmentPagerAdapter that returns a fragment corresponding to one of the tabs
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

    	//Declare Variables
    	String establishment_id, mobile_url, rating, name, yelp_id, address, city, state, zip, display_phone, phone, distance;
    	
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /* Used to pass arguments to the tab fragments */
        @Override
        public Fragment getItem(int i) {
        	
        	// Get the arguments from intent
            establishment_id = intent.getStringExtra("establishment_id");
            name = intent.getStringExtra("name");
            rating = intent.getStringExtra("rating");
            address = intent.getStringExtra("address");
            city = intent.getStringExtra("city");
            state = intent.getStringExtra("state");
            zip = intent.getStringExtra("zip");
            phone = intent.getStringExtra("phone");
            display_phone = intent.getStringExtra("display_phone");
            mobile_url = intent.getStringExtra("mobile_url");
            distance = intent.getStringExtra("distance");
            yelp_id = intent.getStringExtra("yelp_id");
            
            // Create a bundle, assign it arguments
            Bundle bundle = new Bundle();
            bundle.putString("establishment_id", establishment_id);
            bundle.putString("yelp_id", yelp_id);
            bundle.putString("name", name);
            bundle.putString("rating", rating);
            bundle.putString("address", address);
            bundle.putString("city", city);
            bundle.putString("state", state);
            bundle.putString("zip", zip);
            bundle.putString("phone", phone);
            bundle.putString("display_phone", display_phone);
            bundle.putString("distance", distance);
            bundle.putString("mobile_url", mobile_url);
            bundle.putString("yelp_id", yelp_id);
        	
            switch (i) {
                case 0:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragmentDetails = new DetailsTabFragment();                     
             
                    //add the bundle to the fragment
                    fragmentDetails.setArguments(bundle);
                    return fragmentDetails;

                case 1:
                	Fragment fragmentDeals = new DealsTabFragment(); 
                	
                	//add the bundle to the fragment
                    fragmentDeals.setArguments(bundle);
                	return fragmentDeals;
                	
                case 2:
                	Fragment fragmentReviews = new ReviewsTabFragment();
                	
                	//add the bundle to the fragment
                    fragmentReviews.setArguments(bundle);
                	return fragmentReviews;
                	
                case 3:
                	Fragment fragmentPictures = new PicturesTabFragment();
                	
                	//add the bundle to the fragment
                    fragmentPictures.setArguments(bundle);
                	return fragmentPictures;
                	
                default:
                	break;
                    
			}
            return null;
        }

        // Number of tabs to load
        @Override
        public int getCount() {
            return 2;
        }
    }


}
