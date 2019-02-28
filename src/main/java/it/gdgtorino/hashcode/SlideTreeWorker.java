package it.gdgtorino.hashcode;

import it.gdgtorino.hashcode.model.Node;
import it.gdgtorino.hashcode.model.Photo;
import it.gdgtorino.hashcode.model.Slide;
import it.gdgtorino.hashcode.model.Vertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlideTreeWorker {

    List<Photo> photos;
    Map<Integer, Node> verticalPhotoTree;

    Map<Integer, Slide> slideTree;

    public SlideTreeWorker(List<Photo> photos, Map<Integer, Node> verticalPhotoTree) {
        this.photos = photos;
        this.verticalPhotoTree = verticalPhotoTree;

        this.slideTree = new HashMap<>();
    }

    public void buildTree(){
        // TODO
    }
}
