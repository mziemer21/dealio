package com.example.navigation;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.activities.DealActivity;
import com.example.activities.DetailsActivity;
import com.example.activities.ListActivity;
import com.example.activities.LogoutActivity;
import com.example.activities.MapActivity;
import com.example.activities.RandomActivity;
import com.example.dealio.R;
import com.example.fragments.ChangeLocationFragment;
import com.example.fragments.FeedbackFragment;
import com.example.fragments.HomeFragment;
import com.example.fragments.NotificationsFragment;

public class NavDrawer extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private String callingClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callingClass = this.getClass().getSimpleName();
		if(callingClass.equalsIgnoreCase("MainActivity"))
		{
			setContentView(R.layout.activity_main);
		}
		
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		
		
		
		if(callingClass.equalsIgnoreCase("DetailsActivity"))
		{
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_tabs);
			mDrawerList = (ListView) findViewById(R.id.list_slidermenu_tabs);
		}
		else if (callingClass.equalsIgnoreCase("MainActivity"))
		{
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		}

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// What's hot, We  will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			if(callingClass.equalsIgnoreCase("MainActivity"))
			{
				displayView(0);
			}
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new NotificationsFragment();
			break;
		case 2:
			Intent userDetailsFragment = new Intent(this, LogoutActivity.class);
	        startActivity(userDetailsFragment);
			break;
		case 3:
			fragment = new ChangeLocationFragment();
			break;
		case 4:
			fragment = new FeedbackFragment();
			break;
		case 5:
			Intent detailsFragment = new Intent(this, DetailsActivity.class);
	        startActivity(detailsFragment);
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			
			if(callingClass.equalsIgnoreCase("DetailsActivity"))
			{
				ActionBar actionBar = getActionBar();
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
				ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
				if(viewPager.getVisibility() == 0)
				{
					viewPager.setVisibility(8);
				}
				fragmentManager.beginTransaction()
					.replace(R.id.frame_container_tabs, fragment).addToBackStack(fragment.getTag()).commit();
			}
			else if (callingClass.equalsIgnoreCase("MainActivity"))
			{
				fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).addToBackStack(fragment.getTag()).commit();
			}

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	/*** For home fragment buttons ***/
	public void onButtonClick(View v)
	{
		
		switch(v.getId())
		{
		case R.id.buttonList:
			Intent ListActivity = new Intent(this, ListActivity.class);
	        startActivity(ListActivity);
			break;
			
		case R.id.buttonDeals:
			Intent DealActivity = new Intent(this, DealActivity.class);
			startActivity(DealActivity);
			break;
			
		case R.id.buttonMap:
			Intent MapActivity = new Intent(this, MapActivity.class);
			startActivity(MapActivity);
			break;
		
		case R.id.buttonRandom:
			Intent RandomActivity = new Intent(this, RandomActivity.class);
			startActivity(RandomActivity);
			break;
			
			default:
			
		}
	}

}
