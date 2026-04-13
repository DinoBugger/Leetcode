# Implementation of Trie (Prefix Tree) - LeetCode 208

A Trie is a specialized tree-based data structure used to store an associative array where the keys are usually strings. It is highly efficient for prefix-based searches.

---

## 1. TrieNode Structure
In this implementation, we use a `List` of nodes and an `index` property to identify characters. This is more space-efficient than a fixed-size array when the character set is sparse.

```java
class TrieNode {
    int index; // Represents the character (c - 'a')
    List<TrieNode> children;
    boolean isEndOfWord;

    public TrieNode(int index) {
        this.index = index;
        this.children = new ArrayList<>();
        this.isEndOfWord = false;
    }
}