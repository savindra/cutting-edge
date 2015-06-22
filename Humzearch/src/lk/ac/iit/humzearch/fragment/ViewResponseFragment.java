package lk.ac.iit.humzearch.fragment;

import java.text.SimpleDateFormat;
import java.util.List;

import com.parse.ParseQueryAdapter.OnQueryLoadListener;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.ViewResponseItem;
import lk.ac.iit.humzearch.adapter.ViewResponseAdapter;
import lk.ac.iit.humzearch.model.Response;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ViewResponseFragment extends Fragment {
	
	private ViewResponseAdapter responseAdapter;
	private ListView listview;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.view_response, container, false);
		intialize(rootView);
		return rootView;
	}

	private void intialize(View rootView) {
		responseAdapter = new ViewResponseAdapter(getActivity());
		
		responseAdapter.addOnQueryLoadListener(new OnQueryLoadListener<Response>() {

			@Override
			public void onLoaded(List<Response> objects, Exception e) {
				progressDialog.dismiss();
				
			}

			@Override
			public void onLoading() {
				if(progressDialog == null)
					progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
				
			}
		});
		
		listview = (ListView) rootView.findViewById(R.id.listViewResponse);
		listview.setAdapter(responseAdapter);
		responseAdapter.loadObjects();
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Response r = responseAdapter.getItem(position);
				SimpleDateFormat f = new SimpleDateFormat("dd MMMM 'at' HH:mm");
				
				Intent intent = new Intent(getActivity(), ViewResponseItem.class);
				intent.putExtra("response_id", r.getObjectId());
				intent.putExtra("response_name", r.getCreatedBy().getString("name"));
				intent.putExtra("response_title", r.getTitle());
				intent.putExtra("response_artist", r.getArtist());
				intent.putExtra("response_date", f.format(r.getCreatedAt()));
				startActivity(intent);
				
			}
		});
		responseAdapter.notifyDataSetChanged();
	}

}
