package lk.ac.iit.humzearch.fragment;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import lk.ac.iit.humzearch.MainMenuActivity;
import lk.ac.iit.humzearch.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TabLogin extends Fragment {
	
	private TextView txtEmail;
	private TextView txtPassword;
	private Button btnLogin;
	private String email, password, result;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tab_login, container, false);
		
		txtEmail = (TextView) v.findViewById(R.id.txtLoginEmail);
		txtPassword = (TextView) v.findViewById(R.id.txtLoginpass);
		btnLogin = (Button) v.findViewById(R.id.btnLogin);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				email = txtEmail.getText().toString();
				password = txtPassword.getText().toString();
				
				if(email.length() == 0 || password.length() == 0){
					result = "Please enter email and password";
					displayResult(result);
				}else {
					progressDialog = ProgressDialog.show(getActivity(), "Login", "Please Wait...Login in Progress..", true);
					login();
					
				}
				
			}
		});
		
		return v;
	}
	
	private void login(){
		ParseUser.logInInBackground(email, password, new LogInCallback() {
			
			@Override
			public void done(ParseUser user, ParseException e) {
				progressDialog.hide();
				if(user != null){
					Intent MainMenuScreen = new Intent(getActivity(), MainMenuActivity.class);
					getActivity().startActivity(MainMenuScreen);
				}else{
					result = "Wrong Email/Password combination.";
					displayResult(result);
				}
				
			}
		});
		
	}
	
	
	
	private void displayResult(String result){
		Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
	}

}
