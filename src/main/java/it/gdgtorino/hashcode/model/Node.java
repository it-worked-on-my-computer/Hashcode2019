package it.gdgtorino.hashcode.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node<T> {

    public String id;
    public T element;
    public Map<String, Integer> neighborsWeight = new HashMap<>();
    public boolean flag = false;

    public Node(String id, T element) {
        this.id = id;
        this.element = element;
    }
}
