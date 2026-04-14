package prob1804;

import java.util.Stack;

public class Prob1804UniqueTrie {


  public static void main(String[] args) {
    Trie trie = new Trie();

    System.out.println("=== TEST 1: Insert Unique Only ===");
    trie.insert("apple");
    trie.insert("apple");
    trie.insert("app");

    System.out.println("search('apple'): " + trie.search("apple"));

    System.out.println("countWordsEqualTo('apple'): " + trie.countWordsEqualTo("apple"));

    System.out.println(
        "countWordsStartingWith('ap'): " + trie.countWordsStartingWith("ap"));

    System.out.println("\n=== TEST 2: Erase Physical Node (Case: Shared Prefix) ===");

    trie.erase("app");
    System.out.println("Sau khi xóa 'app':");
    System.out.println("search('app'): " + trie.search("app"));
    System.out.println("search('apple'): " + trie.search("apple"));
    System.out.println("countWordsStartingWith('ap'): " + trie.countWordsStartingWith("ap"));

    System.out.println("\n=== TEST 3: Erase Physical Node (Case: Branching & Pruning) ===");
    trie.insert("apt");
    System.out.println(
        "Trước khi xóa 'apt': countWordsStartingWith('ap'): " + trie.countWordsStartingWith(
            "ap"));

    trie.erase("apt");
    System.out.println("Sau khi xóa 'apt':");
    System.out.println("search('apt'): " + trie.search("apt"));
    System.out.println("search('apple'): " + trie.search("apple"));
    System.out.println("countWordsStartingWith('ap'): " + trie.countWordsStartingWith("ap"));

    System.out.println("\n=== TEST 4: Erase Unique Word (Clean Sweep) ===");
    Trie trie2 = new Trie();
    trie2.insert("banana");
    System.out.println("Trước khi xóa: search('banana'): " + trie2.search("banana"));

    trie2.erase("banana");
    System.out.println("Sau khi xóa 'banana':");
    System.out.println("search('banana'): " + trie2.search("banana"));
    System.out.println("countWordsStartingWith('b'): " + trie2.countWordsStartingWith("b"));
  }

  static class TrieNode {

    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord = false;
    int prefixCount = 0;
  }

  static class Trie {

    TrieNode root;

    public Trie() {
      root = new TrieNode();
    }


    public void insert(String word) {
      if (word == null || word.isEmpty()) {
        return;
      }

      TrieNode cur = root;
      boolean isNewBranch = false;

      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (cur.children[index] == null) {
          cur.children[index] = new TrieNode();
          isNewBranch = true;
        }
        cur = cur.children[index];


      }

      if (cur.isEndOfWord) {

        return;
      }

    }
    
    public void insertOptimized(String word) {
      if (word == null || word.isEmpty()) {
        return;
      }

      if (search(word)) {
        return;
      }

      TrieNode cur = root;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (cur.children[index] == null) {
          cur.children[index] = new TrieNode();
        }
        cur = cur.children[index];
        cur.prefixCount++;
      }
      cur.isEndOfWord = true;
    }


    public int countWordsEqualTo(String word) {
      return search(word) ? 1 : 0;
    }


    public int countWordsStartingWith(String prefix) {
      if (prefix == null || prefix.isEmpty()) {
        return 0;
      }

      TrieNode cur = root;
      for (char c : prefix.toCharArray()) {
        int index = c - 'a';
        if (cur.children[index] == null) {
          return 0;
        }
        cur = cur.children[index];
      }
      return cur.prefixCount;
    }


    public void erase(String word) {
      if (word == null || word.isEmpty()) {
        return;
      }
      if (!search(word)) {
        return;
      }

      TrieNode cur = root;
      Stack<TrieNode> pathStack = new Stack<>();
      pathStack.push(root);

      for (char c : word.toCharArray()) {
        int index = c - 'a';
        cur = cur.children[index];
        pathStack.push(cur);
      }

      cur.isEndOfWord = false;

      while (!pathStack.isEmpty()) {
        TrieNode node = pathStack.pop();

        node.prefixCount--;
        // Coditions of deleting
        // Not is the end of any word
        // Not have any child
        // node is not root
        if (!node.isEndOfWord && !hasChildren(node) && node != root) {

          TrieNode parent = pathStack.peek();

          for (int i = 0; i < 26; i++) {
            // find the node that need to be deleted
            if (parent.children[i] == node) {
              parent.children[i] = null;
              break;
            }
          }

        } else {
          break;
        }
      }
    }

    private boolean hasChildren(TrieNode node) {
      for (TrieNode child : node.children) {
        if (child != null) {
          return true;
        }
      }
      return false;
    }


    public boolean search(String word) {
      if (word == null || word.isEmpty()) {
        return false;
      }
      TrieNode cur = root;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (cur.children[index] == null) {
          return false;
        }
        cur = cur.children[index];
      }
      return cur.isEndOfWord;
    }
  }
}