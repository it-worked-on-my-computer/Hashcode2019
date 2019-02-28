package it.gdgtorino.hashcode.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node<T> {

    public T element;
    
    public Map<String, Integer> neighborsWeight = new HashMap<>();

}
