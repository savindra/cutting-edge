package lk.ac.iit.humzearch.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("UserData")
public class UserData extends ParseObject {
	
	public ParseUser getCreatedBy(){
		return getParseUser("createdBy");
	}
	
	public void setCreatedBy(ParseUser user){
		put("createdBy", user);
	}
	
	public int getPoints(){
		return getInt("points");
	}
	
	public void setPoints(int points){
		put("points", points);
	}
	
	public String getAddress1(){
		return getString("address1");
	}
	
	public void setAddress1(String address1){
		put("address1", address1);
	}
	
	public String getAddress2(){
		return getString("address2");
	}
	
	public void setAddress2(String address2){
		put("address2", address2);
	}
	
	public String getCity(){
		return getString("city");
	}
	
	public void setCity(String city){
		put("city", city);
	}
	
	public String getCountry(){
		return getString("country");
	}
	
	public void setCountry(String country){
		put("country", country);
	}
	
	public String getPostCode(){
		return getString("postcode");
	}
	
	public void setPostCode(String postcode){
		put("postcode", postcode);
	}
	

}
