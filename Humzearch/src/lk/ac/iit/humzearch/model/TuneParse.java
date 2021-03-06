package lk.ac.iit.humzearch.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Tune")
public class TuneParse extends ParseObject  {
	
	public String getArtist(){
		return getString("artist");
	}
	
	public void setArtist(String artist){
		put("artist", artist);
	}
	
	public String getCountry(){
		return getString("country");
	}
	
	public void setCountry(String country){
		put("country", country);
	}
	
	public ParseUser getCreatedBy(){
		return getParseUser("createdBy");
	}
	
	public void setCreatedBy(ParseUser user){
		put("createdBy", user);
	}
	
	public String getLanguage(){
		return getString("language");
	}
	
	public void setLanguage(String language){
		put("language", language);
	}
	
	public String getStatus(){
		return getString("status");
	}
	
	public void setStatus(String status){
		put("status", status);
	}
	
	public ParseFile getTuneFile(){
		return getParseFile("tune");
	}
	
	public void setTuneFile(ParseFile tune){
		put("tune", tune);
	}
	
	public String getYear(){
		return getString("year");
	}
	
	public void setYear(String year){
		put("year", year);
	}

}
