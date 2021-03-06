package lk.ac.iit.humzearch.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Response")
public class Response extends ParseObject {
	
	public ParseUser getCreatedBy(){
		return getParseUser("createdBy");
	}
	
	public void setCreatedBy(ParseUser user){
		put("createdBy", user);
	}
	
	public TuneParse getTune(){
		return (TuneParse) getParseObject("tune");
	}
	
	public void setTune(TuneParse tune){
		put("tune", tune);
	}
	
	public String getArtist(){
		return getString("artist");
	}
	
	public void setArtist(String artist){
		put("artist", artist);
	}
	
	public String getTitle(){
		return getString("title");
	}
	
	public void setTitle(String title){
		put("title", title);
	}
	
	public String getStatus(){
		return getString("status");
	}
	
	public void setStatus(String status){
		put("status", status);
	}

}
