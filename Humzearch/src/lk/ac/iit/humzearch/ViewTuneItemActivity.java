package lk.ac.iit.humzearch;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;

import lk.ac.iit.humzearch.model.TuneParse;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class ViewTuneItemActivity extends ActionBarActivity {
	
	private final String TAG = ViewTuneItemActivity.class.getSimpleName();
	
	private TextView txtName, txtArtist, txtLanguage, txtCountry, txtYear;
	private ParseImageView imgUser;
	private Button btnPlay, btnPause, btnAddRespone;
	private SeekBar seekBar;
	private Dialog responseDialog;
	
	private ParseFile imgFile, tuneFile;
	
	private ParseQuery<TuneParse> tuneQuery;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_tune_item);
		initialize();
	}

	private void initialize() {
		txtName = (TextView) findViewById(R.id.txtViewTuneItemName);
		txtArtist = (TextView) findViewById(R.id.txtViewTuneItemArtist);
		txtLanguage = (TextView) findViewById(R.id.txtViewTuneItemLang);
		txtCountry = (TextView) findViewById(R.id.txtViewTuneItemCountry);
		txtYear = (TextView) findViewById(R.id.txtViewTuneItemYear);
		imgUser = (ParseImageView) findViewById(R.id.imgViewTuneItemUser);
		btnPlay = (Button) findViewById(R.id.btnViewTuneItemPlay);
		btnPause = (Button) findViewById(R.id.btnViewTuneItemPause);
		btnAddRespone = (Button) findViewById(R.id.btnViewTuneResponse);
		seekBar = (SeekBar) findViewById(R.id.seekBarViewTuneItem);
		
		btnAddRespone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addResponse();
				
			}
		});
		
		filData();
		
	}

	private void filData() {
		String objID = getIntent().getStringExtra("tuneParseID");
		
		tuneQuery = ParseQuery.getQuery("Tune");
		tuneQuery.whereEqualTo("objectId", objID);
		tuneQuery.include("createdBy");
		tuneQuery.getFirstInBackground(new GetCallback<TuneParse>() {
			
			@Override
			public void done(TuneParse tune, ParseException e) {
				if(e == null){
					txtName.setText(tune.getCreatedBy().getString("name"));
					txtArtist.setText(tune.getArtist());
					txtLanguage.setText(tune.getLanguage());
					txtCountry.setText(tune.getCountry());
					txtYear.setText(tune.getYear());
					
					imgFile = tune.getCreatedBy().getParseFile("image");
					if(imgFile != null){
						imgUser.setParseFile(imgFile);
						imgUser.loadInBackground();
					}
				}else{
					Log.d(TAG, e.getMessage());
				}
			}
		});
		
	}
	
	private void addResponse() {
		
		responseDialog = new Dialog(this);
		responseDialog.setContentView(R.layout.view_tune_dialog);
		responseDialog.setTitle("Response");
		responseDialog.show();
	}

}
