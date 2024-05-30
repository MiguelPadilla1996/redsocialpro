/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author DE
 */

import java.util.LinkedList;
import java.util.List;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    public List<String> search(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return new LinkedList<>();
            }
        }
        return searchAllWords(current, prefix);
    }

    private List<String> searchAllWords(TrieNode node, String prefix) {
        List<String> result = new LinkedList<>();
        if (node.isEndOfWord) {
            result.add(prefix);
        }
        for (char ch : node.children.keySet()) {
            result.addAll(searchAllWords(node.children.get(ch), prefix + ch));
        }
        return result;
    }
}

