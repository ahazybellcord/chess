package model;

import java.awt.List;
import java.util.ArrayList;

public class Tree<T> {
    private Node<T> root;

    public Tree(T rootData) {
        setRoot(new Node<T>());
        getRoot().data = rootData;
        getRoot().setChildren(new ArrayList<Node<T>>());
    }

    public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public static class Node<T> {
        private T data;
        private Node<T> parent;
        private ArrayList<Node<T>> children;
		public ArrayList<Node<T>> getChildren() {
			return children;
		}
		public void setChildren(ArrayList<Node<T>> children) {
			this.children = children;
		}
    }
}
