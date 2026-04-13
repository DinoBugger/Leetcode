package prob208;

public class Prob208Recursion {
  class TrieNode{
    int index;
    TrieNode[] children=new TrieNode[26];
    boolean isEndOfWord=false;
  }

  class Trie{
    TrieNode root;
    public Trie(){
      root=new TrieNode();
    }

    public void insert(String word){
      insert(word, root, 0);
    }

    public boolean search(String word){
      return search(word, root, 0);
    }

    public boolean startsWith(String prefix){
      return startsWith(prefix, root, 0);
    }

    private void insert(String word, TrieNode cur, int index){
      if(word.length()==index ){
        cur.isEndOfWord=true;
        return;
      }

      int position = word.charAt(index) -'a';
      if(cur.children[position]==null){
        cur.children[position]=new TrieNode();
      }

      insert(word, cur.children[position], index+1);
    }

    private boolean search(String word, TrieNode cur, int index){
      if(word.length()==index){
        return cur.isEndOfWord;
      }

      int position = word.charAt(index) -'a';
      if(cur.children[position]==null){
        return false;
      }

      return search(word, cur.children[position], index+1);

    }

    private boolean startsWith(String word, TrieNode cur, int index){
      if(word.length()==index){
        return true;
      }

      int position = word.charAt(index) -'a';
      if(cur.children[position]==null){
        return false;
      }

      return startsWith(word, cur.children[position], index+1);

    }

  }
}
