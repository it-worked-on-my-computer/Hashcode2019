package it.gdgtorino.hashcode;

import it.gdgtorino.hashcode.model.Node;
import it.gdgtorino.hashcode.model.Photo;
import it.gdgtorino.hashcode.model.Slide;
import it.gdgtorino.hashcode.model.Vertex;
import it.gdgtorino.hashcode.utils.Utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlideTreeWorker {

    List<Photo> photosHorizontales;
    List<Photo> photosVerticales;
    Map<Integer, Node> verticalPhotoTree;

    Map<String, Node> slideTree;

    public SlideTreeWorker(List<Photo> photosHorizontales, List<Photo> photosVerticales, Map<Integer, Node> verticalPhotoTree) {
        this.photosHorizontales = photosHorizontales;
        this.photosVerticales = photosVerticales;
        this.verticalPhotoTree = verticalPhotoTree;

        this.slideTree = new HashMap<>();
    }

    public void walk(){
        this.buildTree();

//        slideTree.keySet().iterator().next().
    }

    private void buildTree() {
        Utility utility = Utility.getInstance();

        for (Photo photo : this.photosHorizontales) {
            // Créer le slide
            Slide newSlide = new Slide();
            newSlide.addId(photo.getId());
            newSlide.addTags(photo.getTags());

            // Créer le noeud
            Node<Slide> currentNode = new Node<>(newSlide.ids.get(0).toString(), newSlide);

            // Itérer sur les noeud existantd
            for (Node<Slide> node : slideTree.values()) {
                // calculer le score avec le slide créé précédemment
                int score = utility.calculateScore(currentNode.element, node.element);

                // ajouter l'arêt aux 2 noeuds
                currentNode.neighborsWeight.put(node.id, score);
                node.neighborsWeight.put(currentNode.id, score);
            }

            // ajouter le noeud courant au graphe
            this.slideTree.put(currentNode.id, currentNode);
        }
    }

}
