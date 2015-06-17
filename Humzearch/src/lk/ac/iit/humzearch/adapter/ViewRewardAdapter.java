package lk.ac.iit.humzearch.adapter;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.app.AppController;
import lk.ac.iit.humzearch.model.Reward;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class ViewRewardAdapter extends ParseQueryAdapter<Reward> {
	
	private final String TAG = ViewRewardAdapter.class.getName();
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public ViewRewardAdapter(Context context) {
		super(context,  new ParseQueryAdapter.QueryFactory<Reward>() {

			public ParseQuery create() {
				ParseQuery<Reward> query = ParseQuery.getQuery("reward");
				query.orderByDescending("value");
				return query;
			}
			
		});
	}

	@Override
	public View getItemView(Reward object, View v, ViewGroup parent) {
		if(v == null){
			v = v.inflate(getContext(), R.layout.view_reward_row, null);
		}
		super.getItemView(object, v, parent);
		
		NetworkImageView imgReward = (NetworkImageView) v.findViewById(R.id.imgViewReward);
		String imgUrl = object.getParseFile("image").getUrl();
		imgReward.setImageUrl(imgUrl, imageLoader);
		
		TextView txtName = (TextView) v.findViewById(R.id.txtViewRewardName);
		txtName.setText(object.getString("name"));
		
		TextView txtValue = (TextView) v.findViewById(R.id.txtViewRewardValue);
		txtValue.setText(object.getValue() + " points");
		
		return v;
	}
	
	

}
