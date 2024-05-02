import java.util.Iterator;
import java.util.LinkedList;

public class BST<K extends Comparable<K>, V> implements Iterable<BST<K, V>.Pair> {
    private Node root;
    private int size;

    private class Node {
        K key;
        V value;
        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public BST() {
        root = null;
        size = 0;
    }

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, value);
        else if (cmp > 0) x.right = put(x.right, key, value);
        else x.value = value;
        return x;
    }

    public V get(K key) {
        Node x = get(root, key);
        if (x == null) return null;
        return x.value;
    }

    private Node get(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            size--;
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Pair> iterator() {
        LinkedList<Pair> items = new LinkedList<>();
        inOrder(root, items);
        return items.iterator();
    }

    private void inOrder(Node x, LinkedList<Pair> items) {
        if (x == null) return;
        inOrder(x.left, items);
        items.add(new Pair(x.key, x.value));
        inOrder(x.right, items);
    }

    public class Pair {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "key is " + key + " and value is " + value;
        }
    }
    public static void main(String[] args) {
        BST<Integer, String> tree = new BST<>();
        tree.put(3, "Three");
        tree.put(1, "One");
        tree.put(4, "Four");

        for (var elem : tree) {
            System.out.println(elem);
        }
    }
}
