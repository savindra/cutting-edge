package lk.ac.iit.humzearch.fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import lk.ac.iit.humzearch.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TabSignUp extends Fragment {
	
	private TextView txtName;
	private TextView txtEmail;
	private TextView txtPassword;
	private TextView txtConfirmPass;
	private Button btnSignUp;
	private String name, email, password, confirmPass, result = "";
	private boolean isValid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tab_signup, container, false);
		
		txtName = (TextView) v.findViewById(R.id.txtSignupName);
		txtEmail = (TextView) v.findViewById(R.id.txtSignupEmail);
		txtPassword = (TextView) v.findViewById(R.id.txtSignupPassword);
		txtConfirmPass = (TextView) v.findViewById(R.id.txtSignupConfirmpass);
		btnSignUp = (Button) v.findViewById(R.id.btnSignup);
		
		btnSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				name = txtName.getText().toString();
				email = txtEmail.getText().toString();
				password = txtPassword.getText().toString();
				confirmPass = txtConfirmPass.getText().toString();
				isValid = true;
				
				if(name.length() == 0 || email.length() == 0 || password.length() == 0){
					isValid = false;
					result = "Please enter all fields.";
					displayResult(result, false);
				}else if(password.length() < 6){
					isValid = false;
					result = "Password should contain atleast 6 characters.";
					displayResult(result, false);
				}else if(!(password.equals(confirmPass))){
					isValid = false;
					result = "Passwords do not match.";
					displayResult(result, false);
				}
				
				if(isValid)
					signup();
				
				
			}
		});
		
		
		return v;
	}
	
	private void signup(){
		
		ParseUser user = new ParseUser();
		user.setUsername(email);
		user.setPassword(password);
		user.put("name", name);
		
		ParseUser.logOut();
		user.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(ParseException e) {
				if(e == null){
					result = "Registration completed.";
				} else {
					result = e.toString();
				}
				displayResult(result, true);
			}
		});
	}
	
	private void displayResult(String result, boolean isLong){
		if(isLong)
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		else
			Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
	}
	

}
