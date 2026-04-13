package prob208;

import java.util.ArrayList;
import java.util.List;

public class Prob208List {
  class TrieNode{
    int index;
    List<TrieNode> children;
    boolean isEndOfWord=false;

    public TrieNode(){
      children=new ArrayList<>();
    }

    public TrieNode(int index){
      this.index=index;
      children=new ArrayList<>();
    }
  }

  class Trie{
    TrieNode root;
    public Trie(){
      root=new TrieNode();
    }

    TrieNode searchForIndex(int index, List<TrieNode> nodes){
      return nodes.stream().filter(node -> node.index==index).findFirst().orElse(null);
    }
    public void insert(String word){
      TrieNode cur=root;
      for (int i=0;i<word.length();i++){
        int index= word.charAt(i)-'a';
        TrieNode foundNode= searchForIndex(index, cur.children);
        ;       if(foundNode==null){
          foundNode=new TrieNode(index);
          cur.children.add(foundNode);
        }
        cur=foundNode;
      }
      cur.isEndOfWord=true;
    }

    public boolean search(String word){
      TrieNode cur=root;
      for(int i=0;i<word.length();i++){
        int index=word.charAt(i)-'a';
        TrieNode foundNode= searchForIndex(index, cur.children);
        if(foundNode==null){
          return false;
        }
        cur=foundNode;
      }
      return cur.isEndOfWord;
    }

    public boolean startsWith(String prefix){
      TrieNode cur=root;
      for(int i=0;i<prefix.length();i++){
        int index=prefix.charAt(i)-'a';
        TrieNode foundNode= searchForIndex(index, cur.children);
        if(foundNode==null){
          return false;
        }
        cur=foundNode;
      }
      return true;
    }
  }
}
