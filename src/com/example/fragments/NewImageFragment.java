package com.example.fragments;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activities.PictureAddActivity;
import com.example.dealio.Image;
import com.example.dealio.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewImageFragment extends Fragment {
	private Button photoButton,saveButton, cancelButton;
	private TextView imageTitle, imageTags;
	private ParseImageView imagePreview;
	Bundle extras;
	private String imagePath;
	private ParseFile photoFile;
	static final int CAMERA_RESULT = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle SavedInstanceState) {
		
		extras = getArguments();
		
		View v = inflater.inflate(R.layout.fragment_new_image, parent, false);

		imageTitle = ((EditText) v.findViewById(R.id.image_title_input));

		imageTags = ((EditText) v.findViewById(R.id.image_tags_input));

		photoButton = ((Button) v.findViewById(R.id.photo_button));
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(imageTitle.getWindowToken(), 0);
				startCamera();
			}
		});

		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Image image = ((PictureAddActivity) getActivity()).getCurrentImage();

				// When the user clicks "Save," upload the image to Parse
				// Add data to the image object:
				image.setTitle(imageTitle.getText().toString());
				
				image.setTags(imageTags.getText().toString());
				
				ParseQuery<ParseObject> queryEstablishment = ParseQuery.getQuery("Establishment");
				queryEstablishment.whereEqualTo("objectId", extras.getString("establishment_id"));
				try {
					image.setEstablishment(queryEstablishment.getFirst());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Associate the image with the current user
				image.setUser(ParseUser.getCurrentUser());

				// If the user added a photo, that data will be
				// added in the CameraFragment

				// Save the image and return
				image.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							getActivity().setResult(Activity.RESULT_OK);
							getActivity().finish();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}

				});

			}
		});

		cancelButton = ((Button) v.findViewById(R.id.cancel_button));
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().setResult(Activity.RESULT_CANCELED);
				getActivity().finish();
			}
		});

		// Until the user has taken a photo, hide the preview
		imagePreview = (ParseImageView) v.findViewById(R.id.image_preview_image);
		imagePreview.setVisibility(View.INVISIBLE);

		return v;
	}

	/*
	 * All data entry about a image object is managed from the NewImageActivity.
	 * When the user wants to add a photo, we'll start up a custom
	 * CameraFragment that will let them take the photo and save it to the image
	 * object owned by the NewimageActivity. Create a new CameraFragment, swap
	 * the contents of the fragmentContainer (see activity_new_image.xml), then
	 * add the NewimageFragment to the back stack so we can return to it when the
	 * camera is finished.
	 */
	public void startCamera() {
		
		Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "bar_images");
        imagesFolder.mkdirs();

        File image = new File(imagesFolder, "bar_image" + timeStamp + ".png");
        Uri uriSavedImage = Uri.fromFile(image);

        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        imagePath = image.toString();
        startActivityForResult(imageIntent, CAMERA_RESULT);
        
        
        
        
		/*Fragment cameraFragment = new CameraFragment();
		FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragmentContainer, cameraFragment);
		transaction.addToBackStack("NewImageFragment");
		transaction.commit();*/
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT) {
        	
        }
        
        
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, null); //BitmapFactory.decodeResource(getResources(),R.drawable.androidbegin);
        Bitmap imageImageScaled = Bitmap.createScaledBitmap(bitmap, 900, 900
				* bitmap.getHeight() / bitmap.getWidth(), false);
        
        // Override Android default landscape orientation and save portrait
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap rotatedScaledImage = Bitmap.createBitmap(imageImageScaled, 0,
				0, imageImageScaled.getWidth(), imageImageScaled.getHeight(),
				matrix, true);
        
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        rotatedScaledImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByte = stream.toByteArray();

        // Create the ParseFile
        photoFile = new ParseFile("androidbegin.png", imageByte);
        
        photoFile.saveInBackground(new SaveCallback() {

			public void done(ParseException e) {
				if (e != null) {
					Toast.makeText(getActivity(),
							"Error saving: " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getActivity(), "Image saved, should preview", Toast.LENGTH_LONG).show();
					imagePreview = (ParseImageView) getActivity().findViewById(R.id.image_preview_image);
					imagePreview.setVisibility(View.VISIBLE);
					((PictureAddActivity) getActivity()).getCurrentImage().setImageFile(photoFile);
					
				}
			}
		}); 
         
         
    }

	/*
	 * On resume, check and see if a image photo has been set from the
	 * CameraFragment. If it has, load the image in this fragment and make the
	 * preview image visible.
	 */
	@Override
	public void onResume() {
		super.onResume();
		ParseFile photoFile = ((PictureAddActivity) getActivity())
				.getCurrentImage().getImageFile();
		if (photoFile != null) {
			imagePreview.setParseFile(photoFile);
			imagePreview.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					imagePreview.setVisibility(View.VISIBLE);
				}
			});
		}
	}
}
