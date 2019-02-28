package it.gdgtorino.hashcode.model;

import java.util.ArrayList;
import java.util.List;

public class Slide {

	public String id;
	public List<String> tags;

	public Slide() {
		id = "";
		tags = new ArrayList<>();
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addTags(List<String> tags) {
		for (String tag : tags) {
			if (!this.tags.contains(tag)) {
				this.tags.add(tag);
			}
		}
	}
}