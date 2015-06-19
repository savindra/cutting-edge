package lk.ac.iit.humzearch.adapter;

import java.text.SimpleDateFormat;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.app.AppController;
import lk.ac.iit.humzearch.model.Response;
import lk.ac.iit.humzearch.model.TuneParse;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class ViewResponseAdapter extends ParseQueryAdapter<Response> {
	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public ViewResponseAdapter(Context context) {
		
		super(context, new ParseQueryAdapter.QueryFactory<Response>() {

			@Override
			public ParseQuery<Response> create() {
				ParseQuery<TuneParse> innerQuery = ParseQuery.getQuery("Tune");
				innerQuery.whereEqualTo("createdBy", ParseUser.getCurrentUser());
				ParseQuery<Response> query = ParseQuery.getQuery("Response");
				query.whereMatchesQuery("tune", innerQuery);
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
		
		NetworkImageView imgUser = (NetworkImageView) v.findViewById(R.id.imgViewResponseUser);
		String imgUrl = object.getCreatedBy().getParseFile("image").getUrl();
		imgUser.setImageUrl(imgUrl, imageLoader);
		
		
		TextView txtMessage = (TextView) v.findViewById(R.id.txtViewResponse);
		
		String message = "<b>" + object.getCreatedBy().getString("name") + "</b>" + " added a response to your tune.";
		txtMessage.setText(Html.fromHtml(message));
		
		SimpleDateFormat f = new SimpleDateFormat("dd MMMM 'at' HH:mm");
		TextView txtDate = (TextView) v.findViewById(R.id.txtViewResponseItemDate);
		txtDate.setText(f.format(object.getCreatedAt()));
		
		return v;
	}
	
	

}
