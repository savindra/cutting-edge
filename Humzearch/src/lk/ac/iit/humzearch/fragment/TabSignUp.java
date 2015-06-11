package lk.ac.iit.humzearch.fragment;

import java.io.ByteArrayOutputStream;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import lk.ac.iit.humzearch.R;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private ProgressDialog progressDialog;

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
					uploadImage();
				
				
			}
		});
		
		
		return v;
	}
	
	private void signup(ParseFile imgFile){
		
		ParseUser user = new ParseUser();
		user.setUsername(email);
		user.setPassword(password);
		user.put("image", imgFile);
		user.put("points", 0);
		user.put("name", name);
		
		user.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(ParseException e) {
				progressDialog.hide();
				if(e == null){
					result = "Registration completed.";
					ParseUser.logOut();
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
	
	private void uploadImage(){
		progressDialog = ProgressDialog.show(getActivity(), "", "Signing up...", true);
		final ParseFile imgFile = new ParseFile("image.png", getImageByteArray());
		imgFile.saveInBackground( new SaveCallback() {		
			@Override
			public void done(ParseException e) {
				if(e == null){
					signup(imgFile);
				} else{
					displayResult("Signup failed", true);	
				}	
			}
		});
	}
	
	public byte[] getImageByteArray(){
		Bitmap img = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_default_user);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		img.compress(Bitmap.CompressFormat.PNG, 0, stream);
		byte[] imgData = stream.toByteArray();
		return imgData;
	}
	

}
