package com.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.view.WindowManager;

import com.example.dealio.Image;
import com.example.dealio.R;
import com.example.fragments.NewImageFragment;

public class PictureAddActivity extends FragmentActivity {

	private Image image;
	Intent intent;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	intent = getIntent();
        image = new Image();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
 
        // Begin with main data entry view,
        // NewImageFragment
        setContentView(R.layout.activity_add_picture);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        
        String establishment_id = intent.getStringExtra("establishment_id");
        
        Bundle bundle = new Bundle();
        bundle.putString("establishment_id", establishment_id);
        
        if (fragment == null) {
            fragment = new NewImageFragment();
            fragment.setArguments(bundle);
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
 
    public Image getCurrentImage() {
        return image;
    }
	
}
