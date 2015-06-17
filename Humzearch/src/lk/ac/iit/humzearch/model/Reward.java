package lk.ac.iit.humzearch.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("reward")
public class Reward extends ParseObject {
	
	public String getName(){
		return getString("name");
	}
	
	public void setName(String name){
		put("name", name);
	}
	
	public String getDescription(){
		return getString("description");
	}
	
	public void setDescription(String description){
		put("description", description);
	}
	
	public int getQuantity(){
		return getInt("quantity");
	}
	
	public void setQuantity(int quantity){
		put("quantity", quantity);
	}
	
	public int getValue(){
		return getInt("value");
	}
	
	public void setValue(int value){
		put("value", value);
	}
	
	public ParseFile getImage(){
		return getParseFile("image");
	}
	
	public void setImage(ParseFile image){
		put("image", image);
	}

}
