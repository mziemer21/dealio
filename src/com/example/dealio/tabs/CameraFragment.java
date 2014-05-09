package com.example.dealio.tabs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dealio.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

public class CameraFragment extends Fragment {

		public static final String TAG = "CameraFragment";

		private Camera camera;
		private SurfaceView surfaceView;
		private ParseFile photoFile;
		private Button photoButton;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup parent,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.fragment_camera, parent, false);

			photoButton = (Button) v.findViewById(R.id.camera_photo_button);

			if (camera == null) {
				try {
					camera = Camera.open();
					photoButton.setEnabled(true);
				} catch (Exception e) {
					Log.e(TAG, "No camera with exception: " + e.getMessage());
					photoButton.setEnabled(false);
					Toast.makeText(getActivity(), "No camera detected",
							Toast.LENGTH_LONG).show();
				}
			}

			photoButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (camera == null)
						return;
					camera.takePicture(new Camera.ShutterCallback() {

						@Override
						public void onShutter() {
							// nothing to do
						}

					}, null, new Camera.PictureCallback() {

						@Override
						public void onPictureTaken(byte[] data, Camera camera) {
							saveScaledPhoto(data);
						}

					});

				}
			});

			surfaceView = (SurfaceView) v.findViewById(R.id.camera_surface_view);
			SurfaceHolder holder = surfaceView.getHolder();
			holder.addCallback(new Callback() {

				public void surfaceCreated(SurfaceHolder holder) {
					try {
						if (camera != null) {
							camera.setDisplayOrientation(90);
							camera.setPreviewDisplay(holder);
							camera.startPreview();
						}
					} catch (IOException e) {
						Log.e(TAG, "Error setting up preview", e);
					}
				}

				public void surfaceChanged(SurfaceHolder holder, int format,
						int width, int height) {
					// nothing to do here
				}

				public void surfaceDestroyed(SurfaceHolder holder) {
					// nothing here
				}

			});

			return v;
		}

		/*
		 * ParseQueryAdapter loads ParseFiles into a ParseImageView at whatever size
		 * they are saved. Since we never need a full-size image in our app, we'll
		 * save a scaled one right away.
		 */
		private void saveScaledPhoto(byte[] data) {

			// Resize photo from camera byte array
			Bitmap imageImage = BitmapFactory.decodeByteArray(data, 0, data.length);
			Bitmap imageImageScaled = Bitmap.createScaledBitmap(imageImage, 200, 200
					* imageImage.getHeight() / imageImage.getWidth(), false);

			// Override Android default landscape orientation and save portrait
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			Bitmap rotatedScaledImage = Bitmap.createBitmap(imageImageScaled, 0,
					0, imageImageScaled.getWidth(), imageImageScaled.getHeight(),
					matrix, true);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			rotatedScaledImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

			byte[] scaledData = bos.toByteArray();

			// Save the scaled image to Parse
			photoFile = new ParseFile("user_photo.jpg", scaledData);
			photoFile.saveInBackground(new SaveCallback() {

				public void done(ParseException e) {
					if (e != null) {
						Toast.makeText(getActivity(),
								"Error saving: " + e.getMessage(),
								Toast.LENGTH_LONG).show();
					} else {
						addPhotoToImageAndReturn(photoFile);
					}
				}
			});
		}

		/*
		 * Once the photo has saved successfully, we're ready to return to the
		 * NewImageFragment. When we added the CameraFragment to the back stack, we
		 * named it "NewImageFragment". Now we'll pop fragments off the back stack
		 * until we reach that Fragment.
		 */
		private void addPhotoToImageAndReturn(ParseFile photoFile) {
			((PictureAddActivity) getActivity()).getCurrentImage().setImageFile(
					photoFile);
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.popBackStack("NewImageFragment",
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}

		@Override
		public void onResume() {
			super.onResume();
			if (camera == null) {
				try {
					camera = Camera.open();
					photoButton.setEnabled(true);
				} catch (Exception e) {
					Log.i(TAG, "No camera: " + e.getMessage());
					photoButton.setEnabled(false);
					Toast.makeText(getActivity(), "No camera detected",
							Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		public void onPause() {
			if (camera != null) {
				camera.stopPreview();
				camera.release();
			}
			super.onPause();
		}
}
