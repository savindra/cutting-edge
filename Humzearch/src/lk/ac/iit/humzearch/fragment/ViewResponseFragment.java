package lk.ac.iit.humzearch.fragment;

import java.util.List;

import com.parse.ParseQueryAdapter.OnQueryLoadListener;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.adapter.ViewResponseAdapter;
import lk.ac.iit.humzearch.model.Response;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
		
	}

}