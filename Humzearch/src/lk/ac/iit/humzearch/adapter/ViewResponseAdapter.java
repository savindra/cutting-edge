package lk.ac.iit.humzearch.adapter;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.model.Response;
import lk.ac.iit.humzearch.model.TuneParse;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class ViewResponseAdapter extends ParseQueryAdapter<Response> {
	
	static ParseUser currentUser = ParseUser.getCurrentUser();

	public ViewResponseAdapter(Context context) {
		
		super(context, new ParseQueryAdapter.QueryFactory<Response>() {

			@Override
			public ParseQuery<Response> create() {
				ParseQuery<TuneParse> innerQuery = ParseQuery.getQuery("Tune");
				innerQuery.whereEqualTo("createdBy", currentUser);
				ParseQuery<Response> query = ParseQuery.getQuery("Response");
				query.whereMatchesQuery("tune", innerQuery);
				query.whereEqualTo("status", "pending");
				query.include("tune");
				query.include("createdBy");
				query.addDescendingOrder("createdAt");
				return query;
			}
		});
	}

	@Override
	public View getItemView(Response object, View v, ViewGroup parent) {
		if(v == null){
			v = v.inflate(getContext(), R.layout.view_response_row, null);
		}
		super.getItemView(object, v, parent);
		
		ParseImageView imgUser = (ParseImageView) v.findViewById(R.id.imgViewResponseUser);
		ParseFile imgFile = object.getCreatedBy().getParseFile("image");
		
		if(imgFile != null){
			imgUser.setParseFile(imgFile);
			imgUser.loadInBackground();
		}
		
		
		TextView txtMessage = (TextView) v.findViewById(R.id.txtViewResponse);
		
		String message = "<b>" + object.getCreatedBy().getString("name") + "</b>" + " added a response to your tune.";
		txtMessage.setText(Html.fromHtml(message));
		return v;
	}
	
	

}
