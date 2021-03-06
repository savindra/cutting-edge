package lk.ac.iit.humzearch.fragment;

import java.util.List;

import com.parse.ParseQueryAdapter.OnQueryLoadListener;

import lk.ac.iit.humzearch.R;
import lk.ac.iit.humzearch.ViewRewardItem;
import lk.ac.iit.humzearch.adapter.ViewRewardAdapter;
import lk.ac.iit.humzearch.model.Reward;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ViewRewardsFragment extends Fragment {
	
	private ViewRewardAdapter rewardAdapter;
	private ListView listView;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.view_reward, container, false);
		initialize(rootView);
		return rootView;
		
	}

	private void initialize(View rootView) {
		rewardAdapter = new ViewRewardAdapter(getActivity());
		rewardAdapter.addOnQueryLoadListener(new OnQueryLoadListener<Reward>() {

			@Override
			public void onLoaded(List<Reward> objects, Exception e) {
				progressDialog.dismiss();
				
			}

			@Override
			public void onLoading() {
				if(progressDialog == null)
					progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
				
			}
		});
		
		listView = (ListView) rootView.findViewById(R.id.listViewRewards);
		rewardAdapter.loadObjects();
		listView.setAdapter(rewardAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Reward r = rewardAdapter.getItem(position);
				
				Intent intent = new Intent(getActivity(), ViewRewardItem.class);
				intent.putExtra("reward_name", r.getName());
				intent.putExtra("reward_img", r.getImage().getUrl());
				intent.putExtra("reward_desc", r.getDescription());
				intent.putExtra("reward_value", String.valueOf(r.getValue()));
				intent.putExtra("reward_id", r.getObjectId());
				startActivity(intent);
				
			}
		});
		
		rewardAdapter.notifyDataSetChanged();
		
	}
	
	

}
