package lk.ac.iit.humzearch;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import lk.ac.iit.humzearch.app.AppController;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SettingsProfileActivity extends ActionBarActivity {
	
	private final String TAG = SettingsProfileActivity.class.getName();
	
	private final int SELECT_PHOTO = 1;
	
	private NetworkImageView imgUser;
	private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private Button btnChange;
	private ParseUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_profile);
		initialize();
	}

	private void initialize() {
		currentUser = ParseUser.getCurrentUser();
		
		imgUser = (NetworkImageView) findViewById(R.id.imgSettingsProfile);
		imgUser.setImageUrl(currentUser.getParseFile("image").getUrl(), imageLoader);
		
		btnChange = (Button) findViewById(R.id.btnSettingsProfileChange);
		btnChange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});
	}
	
	private void selectImage(){
		Intent imgPickIntent = new Intent(Intent.ACTION_PICK);
		imgPickIntent.setType("image/*");
		startActivityForResult(imgPickIntent, SELECT_PHOTO);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
        switch(requestCode){
        case SELECT_PHOTO:
        	if(resultCode == RESULT_OK){
        		try{
        			final Uri imageUri = imageReturnedIntent.getData();
        			final InputStream imageStream = getContentResolver().openInputStream(imageUri);
        			final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        			imgUser.setImageBitmap(selectedImage);
        			uploadImage(getImageByteArray(selectedImage));
        		}catch(FileNotFoundException e){
        			Log.d(TAG, e.getMessage());
        			displayToast(e.getMessage());
        		}
        	}
        }
    }
	
	private void displayToast(String message){
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	public void uploadImage(byte[] imgByteArray){
		final ParseFile imgFile = new ParseFile("image.jpg", imgByteArray);
		imgFile.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if(e == null){
					currentUser.put("image", imgFile);
					currentUser.saveEventually();
				}else{
					Log.d(TAG, e.getMessage());
					displayToast(e.getMessage());
				}
			}
		});
		
	}
	
	public byte[] getImageByteArray(Bitmap selectedImage){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		selectedImage.compress(Bitmap.CompressFormat.JPEG, 80, stream);
		byte[] imgData = stream.toByteArray();
		return imgData;
	}

}
