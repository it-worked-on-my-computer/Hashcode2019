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
    Integer[][] graph;

    public SlideTreeWorker(InputData inputData /*, Map<Integer, Node> verticalPhotoTree */ ) {
        this.photosHorizontales = inputData.getPhotosHorizontales();
        this.photosVerticales = inputData.getPhotosVerticales();
        //this.verticalPhotoTree = verticalPhotoTree;

        //this.slideTree = new HashMap<>();
        this.graph = new Integer[this.photosHorizontales.size()][this.photosHorizontales.size()];
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

        System.out.println( "Slideshow score : " + Utility.getInstance().getScore( slideshow ) );
        return slideshow;
    }

    public List<String> walk2(){
        this.buildTree();
        List<String> slideshow = new ArrayList<>();

        int id = 0;
        while( true ){
            slideshow.add( this.photosHorizontales.get( id ).getId() );

            int bestScore = 0;
            int bestId = -1;
            for( int i = 0; i < this.photosHorizontales.size(); i++ ){
                if( i != id && this.graph[id][i] > bestScore ){
                    bestScore = this.graph[id][i];
                    bestId = i;
                }
            }

            if( bestScore > 0 ){
                for( int j = 0; j < this.photosHorizontales.size(); j++ ){
                    this.graph[j][bestId] = -1;
                }
                id = bestId;
            } else {
                break;
            }
        }

        return slideshow;
    }


    public void buildTree() {
        Utility utility = Utility.getInstance();

        for( int i = 0; i < this.photosHorizontales.size() ; i++ ){
            Photo photo = this.photosHorizontales.get(i);

            // Créer le slide
            /*
            Slide newSlide = new Slide();
            newSlide.setId(photo.getId());
            newSlide.addTags(photo.getTags());
            */

            for( int j = 0; j < this.photosHorizontales.size(); j++ ){
                if( i != j ){
                    this.graph[i][j] = utility.calculateScore2( photo.getTags(), this.photosHorizontales.get(j).getTags() );
                }
            }
            // Créer le noeud
            //Node<Slide> currentNode = new Node<>(newSlide.id, newSlide);

            // Itérer sur les noeud existantd
            /*
            for (Node<Slide> node : slideTree.values()) {
                // calculer le score avec le slide créé précédemment
                int score = utility.calculateScore(currentNode.element, node.element);

                // ajouter l'arêt aux 2 noeuds
                currentNode.neighborsWeight.put(node.id, score);
                node.neighborsWeight.put(currentNode.id, score);
            }

            // ajouter le noeud courant au graphe
            this.slideTree.put(currentNode.id, currentNode);
            */
        }
    }

}
