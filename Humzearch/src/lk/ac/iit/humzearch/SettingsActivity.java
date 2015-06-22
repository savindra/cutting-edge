package lk.ac.iit.humzearch;

import com.parse.ParseUser;

import lk.ac.iit.humzearch.adapter.SettingsAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SettingsActivity extends ActionBarActivity {
	
	private String[] settingMenus = {"Profile", "Address", "Logout"};
	private Integer[] settingMenuIcons = {R.drawable.ic_myaccount, R.drawable.ic_address,R.drawable.ic_logout};
	
	private ListView listView;
	private SettingsAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		adapter = new SettingsAdapter(this, settingMenus, settingMenuIcons);
		listView = (ListView) findViewById(R.id.listSettings);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch(position){
				
				case 0:
					startActivity(new Intent(SettingsActivity.this, SettingsProfileActivity.class));
					break;
				case 1:
					startActivity(new Intent(SettingsActivity.this, SettingsAddressActivity.class));
					break;
				case 2:
					logout();
					break;
				}
				
			}
		});
	}
	
	private void logout(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Alert");
		alertDialog.setMessage("Are you sure you want to logout?");
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ParseUser.logOut();
				Intent intent = new Intent(SettingsActivity.this, LoginSignupActivity.class);
				startActivity(intent);
				
			}
		});
		alertDialog.show();
	}
	

}
