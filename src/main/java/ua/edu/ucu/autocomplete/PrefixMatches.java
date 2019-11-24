package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author andrii
 */
public class PrefixMatches {
    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int counter = 0;
        for (String s : strings) {
            String[] strarr = s.split("\\s+");
            for (String el : strarr) {
                int len = el.length();
                if (len > 2) {
                    trie.add(new Tuple(el, len));
                    counter++;
                }
            }
        }
        return counter;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        ArrayList<String> resultWords = new ArrayList<>();
        int counter = k;
        int len = 0;
        if (pref.length() >= 3) {
            len = pref.length();
        } else if (len == 2) {
            len = 3;
        } else {
            return null;
        }
        ArrayList<String> words = new ArrayList<>();
        for (String word : trie.wordsWithPrefix(pref)) {
            words.add(word);
        }
        Collections.sort(words, Comparator.comparingInt(String::length));
        for (String word : words) {
            if (counter == 0) {
                break;
            } else if (word.length() == len) {
                resultWords.add(word);
            } else {
                counter--;
                len = word.length();
                if (counter != 0) {
                    resultWords.add(word);
                }
            }
        }
        return resultWords;
    }

    public int size() {
        return trie.size();
    }

    public static void main(String[] args) {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");

        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        System.out.println(result);
    }
}
