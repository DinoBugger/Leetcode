package prob1804;

import java.util.Stack;

public class Prob1804Loop {

  public static void main(String[] args) {
    Trie trie = new Trie();

    // Test data
    trie.insert("apple");
    trie.insert("app");
    trie.insert("apt");

    System.out.println("Before deletion:");
    System.out.println("search('app'): " + trie.search("app")); // true

    // Delete 'app' (Case 3: 'apple' still exists)
    trie.erase("app");
    System.out.println("\nAfter deleting 'app':");
    System.out.println("search('app'): " + trie.search("app")); // false
    System.out.println("search('apple'): " + trie.search("apple")); // true

    // Delete 'apt' (Case 4: Branching point)
    trie.erase("apt");
    System.out.println("\nAfter deleting 'apt':");
    System.out.println("search('apt'): " + trie.search("apt")); // false

    // Delete 'apple' (Case 2: Delete everything/clean up path)
    trie.erase("apple");
    System.out.println("\nAfter deleting 'apple':");
    System.out.println("search('apple'): " + trie.search("apple")); // false

    System.out.println("\n=== Testing Duplicates ===");
    trie.insert("test");
    trie.insert("test");
    System.out.println("countWordsEqualTo('test'): " + trie.countWordsEqualTo("test")); // 2
    trie.erase("test");
    System.out.println(
        "countWordsEqualTo('test') after 1 erase: " + trie.countWordsEqualTo("test")); // 1
  }

  static class TrieNode {

    char value;
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord = false;
    int count = 0;

    TrieNode() {

    }

    TrieNode(char value) {
      this.value = value;
    }
  }

  static class Trie {

    TrieNode root;

    public Trie() {
      root = new TrieNode();
    }

    // =================== INSERT
    public void insert(String word) {
      if (word == null || word.isEmpty()) {
        return;
      }

      TrieNode cur = root;

      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (cur.children[index] == null) {
          cur.children[index] = new TrieNode(c);
        }
        cur = cur.children[index];
      }
      cur.count++;
      cur.isEndOfWord = true;
    }

    // =================== countWordsEqualTo

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

      return cur.isEndOfWord ? cur.count : 0;

    }


    // =================== countWordsStartingWith
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

      return countSuffix(cur);
    }

    // This is when we know that the prefix is existed and has at least a word started with it
    // Now we should go deeper
    // countSuffix is in charge of deep searching for word in every branch

    public int countSuffix(TrieNode cur) {
      if (cur == null) {
        return 0;
      }
      // FIX: Phải cộng dồn biến count để xử lý từ trùng lặp (LeetCode 1804)
      int count = cur.count;

      for (TrieNode child : cur.children) {
        if (child != null) {
          count += countSuffix(child);
        }
      }

      return count;
    }

    // Helper function to check if a node has any non-null children
    private boolean hasChildren(TrieNode node) {
      if (node == null) {
        return false;
      }
      for (TrieNode child : node.children) {
        if (child != null) {
          return true;
        }
      }
      return false;
    }

    // =================== DELETE
    // I'm sure this is the most challenging part with several cases
    // Case 1: The word is not existed, and we do nothing
    // Case 2: Word is existed, and it is the direct child of root, so we need to remove it from root
    // Case 3: Word is existed, and it is a word on another word path, e.g: app is a word on application word path
    //         so we need to find a way that can remove "app" without affecting "application" and I think we should
    //         set isEndOfWord equal FALSE
    // Case 4: Word is existed, and it is partly on another word path, the other of the word is on another branch.
    //         "ape" and "application" -> "ap" on "application" path, however 'e' is on different branch
    // PROBLEM: This is quite complicated because,  you have to go to the deep of the word to know that the word is existed or not,
    //          after that base on case you have to erase like, in the above like in the second case you need to have a
    //          mark at root to delete the word from that
    // APPROACH: the more efficient choice is backtracking but, in this case I will follow loop-based approach
    // If using loop-base we need something to store path we have traveled
    // So I decided to use Stack a Data Structure with purpose alike recursion

    public void erase(String word) {
      if (word == null || word.isEmpty()) {
        return;
      }
      TrieNode cur = root;
      Stack<TrieNode> pathStack = new Stack<>();
      pathStack.push(root); // Push root to handle edge cases easily

      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (cur.children[index] == null) {
          return;
        }
        cur = cur.children[index];
        pathStack.push(cur);
      }

      // check case 1: if the word at the end is not a true word => we do nothing
      if (!cur.isEndOfWord || cur.count == 0) {
        return;
      }

      // Handle duplicate words first
      cur.count--;
      if (cur.count > 0) {
        // Still have duplicates, no need to remove nodes physically
        return;
      }

      // If count reaches 0, it's no longer a valid word end
      cur.isEndOfWord = false;

      // Now easy enough you can actually realize that from here if we use any search functions
      // we cannot find the word that has been removed above
      // but the thing is that, in case 1 and case 3 we need to delete redundant node

      // FIX: Changed condition from isEmpty() to !isEmpty() to actually run the loop
      while (!pathStack.isEmpty()) {
        TrieNode nodeToRemove = pathStack.pop(); // in first step, this node is the current one

        // Condition to erase a node
        // 1. It not has any child (FIX: Use helper method instead of checking length)
        // 2. It is not end of any word
        if (!hasChildren(nodeToRemove) && nodeToRemove.count == 0) {
          if (pathStack.isEmpty()) {
            // pathStack is empty, making sure that the nodeToRemove is the root
            // Which means that it is case 1 (or tree becomes empty)
            // We just clear root's children, don't nullify root itself
            for (int i = 0; i < 26; i++) {
              nodeToRemove.children[i] = null;
            }
            return;
          }
          // Unlike pop, peek just look at the top of stack but not take it out
          TrieNode parent = pathStack.peek();
          int index = nodeToRemove.value - 'a';
          parent.children[index] = null;
        } else {
          // Node is still useful (has children or is end of another word), stop removing upwards
          break;
        }

      }
    }

    public boolean search(String word) {
      TrieNode cur = root;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (cur.children[index] == null) {
          return false;
        }
        cur = cur.children[index];
      }
      return cur.isEndOfWord && cur.count > 0;
    }
  }


}