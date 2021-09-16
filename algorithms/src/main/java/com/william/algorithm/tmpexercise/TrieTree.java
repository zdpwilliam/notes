package com.william.algorithm.tmpexercise;

import java.util.HashMap;

/**
 * @Package com.william.algorithm.tmpexercise
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/4/22 13:05
 * @Version V1.0
 */
public class TrieTree {


    public static void main(String[] args) {
        Trie trie = new Trie();
        String[] data = new String[]{"Trie", "insert", "search", "search", "startsWith", "insert", "search"};
        for (int i = 0; i < data.length; i++) {
            trie.insert(data[i]);
        }
        System.out.println(trie.search("search"));
    }

    static class Trie {
        private Trie[] children;
        private boolean isEnd;

        /** Initialize your data structure here. */
        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
            Trie node = this;
            for(int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch - 'a';
                if(node.children[index] == null) {
                    node.children[index] = new Trie();
                }
                node = node.children[index];
            }
            node.isEnd = true;
        }

        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            boolean existed = false;
            for(int i = 0; i < word.length(); i++) {
                if (existed) {
//                    trieMap.get(word.charAt(i));
                }
                existed = false;
            }
            return existed;
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            return false;
        }
    }

}
