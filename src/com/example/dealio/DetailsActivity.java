package com.example.dealio;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class DetailsActivity extends Activity {
	public static Context appContext;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        appContext = getApplicationContext();

       //ActionBar
        ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        ActionBar.Tab DetailsTab = actionbar.newTab().setText("Details");
        ActionBar.Tab DealsTab = actionbar.newTab().setText("Deals");
        ActionBar.Tab ReviewsTab = actionbar.newTab().setText("Reviews");
        ActionBar.Tab PicturesTab = actionbar.newTab().setText("Pictures");
        
        Fragment DetailsFragment = new Fragment();
        Fragment DealsFragment = new BFragment();
        Fragment ReviewsFragment = new Fragment();
        Fragment PicturesFragment = new BFragment();

        DetailsTab.setTabListener(new MyTabsListener(DetailsFragment));
        DealsTab.setTabListener(new MyTabsListener(DealsFragment));
        ReviewsTab.setTabListener(new MyTabsListener(ReviewsFragment));
        PicturesTab.setTabListener(new MyTabsListener(PicturesFragment));

        actionbar.addTab(PlayerTab);
        actionbar.addTab(StationsTab);
        actionbar.addTab(PlayerTab);
        actionbar.addTab(StationsTab);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
		
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
    
}



class MyTabsListener implements ActionBar.TabListener {
	public Fragment fragment;

	public MyTabsListener(Fragment fragment) {
		this.fragment = fragment;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Toast.makeText(DetailsActivity.appContext, "Reselected!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.fragment_container, fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
	}
}