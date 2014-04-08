package com.example.dealio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kii.cloud.storage.KiiUser;

/****
 * This is a fragment launched from the nav drawer
 * It allows the user to logout
 * @author zieme_000
 *
 */
public class LogoutFragment extends Fragment implements OnClickListener {
	
	KiiUser user;
    private ProgressDialog pDialog;
    
	public LogoutFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_logout, container, false);
        Button b = (Button) rootView.findViewById(R.id.btnLogout);
        b.setOnClickListener(this);
        return rootView;
    }
	
	@Override
	public void onStart() {
		super.onStart();
        user = KiiUser.getCurrentUser();
        if (user != null) {
        	TextView view = (TextView) getView().findViewById(R.id.lblDisplayName);
        	view.setText(user.getDisplayname());
        }
	}
	
    
    class PerformLogout extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Logging Out...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Performing login
         * @return 
         * */
        protected String doInBackground(String... args) {
        	if (KiiUser.isLoggedIn()) {
        		KiiUser.logOut();
        	}
        	return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        	TextView view = (TextView) getView().findViewById(R.id.lblDisplayName);
        	view.setText("");
        	
        	Intent i = new Intent(getActivity(),LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

    }

	@Override
	public void onClick(View v) {

		switch (v.getId()){

		case R.id.btnLogout:
			PerformLogout pl = new PerformLogout();
			pl.execute();
			break;

		}		
	}
}
