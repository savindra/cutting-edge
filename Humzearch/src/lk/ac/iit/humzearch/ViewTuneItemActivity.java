package lk.ac.iit.humzearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import lk.ac.iit.humzearch.app.AppController;
import lk.ac.iit.humzearch.model.Response;
import lk.ac.iit.humzearch.model.TuneParse;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTuneItemActivity extends ActionBarActivity {
	
	private final static String TAG = ViewTuneItemActivity.class.getSimpleName();
	
	private TextView txtName, txtDate, txtArtist, txtLanguage, txtCountry, txtYear, txtDelete;
	private ParseImageView imgUser;
	private ParseFile imgFile;
	private Button btnPlay, btnPause, btnAddRespone;
	private SeekBar seekBar;
	private MediaPlayer mMediaplayer;
	private double timeElapsed = 0;
	private boolean isPlaying = false;
	
	private ProgressDialog progressDialog;
	
	private Dialog responseDialog;
	private AlertDialog alertDialog;
	private TextView txtDialogArtist, txtDialogTitle;
	private Button btnDialogAddResponse;
	
	private TuneParse tuneParse = new TuneParse();
	private ParseQuery<TuneParse> tuneQuery;
	private String tuneObjID;
	int result;
	int responseCount;
	
	private Handler timerHandler;
	
	private Intent i;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_tune_item);
		
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setTitle("View Tune");
		
		initialize();
		downloadData();
	}
	


	private void initialize() {
		txtName = (TextView) findViewById(R.id.txtViewTuneItemName);
		txtDate = (TextView) findViewById(R.id.txtViewTuneItemDate);
		txtArtist = (TextView) findViewById(R.id.txtViewTuneItemArtist);
		txtLanguage = (TextView) findViewById(R.id.txtViewTuneItemLang);
		txtCountry = (TextView) findViewById(R.id.txtViewTuneItemCountry);
		txtYear = (TextView) findViewById(R.id.txtViewTuneItemYear);
		txtDelete = (TextView) findViewById(R.id.txtViewTuneItemDelete);
		imgUser = (ParseImageView) findViewById(R.id.imgViewTuneItemUser);
		btnPlay = (Button) findViewById(R.id.btnViewTuneItemPlay);
		btnPause = (Button) findViewById(R.id.btnViewTuneItemPause);
		btnAddRespone = (Button) findViewById(R.id.btnViewTuneResponse);
		seekBar = (SeekBar) findViewById(R.id.seekBarViewTuneItem);
		mMediaplayer = new MediaPlayer();
		timerHandler = new Handler();
		i = getIntent();
		
		txtDelete.setVisibility(View.GONE);
		txtDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				delete();
			}
		});
		
		btnAddRespone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addResponse();
				
			}
		});
		
		btnPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				play();
				
			}
		});
		
		btnPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pause();
			}
		});
		
	}
	
	// Get tune data from previous activity
	private void fillData(TuneParse tuneParse){
		
		imgFile = tuneParse.getCreatedBy().getParseFile("image");
		if(imgFile != null){
			imgUser.setParseFile(imgFile);
			imgUser.loadInBackground();
		}
		
		mMediaplayer = MediaPlayer.create(this, Uri.parse(tuneParse.getTuneFile().getUrl()));
		
		if(tuneParse.getCreatedBy().getUsername().equalsIgnoreCase(ParseUser.getCurrentUser().getUsername()))
			txtDelete.setVisibility(View.VISIBLE);
	}
	
	// Download a copy of tune object from cloud via object id
	private void downloadData() {
		progressDialog = ProgressDialog.show(this, "", "Loading...", true);
		
		tuneObjID = i.getStringExtra("tuneParseID");
		txtName.setText(i.getStringExtra("name"));
		txtDate.setText(i.getStringExtra("tune_date"));
		txtArtist.setText(testText(i.getStringExtra("tune_artist")));
		txtLanguage.setText(testText(i.getStringExtra("tune_lang")));
		txtCountry.setText(testText(i.getStringExtra("tune_country")));
		txtYear.setText(testText(i.getStringExtra("tune_year")));
		
		tuneQuery = ParseQuery.getQuery("Tune");
		tuneQuery.whereEqualTo("objectId", tuneObjID);
		tuneQuery.include("createdBy");
		tuneQuery.getFirstInBackground(new GetCallback<TuneParse>() {
			
			@Override
			public void done(TuneParse tune, ParseException e) {
				progressDialog.dismiss();
				if(e == null){
					tuneParse = tune;
					fillData(tuneParse);
				}else{
					Log.d(TAG, e.getMessage());
				}
			}
		});
		
	}

	// Mediaplayer controllers
	private void play() {
		isPlaying = true;
		seekBar.setMax((int) mMediaplayer.getDuration());
		mMediaplayer.start();
		timeElapsed = mMediaplayer.getCurrentPosition();
		seekBar.setProgress((int) timeElapsed);
		timerHandler.postDelayed(updateSeekBarTime, 100);
	}
	
	private void pause(){
		mMediaplayer.pause();
		isPlaying = false;
	}
	
	
	private Runnable updateSeekBarTime = new Runnable() {
		@Override
		public void run() {
			
			if(isPlaying){
				timeElapsed = mMediaplayer.getCurrentPosition();
				seekBar.setProgress((int) timeElapsed);
				timerHandler.postDelayed(this, 100);
			}
		}
	};	
	// Mediaplayer controllers
	
	private void addResponse() {
		
		responseDialog = new Dialog(this);
		responseDialog.setContentView(R.layout.view_tune_dialog);
		responseDialog.setTitle("Response");
		responseDialog.show();
		
		txtDialogTitle = (TextView) responseDialog.findViewById(R.id.txtViewTuneDialogTitle);
		txtDialogArtist = (TextView) responseDialog.findViewById(R.id.txtViewTuneDialogArtist);
		btnDialogAddResponse = (Button) responseDialog.findViewById(R.id.btnViewTuneDialogResponse);
		
		btnDialogAddResponse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!validateDialog()){
					displayToast(responseDialog.getContext(), "Please enter title and artist.");
				
				}else if(tuneParse.getCreatedBy().getUsername().equalsIgnoreCase(ParseUser.getCurrentUser().getUsername())){
					displayToast(responseDialog.getContext(), "You cannot add response to your tune.");
				}else{
					responsesToTuneCount();
				}
			}
		});
	}
	
	public void responsesToTuneCount(){
		progressDialog = ProgressDialog.show(responseDialog.getContext(), "", "Loading...", true);
		ParseQuery<Response> query = ParseQuery.getQuery("Response");
		query.whereEqualTo("createdBy", ParseUser.getCurrentUser());
		query.whereEqualTo("tune", tuneParse);
		query.countInBackground(new CountCallback() {
			
			@Override
			public void done(int count, ParseException e) {
				if(e == null){
					if(count == 0 ){
						saveResponse();
					}else{
						progressDialog.dismiss();
						displayToast(responseDialog.getContext(), "You've already added a response to this tune.");
					}
						
				}else{
					progressDialog.dismiss();
					displayToast(responseDialog.getContext(), e.getMessage());
					Log.d(TAG, e.getMessage());
				}
				
			}
		});
	}
	
	public void saveResponse(){
		
		Response response = new Response();
		ParseACL responseACL = new ParseACL(ParseUser.getCurrentUser());
		responseACL.setWriteAccess(tuneParse.getCreatedBy(), true);
		responseACL.setPublicReadAccess(true);
		response.setACL(responseACL);
		
		response.setCreatedBy(ParseUser.getCurrentUser());
		response.setTune(tuneParse);
		response.setArtist(txtDialogArtist.getText().toString());
		response.setTitle(txtDialogTitle.getText().toString());
		response.setStatus("pending");
		response.saveEventually(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				progressDialog.dismiss();
				if(e == null){
					responseDialog.dismiss();
					Toast.makeText(responseDialog.getContext(), "Your response recorded. Thank you!", Toast.LENGTH_LONG).show();
					sendPushNotification();
				}else{
					Toast.makeText(responseDialog.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}
	
	public void sendPushNotification(){
		ParseQuery pushQuery = ParseInstallation.getQuery();
		pushQuery.whereEqualTo("user", tuneParse.getCreatedBy());
		
		ParsePush push = new ParsePush();
		push.setQuery(pushQuery);
		push.setMessage(ParseUser.getCurrentUser().getString("name") + " added a response to your tune.");
		push.sendInBackground();
		
	}
	
	public void delete(){
		
		
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage("All the responses made to this tune will also be deleted. Do you want to proceed?");
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
			}
		});
		
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ParseQuery<Response> query = ParseQuery.getQuery("Response");
				query.whereEqualTo("tune", tuneParse);
				query.findInBackground(new FindCallback<Response>() {
					
					@Override
					public void done(List<Response> objects, ParseException e) {
						for(Response r : objects){
							r.deleteInBackground();
						}
						tuneParse.deleteInBackground();
					}
				});
				displayToast(ViewTuneItemActivity.this, "Your tune deleted successfully.");
				startActivity(new Intent(ViewTuneItemActivity.this, MainMenuActivity.class));
			}
		});
		alertDialog.show();
		
	}
	
	public boolean validateDialog(){
		String title = txtDialogTitle.getText().toString();
		String artist = txtDialogArtist.getText().toString();
		
		if(title.length() == 0 || artist.length() == 0){
			return false;
		}
		return true;
	}
	
	public void displayToast(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	private String testText(String text){
		if(text.equalsIgnoreCase(""))
			return "Not Entered";
		else
			return text;
	}

}
