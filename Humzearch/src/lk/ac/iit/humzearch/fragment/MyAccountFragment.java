package lk.ac.iit.humzearch.fragment;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.adapter.MyAccountRewardAdapter;
import lk.ac.iit.humzearch.app.AppController;
import lk.ac.iit.humzearch.model.UserData;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class MyAccountFragment extends Fragment {
	
	private TextView txtName, txtEmail, txtDateJoined, txtAddress, txtPoints;
	private NetworkImageView imgUser;
	private ImageLoader imageLoader;
	private ListView listView;
	private MyAccountRewardAdapter adapter;
	
	private ParseUser user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.my_account, container, false);
		initialize(rootView);
		fillData();
		return rootView;
	}


	private void initialize(View rootView) {
		txtName = (TextView) rootView.findViewById(R.id.txtMyAccountName);
		txtEmail = (TextView) rootView.findViewById(R.id.txtMyAccountEmail);
		txtDateJoined = (TextView) rootView.findViewById(R.id.txtMyAccountDateJoined);
		txtAddress = (TextView) rootView.findViewById(R.id.txtMyAccountAddress);
		txtPoints = (TextView) rootView.findViewById(R.id.txtMyAccountPoints);
		imgUser = (NetworkImageView) rootView.findViewById(R.id.imgMyAccountUser);
		imageLoader = AppController.getInstance().getImageLoader();
		listView = (ListView) rootView.findViewById(R.id.listMyAccountReward);
		user = ParseUser.getCurrentUser();
		adapter = new MyAccountRewardAdapter(getActivity());
		
	}
	
	private void fillData(){
		txtName.setText(user.getString("name"));
		txtEmail.setText(user.getUsername());
		txtDateJoined.setText(user.getCreatedAt().toString());
		imgUser.setImageUrl(user.getParseFile("image").getUrl(), imageLoader);
		
		ParseQuery<UserData> userDataQuery = ParseQuery.getQuery("UserData");
		userDataQuery.whereEqualTo("createdBy", user);
		userDataQuery.getFirstInBackground(new GetCallback<UserData>() {
			
			@Override
			public void done(UserData object, ParseException e) {
				if(e == null){
					if(object.getAddress1() != null || object.getAddress2() != null){
						txtAddress.setText(object.getAddress1() + "\n" + object.getAddress2() + "\n" + object.getCity()
							+ "\n" + object.getCountry());
					}else{
						txtAddress.setText("Address not entered.");
					}
					txtPoints.setText(String.valueOf(object.getPoints()) + " Points");
				}
			}
		});
		
		adapter.loadObjects();
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		
	}

}
