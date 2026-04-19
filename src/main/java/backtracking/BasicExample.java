package backtracking;

import java.util.ArrayList;
import java.util.List;

public class BasicExample {
  // Perform brute force with Backtracking

  private final static String[] people = {"M1", "M2", "F1"};
  private static int counter = 0;

  static void main() {
    backtrack(people, new ArrayList<>(), new boolean[people.length]);
    System.out.println(counter);
  }


  private static void backtrack(String[] people, List<String> current, boolean[] used) {
    // BASE CASE
    if (people.length == current.size()) {
      counter++;
      System.out.println(current);
      return;
    }

    //LOOP
    for (int i = 0; i < people.length; i++) {
      if (!used[i]) { // Checkout if the seat is available
        used[i] = true;
        current.add(people[i]);

        backtrack(people, current, used);

        // BACKTRACK
        current.removeLast();
        used[i] = false;
      }
    }
  }
}
