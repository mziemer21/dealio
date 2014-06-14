package com.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dealio.R;

/***
 * This is a fragment launched from the nav drawer
 * It allows the user to select what notifications they receive
 * @author zieme_000
 *
 */
public class NotificationsFragment extends Fragment {
	
	public NotificationsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
         
        return rootView;
    }
}
