package prob1804;

public class Prob1804Recursive {
  class TrieNode{
    int index;
    TrieNode[] children =new TrieNode[26];
    boolean isEndOfWord;
    int count;
  }

  class Trie{
    TrieNode root;
    void insert(String word){

    }

    void insert(String word, TrieNode cur, int index){
      if(word.length()==index){
        if (!cur.isEndOfWord) {
          cur.isEndOfWord = true;
        }
        cur.count++;
        return;
      }
      int position = word.charAt(index) - 'a';
      if(cur.children[position]==null) {
        cur.children[position] = new TrieNode();
      }

      insert(word, cur.children[position], index+1);
    }

    int countWordsEqualTo(String word, TrieNode cur, int index){
      if(word.length()==index){
        return !cur.isEndOfWord ? 0: cur.count;
      }
      int position = word.charAt(index) - 'a';
      if(cur.children[position]==null) {
        return 0;
      }

      return countWordsEqualTo(word, cur.children[position], index+1);

    }

    int countWordsStartingWith(String prefix, TrieNode cur, int index){
      if(prefix.length()==index){
        return cur.children.length;
      }
      int position = prefix.charAt(index) - 'a';
      if(cur.children[position]==null) {
        return 0;
      }

      return countWordsStartingWith(prefix, cur.children[position], index+1);

    }

    private void erase(String word, TrieNode cur, int index){
      if(index==word.length()){
        if(cur.isEndOfWord){
          cur.isEndOfWord=false;
        }
        return;
      }

      char currentChar = word.charAt(index);




    }









  }

}
