package lk.ac.iit.humzearch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lk.ac.iit.humzearch.TuneResultActivity;
import lk.ac.iit.humzearch.model.Tune;
import lk.ac.iit.humzearch.util.AndroidMultiPartEntity.ProgressListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.sax.StartElementListener;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

public class UploadTune extends AsyncTask<Void, Integer, String> {
	
	private static final String TAG = UploadTune.class.getSimpleName();
	
	private final String DORESO_API_KEY = "LLUF2Ei7raogz5tlM7tihDmjR715SGPonLxASq19YBk";
	private final String DORESO_API_LINK = "http://developer.doreso.com/api/v1/song/identify";
	
	private String ITUNES_LINK = "https://itunes.apple.com/search";
	
	private Context context;
	private ProgressDialog progressDialog;
	private String tuneFile;
	private Tune tune;
	
	private JSONArray tuneData;
	
	private AlertDialog alertDialog;
	
	public UploadTune(Context context, String tuneFile) {
		super();
		this.context = context;
		this.tuneFile = "/storage/emulated/0/Download/test.mp3";
		tune = new Tune();
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(context, "", "Searching...", true);
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Void... params) {
		String response = recognizeTune();
		String statusCode, msg, result = null;
		try{
			JSONObject jsonObj = new JSONObject(response);
			statusCode = jsonObj.getString("status");
			msg = jsonObj.getString("msg");
			
			Log.d(TAG, statusCode + " " + msg );
			
			if(statusCode.equals("1") && msg.equals("success")){
				tuneData = jsonObj.getJSONArray("data");
				
				JSONObject t = tuneData.getJSONObject(0);
				tune.setTitle(t.getString("name"));
				tune.setArtist(t.getString("artist_name"));
				tune.setAlbum(t.getString("album"));
				
				String metaData = getArtwork();
				jsonObj = new JSONObject(metaData);
				
				statusCode = null;
				statusCode = jsonObj.getString("resultCount");
				
				if(statusCode.equals("1")){
					tuneData = jsonObj.getJSONArray("results");
					
					t = tuneData.getJSONObject(0);
					tune.setImg(t.getString("artworkUrl100"));
					tune.setCountry(t.getString("country"));
					tune.setUrl(t.getString("previewUrl"));
					
				}
				
				result = "success";
				
			}else{
				result = msg;
			}
			
		}catch(JSONException e){
			Log.d(TAG, e.toString());
		}
		Log.d(TAG, tune.toString());
		return result;
	}
	
	public String getArtwork(){
		StringBuilder sb = new StringBuilder();
		JSONObject json = null;
		
		ITUNES_LINK += "?term=" + tune.getTitle().replace(" ", "+").trim() + "+" +
				tune.getArtist().replace(" ", "+").trim() + "&media=music&entity=song&limit=1";
		
		Log.d(TAG, ITUNES_LINK);
		try{
			json = new JSONObject(EntityUtils.toString(
                    new DefaultHttpClient().execute(
                            new HttpGet(ITUNES_LINK)).getEntity()));
		    
		}catch(Exception e){
			Log.d(TAG, e.toString());
		}
		return json.toString();
	}
	
	
	
	@SuppressWarnings("deprecation")
	public String recognizeTune(){
		String responseString = null;
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(DORESO_API_LINK);
		
		try{
			AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {
				
				@Override
				public void transferred(long num) {
					// TODO Auto-generated method stub
					
				}
			});
			
			File sourceFile = new File(tuneFile);
			
			entity.addPart("track", new FileBody(sourceFile));
			entity.addPart("api_key", new StringBody(DORESO_API_KEY));
			Log.d(TAG, tuneFile);
			
			httpPost.setEntity(entity);
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity r_entity = response.getEntity();
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode == 200){
				responseString = EntityUtils.toString(r_entity);
			} else {
				responseString = "Error occurred! Http Status Code: " + statusCode;
			}
			
		} catch (ClientProtocolException e){
			responseString = e.toString();
		} catch (Exception e){
			responseString = e.toString();
			e.printStackTrace();
		}
		return responseString;
	}
	
	@Override
	protected void onPostExecute(String result) {
		progressDialog.dismiss();
		if(result.equals("success")){
			Intent intent = new Intent(context, TuneResultActivity.class);
			intent.putExtra("tune_img", tune.getImg());
			intent.putExtra("tune_title", tune.getTitle());
			intent.putExtra("tune_artist", tune.getArtist());
			intent.putExtra("tune_album", tune.getAlbum());
			intent.putExtra("tune_country", tune.getCountry());
			intent.putExtra("tune_url", tune.getUrl());
			context.startActivity(intent);
		}else{
			alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setMessage(result);
			alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					alertDialog.dismiss();
					
				}
			});
			alertDialog.show();
		}
		super.onPostExecute(result);
	}

}
