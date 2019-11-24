package ua.edu.ucu.tries;

import java.util.ArrayList;
import ua.edu.ucu.collections.Queue;

public class RWayTrie implements Trie {
    private static int R = 256;
    private Node root;
    private int size;

    private static class Node
    {
        private Object val;
        private Node[] next = new Node[R];
    }

    public RWayTrie() {
        this.root = new Node();
    }

    private Node get(Node x, String key, int d)
    {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    private Node put(Node x, String key, Integer val, int d)
    {
        if (x == null) x = new Node();
        if (d == key.length()) { x.val = val; return x; }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }

    @Override
    public void add(Tuple t) {
        put(root, t.term, t.weight, 0);
        size++;
    }

    @Override
    public boolean contains(String word) {
        if (get(root, word, 0) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean delete(String word) {
        if (put(root, word, 0, 0).val.equals(0)) {
            size--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        ArrayList<String> words = new ArrayList<>();
        Node n = get(root, s, 0);
        wordWithPref(n, s, q);
        int size = q.size();
        for (int i = 0; i < size; i++) {
            words.add((String) q.dequeue());
        }
        return words;
    }

    private void wordWithPref(Node x, String pref, Queue q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pref);
        for (char c = 0; c < R; c++)
            wordWithPref(x.next[c], pref + c, q);
    }

    @Override
    public int size() {
        return size;
    }

}
