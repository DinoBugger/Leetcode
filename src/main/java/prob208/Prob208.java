package prob208;

public class Prob208 {
  class TrieNode{
    TrieNode[] children= new TrieNode[26];
    boolean isEndOfWord =false;
  }
  class Trie {
    TrieNode root;

    public Trie() {
      root=new TrieNode();
    }

    public void insert(String word) {
      if(word==null || word.isEmpty()){
        return;
      }
      var cur=root;

      for(char c :word.toCharArray()){
        int index=c-'a';
        if(cur.children[index]==null){
          cur.children[index]=new TrieNode();
        }
        cur=cur.children[index];
      }
      cur.isEndOfWord=true;
    }

    public boolean search(String word) {
      if(word==null || word.isEmpty()){
        return false;
      }
      TrieNode cur= root;
      for(char c:word.toCharArray()){
        int index=c-'a';
        if(cur.children[index]==null){
          return false;
        }
        cur=cur.children[index];
      }
      return cur.isEndOfWord;

    }

    public boolean startsWith(String prefix) {
      if(prefix==null || prefix.isEmpty()){
        return false;
      }
      TrieNode cur= root;
      for(char c:prefix.toCharArray()){
        int index=c-'a';
        if(cur.children[index]==null){
          return false;
        }
        cur=cur.children[index];
      }
      return true;
    }
  }
/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
}
