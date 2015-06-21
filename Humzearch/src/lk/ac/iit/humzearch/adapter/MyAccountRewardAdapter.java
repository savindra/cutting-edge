package lk.ac.iit.humzearch.adapter;

import java.text.SimpleDateFormat;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.app.AppController;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class MyAccountRewardAdapter extends ParseQueryAdapter<ParseObject> {
	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public MyAccountRewardAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {

			@Override
			public ParseQuery<ParseObject> create() {
				ParseQuery<ParseObject> query = ParseQuery.getQuery("UserReward");
				query.whereEqualTo("createdBy", ParseUser.getCurrentUser());
				query.include("reward");
				return query;
			}
		});
	}

	@Override
	public View getItemView(ParseObject object, View v, ViewGroup parent) {
		if(v == null){
			v = v.inflate(getContext(), R.layout.my_account_reward_row, null);
		}
		super.getItemView(object, v, parent);
		
		NetworkImageView imgReward = (NetworkImageView) v.findViewById(R.id.imgMyAccountReward);
		imgReward.setImageUrl(object.getParseObject("reward").getParseFile("image").getUrl(), imageLoader);
		
		TextView txtRewardName = (TextView) v.findViewById(R.id.txtMyAccountRewardName);
		txtRewardName.setText(object.getParseObject("reward").getString("name"));
		
		TextView txtRewardDate = (TextView) v.findViewById(R.id.txtMyAccountRewardDate);
		SimpleDateFormat f = new SimpleDateFormat("dd MMMM 'at' HH:mm");
		txtRewardDate.setText(f.format(object.getCreatedAt()));
		
		return v;
	}
	
	

}
