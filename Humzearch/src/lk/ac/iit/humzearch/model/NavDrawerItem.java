package lk.ac.iit.humzearch.model;

public class NavDrawerItem {
	
	private String title;
	private int icon;
	private int type;
	private String img;
	private String count = "0";
	private boolean isCounterVisible = false;
	
	public NavDrawerItem() {
		super();
	}

	public NavDrawerItem(String title, int icon, int type) {
		super();
		this.title = title;
		this.type = type;
		this.icon = icon;
	}
	
	public NavDrawerItem(String title, String img, int type) {
		super();
		this.title = title;
		this.type = type;
		this.img = img;
	}

	public NavDrawerItem(String title, int icon, int type, 
			boolean isCounterVisible, String count) {
		super();
		this.title = title;
		this.icon = icon;
		this.count = count;
		this.type = type;
		this.isCounterVisible = isCounterVisible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public boolean isCounterVisible() {
		return isCounterVisible;
	}

	public void setCounterVisible(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
