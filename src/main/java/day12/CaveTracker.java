package day12;

import util.IterativeSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class CaveTracker implements IterativeSolver {
    public static final String START = "start";
    public static final String END = "end";
    public static final String DELIMTER = "-";
    private final List<Tree.Node<String>> nodes = new ArrayList<>();
    private final Map<String, String> links = new HashMap<>();
    private Tree<String> treeNode;

    @Override
    public void iterate(String line) {
        final String[] split = line.split(DELIMTER);
//        if (START.equals(split[0])) {
//            final Tree.Node<String> node1 = new Tree.Node<>(split[1]);
//            if (treeNode == null) {
//                treeNode = new Tree<>(split[0]);
//            }
//            treeNode.root.children.add(node1);
//        } else if (START.equals(split[1])) {
//            final Tree.Node<String> node1 = new Tree.Node<>(split[0]);
//            if (treeNode == null) {
//                treeNode = new Tree<>(split[1]);
//            }
//            treeNode.root.children.add(node1);
//        } else {
//            final Tree.Node<String> node0 = new Tree.Node<>(split[0]);
//            final Tree.Node<String> node1 = new Tree.Node<>(split[1]);
//            node0.children.add(node1);
//            nodes.add(node0);
//        }
        links.putIfAbsent(split[0], split[1]);
    }

    public void buildTree() {
//        var parentNodes = Collections.singletonList(treeNode.root);
//        while (!nodes.isEmpty()) {
//            parentNodes = parentNodes.stream().map(pn -> {
//                pn.children.forEach(this::linkNodeToChild);
//                return pn.children;
//            }).flatMap(Collection::stream).distinct().toList();
//        }
        var paths = new ArrayList<>();
        links.forEach((key, value) -> {
            var path = new LinkedList<String>();
            path.add(key);
            path.add(value);

            String linkKey, linkValue;
            do {
                final String left = path.getFirst();
                linkKey = findLink(left);
                if (nonNull(linkKey)) path.addFirst(linkKey);

                final String right = path.getLast();
                linkValue = findLink(right);
                if (nonNull(linkValue)) path.addFirst(linkValue);
                if (path.contains(START) && path.contains(END)) {
                    break;
                }
            } while (nonNull(linkKey) || nonNull(linkValue));
            paths.add(path);
        });
        System.out.println(paths.size());
    }

    private String findLink(final String match) {
        final Optional<String> valueLink = links.entrySet().stream()
                .filter(k -> k.getKey().equals(match))
                .map(Map.Entry::getValue)
                .findFirst();
        if (valueLink.isPresent()) return valueLink.get();
        final Optional<String> keyLink = links.entrySet().stream()
                .filter(k -> k.getValue().equals(match))
                .map(Map.Entry::getKey)
                .findFirst();
        return keyLink.orElse(null);
    }

    private void linkNodeToChild(Tree.Node<String> child) {
        if (!child.children.isEmpty()) {
            return;
        }
        final List<Tree.Node<String>> foundNodes = this.nodes.stream().filter(n -> n.data.equals(child.data)).distinct().toList();
        child.children.addAll(foundNodes);
        nodes.removeAll(foundNodes);
    }

    public int getTotalPaths() {
        var paths = getAllLeafNodes(treeNode.root);
        return paths.size() * 2;
    }

    public List<String> getAllLeafNodes(Tree.Node<String> node) {
        List<String> leafNodes = new ArrayList<>();
        if (node.children.isEmpty()) {
            leafNodes.add(node.data);
        } else {
            for (Tree.Node<String> child : node.children) {
                leafNodes.addAll(getAllLeafNodes(child));
            }
        }
        return leafNodes;
    }

    static class Tree<T> {
        private final Node<T> root;

        public Tree(T rootData) {
            root = new Node<>(rootData);
        }

        public static class Node<T> {
            private T data;
            private Node<T> parent;
            private List<Node<T>> children;

            public Node(T data) {
                this.data = data;
                this.children = new ArrayList<>();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Node<?> node = (Node<?>) o;
                return Objects.equals(data, node.data) && Objects.equals(parent, node.parent) && Objects.equals(children, node.children);
            }

            @Override
            public int hashCode() {
                return Objects.hash(data, parent, children);
            }
        }
    }
}
