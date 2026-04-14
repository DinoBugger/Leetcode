package prob1804;

public class Prob1804Optimized {

  // =================== MAIN TEST
  public static void main(String[] args) {
    Trie trie = new Trie();

    System.out.println("=== TEST 1: Basics & Duplicates ===");
    trie.insert("apple");
    trie.insert("apple"); // Insert twice
    trie.insert("app");

    System.out.println(
        "countWordsEqualTo('apple'): " + trie.countWordsEqualTo("apple")); // Expect: 2
    System.out.println("countWordsStartingWith('ap'): " + trie.countWordsStartingWith(
        "ap")); // Expect: 3 (2 apple + 1 app)

    trie.erase("apple"); // Delete once
    System.out.println("After deleting 'apple' once:");
    System.out.println(
        "countWordsEqualTo('apple'): " + trie.countWordsEqualTo("apple")); // Expect: 1
    System.out.println(
        "countWordsStartingWith('ap'): " + trie.countWordsStartingWith("ap")); // Expect: 2

    System.out.println("\n=== TEST 2: Clearing Branches (Optimization Check) ===");
    Trie trie2 = new Trie();
    trie2.insert("test");
    trie2.insert("testing");

    System.out.println("Before deleting 'testing':");
    System.out.println(
        "countWordsStartingWith('test'): " + trie2.countWordsStartingWith("test")); // 2

    trie2.erase("testing");
    System.out.println("After deleting 'testing':");
    System.out.println("countWordsEqualTo('testing'): " + trie2.countWordsEqualTo("testing")); // 0
    System.out.println("countWordsStartingWith('test'): " + trie2.countWordsStartingWith(
        "test")); // 1 (only 'test' remains)
    System.out.println("search('testing'): " + trie2.search("testing")); // false

    System.out.println("\n=== TEST 3: Deleting Unique Word (Immediate Pruning) ===");
    Trie trie3 = new Trie();
    trie3.insert("banana");
    System.out.println(
        "Before delete: countWordsStartingWith('b'): " + trie3.countWordsStartingWith("b")); // 1

    trie3.erase("banana");
    System.out.println("After deleting 'banana':");
    System.out.println("search('banana'): " + trie3.search("banana")); // false
    System.out.println("countWordsStartingWith('b'): " + trie3.countWordsStartingWith("b")); // 0
    // At this point, node 'b' should be null as it was pruned, leaving the tree nearly empty.
  }

  static class TrieNode {

    TrieNode[] children = new TrieNode[26];
    int count = 0;       // Number of words end at this node
    int prefixCount = 0; // Number of words passing at this node
  }

  static class Trie {

    TrieNode root;

    public Trie() {
      root = new TrieNode();
    }

    // =================== INSERT
    // O(L)
    public void insert(String word) {
      if (word == null || word.isEmpty()) {
        return;
      }

      TrieNode cur = root;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (cur.children[index] == null) {
          cur.children[index] = new TrieNode();
        }
        cur = cur.children[index];
        cur.prefixCount++; // auto count when loop go through this node
      }
      cur.count++; // count when word end here
    }

    // =================== COUNT WORDS EQUAL TO
    // O(L)
    public int countWordsEqualTo(String word) {
      if (word == null || word.isEmpty()) {
        return 0;
      }

      TrieNode cur = root;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (cur.children[index] == null) {
          return 0;
        }
        cur = cur.children[index];
      }
      return cur.count;
    }

    // =================== COUNT WORDS STARTING WITH
    // O(L)
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

    // =================== ERASE
    // O(L)
    public void erase(String word) {
      if (word == null || word.isEmpty()) {
        return;
      }

      if (!search(word)) {
        return;
      }

      TrieNode cur = root;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        // get the next child
        TrieNode next = cur.children[index];
        // minus 1 and check of
        next.prefixCount--;

        if (next.prefixCount == 0) {
          cur.children[index] = null;
          return;
        }

        cur = next;
      }
      cur.count--;
    }

    // =================== SEARCH
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
      return cur.count > 0;
    }
  }
}