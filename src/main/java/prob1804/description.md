# 1804. Implement Trie II (Prefix Tree)

**Difficulty:** Medium

---

## Problem Description

A **trie** (pronounced as "try") or **prefix tree** is a tree data structure used to efficiently store and retrieve keys in a dataset of strings.

Implement the `Trie` class:

* `Trie()` Initializes the trie object.
* `void insert(String word)` Inserts the string `word` into the trie.
* `int countWordsEqualTo(String word)` Returns the number of instances of the string `word` in the trie.
* `int countWordsStartingWith(String prefix)` Returns the number of strings in the trie that have the string `prefix` as a prefix.
* `void erase(String word)` Erases the string `word` from the trie.

---

## Examples

**Example 1:**

**Input**
`["Trie", "insert", "insert", "countWordsEqualTo", "countWordsStartingWith", "erase", "countWordsEqualTo", "countWordsStartingWith", "erase", "countWordsStartingWith"]`
`[[], ["apple"], ["apple"], ["apple"], ["app"], ["apple"], ["apple"], ["app"], ["apple"], ["app"]]`

**Output**
`[null, null, null, 2, 2, null, 1, 1, null, 0]`

**Explanation**
```text
Trie trie = new Trie();
trie.insert("apple");               // Inserts "apple".
trie.insert("apple");               // Inserts another "apple".
trie.countWordsEqualTo("apple");    // There are two instances of "apple", return 2.
trie.countWordsStartingWith("app"); // "app" is a prefix of "apple" and "apple", return 2.
trie.erase("apple");                // Erases one "apple".
trie.countWordsEqualTo("apple");    // Now there is only one instance of "apple", return 1.
trie.countWordsStartingWith("app"); // return 1.
trie.erase("apple");                // Erases "apple". Now the trie is empty.
trie.countWordsStartingWith("app"); // return 0.