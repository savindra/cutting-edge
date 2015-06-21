package lk.ac.iit.humzearch.model;

import java.util.Date;

public class Tune {
	
	private String tuneID;
	private String title = "";
	private String artist = "";
	private String album = "";
	private String country = "";
	private String img = "";
	private String url = "";
	private Date date_added;
	private String year;
	
	public Tune() {
		super();
	}

	public String getTuneID() {
		return tuneID;
	}

	public void setTuneID(String tuneID) {
		this.tuneID = tuneID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate_added() {
		return date_added;
	}

	public void setDate_added(Date date_added) {
		this.date_added = date_added;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Tune [tuneID=" + tuneID + ", title=" + title + ", artist="
				+ artist + ", album=" + album + ", country=" + country
				+ ", img=" + img + ", url=" + url + ", date_added="
				+ date_added + ", year=" + year + "]";
	}
	
}
