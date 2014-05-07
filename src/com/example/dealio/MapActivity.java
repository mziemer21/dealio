package com.example.dealio;


import android.os.Bundle;

import com.example.dealio.navDrawer.NavDrawerTabs;

public class MapActivity extends NavDrawerTabs {

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_map);
 }

}