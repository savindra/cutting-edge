package lk.ac.iit.humzearch.fragment;

import java.util.List;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.ViewTuneItemActivity;
import lk.ac.iit.humzearch.adapter.ViewTunesAdapter;
import lk.ac.iit.humzearch.model.TuneParse;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ViewTuneFragment extends Fragment {
	
	private ViewTunesAdapter adapter;
	private ListView listView;
	private List<TuneParse> tuneList;
	
	private ProgressDialog progressDialog;
	
	public ViewTuneFragment(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.view_tune, container, false);
		intialize(rootView);
		return rootView;
	}
	
	private void intialize(View rootView){
		adapter = new ViewTunesAdapter(getActivity());
		
		listView = (ListView) rootView.findViewById(R.id.listViewTune);
		listView.setAdapter(adapter);
		
		adapter.loadObjects();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TuneParse t = adapter.getItem(position);
				String objID = t.getObjectId();
				
				Intent intent = new Intent(getActivity(), ViewTuneItemActivity.class);
				intent.putExtra("tuneParseID", objID);
				startActivity(intent);
			}
		});
	}

}
