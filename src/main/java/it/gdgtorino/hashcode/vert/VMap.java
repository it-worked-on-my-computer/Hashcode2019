package it.gdgtorino.hashcode.vert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gdgtorino.hashcode.model.Node;
import it.gdgtorino.hashcode.model.Photo;
import it.gdgtorino.hashcode.model.Vertex;

public class VMap {

	Map<String, Node> computeVMap(final List<Photo> images) {
		
		Map<String, Node> vmap = new HashMap<>();
		
		for(Photo image : images) {
			Node node = new Node<Photo>(image.getId(), image);
			vmap.put(image.getId(), node);
			
			for(Photo image1 : images) {
				if(image1 != image) {

					Set<String> tags = new HashSet<String>();
					tags.addAll(image.getTags());
					tags.addAll(image1.getTags());
					
					node.neighborsWeight.put(image1.getId(), tags.size());
					
				}
			}
		}
		
		return vmap;
	}
}
