package day12;

import util.IterativeSolver;

import java.util.ArrayList;
import java.util.List;

public class CaveTracker implements IterativeSolver {
    public static final String START = "start";
    public static final String DELIMTER = "-";
    private final List<Tree.Node<String>> nodes = new ArrayList<>();
    private Tree<String> treeNode;

    @Override
    public void iterate(String line) {
        final String[] split = line.split(DELIMTER);
        if (START.equals(split[0])) {
            final Tree.Node<String> node1 = new Tree.Node<>(split[1]);
            if (treeNode == null) {
                treeNode = new Tree<>(split[0]);
            }
            treeNode.root.children.add(node1);
        } else {
            final Tree.Node<String> node0 = new Tree.Node<>(split[0]);
            final Tree.Node<String> node1 = new Tree.Node<>(split[1]);
            node0.children.add(node1);
            nodes.add(node0);
        }
    }

    public void buildTree() {
        treeNode.root.children.forEach(child -> nodes.stream()
                .filter(n -> n.data.equals(child.data))
                .forEach(n->child.children.add(n)));
    }

    public int getTotalPaths() {
        return nodes.size();
    }

    static class Tree<T> {
        private final Node<T> root;

        public Tree(T rootData) {
            root = new Node<T>(rootData);
        }

        public static class Node<T> {
            private T data;
            private Node<T> parent;
            private List<Node<T>> children;

            public Node(T data) {
                this.data = data;
                this.children = new ArrayList<>();
            }
        }
    }
}
