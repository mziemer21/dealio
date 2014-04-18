package com.example.dealio.tabs;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

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
    	String name, description, address;
    	Integer rating, price;
    	
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /* Used to pass arguments to the tab fragments */
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragmentDetails = new DetailsTabFragment();                     
             
                    // Get the arguments from intent
                    name = intent.getStringExtra("name");
                    description = intent.getStringExtra("description");
                    rating = intent.getIntExtra("rating", 0);
                    price = intent.getIntExtra("price", 0);
                    address = intent.getStringExtra("address");
                    
                    // Create a bundle, assign it arguments
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("description", description);
                    bundle.putInt("rating", rating);
                    bundle.putInt("price", price);
                    bundle.putString("address", address);
                    
                    //add the bundle to the fragment
                    fragmentDetails.setArguments(bundle);
                    return fragmentDetails;

                case 1:
                	Fragment fragmentDeals = new DealsTabFragment();  
                	return fragmentDeals;
                	
                case 2:
                	Fragment fragmentReviews = new ReviewsTabFragment();
                	return fragmentReviews;
                	
                case 3:
                	Fragment fragmentPictures = new PicturesTabFragment();
                	return fragmentPictures;
                	
                default:
                	break;
                    
			}
            return null;
        }

        // Number of tabs to load
        @Override
        public int getCount() {
            return 4;
        }
    }


}
