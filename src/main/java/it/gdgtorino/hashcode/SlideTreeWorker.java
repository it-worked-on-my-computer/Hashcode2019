package it.gdgtorino.hashcode;

import it.gdgtorino.hashcode.io.InputData;
import it.gdgtorino.hashcode.model.Node;
import it.gdgtorino.hashcode.model.Photo;
import it.gdgtorino.hashcode.model.Slide;
import it.gdgtorino.hashcode.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlideTreeWorker {

    List<Photo> photosHorizontales;
    List<Photo> photosVerticales;
    Map<Integer, Node> verticalPhotoTree;

    Map<String, Node> slideTree;

    public SlideTreeWorker(InputData inputData /*, Map<Integer, Node> verticalPhotoTree */ ) {
        this.photosHorizontales = inputData.getPhotosHorizontales();
        this.photosVerticales = inputData.getPhotosVerticales();
        //this.verticalPhotoTree = verticalPhotoTree;

        this.slideTree = new HashMap<>();
    }

    public List<Slide> walk(){
        this.buildTree();
        List<Slide> slideshow = new ArrayList<>();

        String nodeId = slideTree.keySet().iterator().next();
        boolean cont = true;
        while( cont ) {
            Node<Slide> n = slideTree.get(nodeId);
            slideshow.add(n.element);

            int bestScore = 0;
            Node<Slide> bestNode = null;
            for (Map.Entry<String, Integer> neighbor : n.neighborsWeight.entrySet()) {
                Node<Slide> nn = slideTree.get(neighbor.getKey());
                if (!nn.flag && neighbor.getValue() > bestScore) {
                    bestScore = neighbor.getValue();
                    bestNode = nn;
                }
            }

            if (bestScore > 0) {
                n.flag = true;
                nodeId = bestNode.id;
            } else {
                cont = false;
            }
        }

        return slideshow;
    }

    public void buildTree() {
        Utility utility = Utility.getInstance();

        for (Photo photo : this.photosHorizontales) {
            // Créer le slide
            Slide newSlide = new Slide();
            newSlide.setId(photo.getId());
            newSlide.addTags(photo.getTags());

            // Créer le noeud
            Node<Slide> currentNode = new Node<>(newSlide.id, newSlide);

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
