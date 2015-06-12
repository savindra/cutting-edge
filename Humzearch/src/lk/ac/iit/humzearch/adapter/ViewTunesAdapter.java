package lk.ac.iit.humzearch.adapter;

import java.util.List;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.model.Tune;
import lk.ac.iit.humzearch.model.TuneParse;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class ViewTunesAdapter extends ParseQueryAdapter<TuneParse> {

	public ViewTunesAdapter(Context context) {
		
		super(context, new ParseQueryAdapter.QueryFactory<TuneParse>() {
			public ParseQuery create() {
				ParseQuery<ParseUser> innerQuery = ParseUser.getQuery();
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Tune");
				query.whereEqualTo("status", "pending");
				query.include("createdBy");
				query.addDescendingOrder("createdAt");
				return query;
			}
		});
	}

	@Override
	public View getItemView(TuneParse object, View v, ViewGroup parent) {
		if(v == null){
			v = v.inflate(getContext(), R.layout.view_tune_row, null);
		}
		super.getItemView(object, v, parent);
		
		ParseImageView imgUser = (ParseImageView) v.findViewById(R.id.imgViewTuneUser);
		ParseFile imgFile = object.getCreatedBy().getParseFile("image");
		if(imgFile != null){
			imgUser.setParseFile(imgFile);
			imgUser.loadInBackground();
		}
		
		TextView txtName = (TextView) v.findViewById(R.id.txtViewTuneUsername);
		txtName.setText(object.getCreatedBy().getString("name"));
		
		TextView txtTag = (TextView) v.findViewById(R.id.txtViewTuneTag);
		String tags = "TAGS: " + object.getArtist() +" "+ object.getLanguage()
				+" "+ object.getCountry() +" "+ object.getYear();
		txtTag.setText(tags);
		
		
		return v;
	}
	
}
