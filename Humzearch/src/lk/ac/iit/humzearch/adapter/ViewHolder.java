package lk.ac.iit.humzearch.adapter;

import com.android.volley.toolbox.NetworkImageView;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	
	TextView txtTitle, txtCount;
	ImageView imgIcon;
	NetworkImageView imgUser;

	public ViewHolder(TextView txtTitle, ImageView imgIcon) {
		super();
		this.txtTitle = txtTitle;
		this.imgIcon = imgIcon;
	}
	
	public ViewHolder(TextView txtTitle, NetworkImageView imgUser) {
		super();
		this.txtTitle = txtTitle;
		this.imgUser = imgUser;
	}
	
	
	
	public TextView getTxtTitle() {
		return txtTitle;
	}
	public void setTxtTitle(TextView txtTitle) {
		this.txtTitle = txtTitle;
	}
	public ImageView getImgIcon() {
		return imgIcon;
	}
	public void setImgIcon(ImageView imgIcon) {
		this.imgIcon = imgIcon;
	}

	public TextView getTxtCount() {
		return txtCount;
	}

	public void setTxtCount(TextView txtCount) {
		this.txtCount = txtCount;
	}

	public NetworkImageView getImgUser() {
		return imgUser;
	}

	public void setImgUser(NetworkImageView imgUser) {
		this.imgUser = imgUser;
	}
	
	

}
