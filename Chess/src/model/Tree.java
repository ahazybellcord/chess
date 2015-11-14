package model;

import java.awt.List;
import java.util.ArrayList;

public class Tree<T> {
    Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>();
        root.data = rootData;
        root.children = new ArrayList<Node<T>>();
    }

    public static class Node<T> {
        private T data;
        private Node<T> parent;
        ArrayList<Node<T>> children;
    }
}
