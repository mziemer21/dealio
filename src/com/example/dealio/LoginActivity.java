package com.example.dealio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.Kii.Site;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiSocialCallBack;
import com.kii.cloud.storage.social.KiiFacebookConnect;
import com.kii.cloud.storage.social.KiiSocialConnect;
import com.kii.cloud.storage.social.KiiSocialConnect.SocialNetwork;

public class LoginActivity extends Activity {

	final String KII_APPID = "ffd249d0";  //For our KiiCloud API calls
	final String KII_APPKEY = "9c6b9573e572db826cb503c5c3f4ee70";
	final String FB_APPID = "442305385916318";
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        
		Kii.initialize(KII_APPID, KII_APPKEY, Site.US); //Need to call this before making any Kii API calls

    }
    
    public void onButtonClick(View v){

        switch (v.getId()){

            case R.id.btnLogin:
            	loginWithFacebook();
            	break;
            	
            case R.id.btnSkip:
            	Intent i = new Intent(getApplicationContext(), MainActivity.class);
            	startActivity(i);
            	
        }
        
    }
    
    protected void loginWithFacebook() {
    	Activity activity = this;
    	KiiSocialConnect conn = Kii.socialConnect(SocialNetwork.FACEBOOK);
    	conn.initialize(FB_APPID, null, null);

    	// Setting the permission for fetching email addresss from Facebook.
    	Bundle options = new Bundle(); 
    	options.putStringArray(KiiFacebookConnect.FACEBOOK_PERMISSIONS, new String[] {"email"}); 

    	// Login.
    	conn.logIn(activity, options, new KiiSocialCallBack(){
    	  @Override
    	  public void onLoginCompleted(SocialNetwork network, KiiUser user, Exception exception) {
    	    if (exception == null) {

    	    } else {
    	      // Failure. handle error.
      	      Toast.makeText(getApplicationContext(), "Login FAILED", Toast.LENGTH_SHORT).show();
    	    }
    	  }
    	});
    }
    

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      Kii.socialConnect(SocialNetwork.FACEBOOK).respondAuthOnActivityResult(
        requestCode,
        resultCode,
        data);
      	if (resultCode == RESULT_OK) {
      		Intent i = new Intent(getApplicationContext(), MainActivity.class);
      		startActivity(i); //After authenticating with FB redirect to MainActivity
      	}
    }

}
