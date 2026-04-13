package prob208;

import java.util.HashMap;
import java.util.Map;

public class Prob208HashMap {
  class TrieNode{
    Map<Integer, TrieNode> children= new HashMap<>();
    boolean isEndOfWord=false;
  }

  class Trie{
    TrieNode root;
    public Trie(){
      root=new TrieNode();
    }

    public void insert(String word){
      TrieNode cur=root;
      for(char c:word.toCharArray()){
        int index= c-'a';
        if(!cur.children.containsKey(index)){
          cur.children.put(index, new TrieNode());
        }
        cur=cur.children.get(index);
      }
      cur.isEndOfWord=true;
    }

    public boolean search(String word){
      TrieNode cur=root;
      for(char c:word.toCharArray()){
        int index= c-'a';
        if(!cur.children.containsKey(index)){
          return false;
        }
        cur=cur.children.get(index);
      }
      return cur.isEndOfWord;
    }

    public boolean startsWith(String prefix){
      TrieNode cur=root;
      for(char c:prefix.toCharArray()){
        int index= c-'a';
        if(!cur.children.containsKey(index)){
          return false;
        }
        cur=cur.children.get(index);
      }
      return true;
    }
  }
}
