package it.gdgtorino.hashcode.model;

import java.util.ArrayList;
import java.util.List;

public class Slide {
	
	public List<Integer> ids;
	public List<String> tags;
	
	public Slide(){
		ids = new ArrayList<>();
		tags = new ArrayList<>();
	}
	
	public void addId(int id){
		ids.add(id);
	}

	public void addTags(List<String> tags){
		for (String tag : tags) {
			if(!this.tags.contains(tag)){
				this.tags.add(tag);
			}
		}
	}
}