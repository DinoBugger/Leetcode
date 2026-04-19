package backtracking;

import java.util.ArrayList;
import java.util.List;

public class ColorPassword {

  private static int count = 0;

  static void main() {
    String[] colors = {"RED", "GREEN", "YELLOW"};
    boolean[] used = new boolean[colors.length];
    List<String> current = new ArrayList<>();

    backtrack(colors, used, current);
    System.out.println("Total of ways: " + count);
  }

  // RED never left in the third slot
  private static void backtrack(String[] colors, boolean[] used, List<String> current) {
    // BASE CASE
    if (current.size() == colors.length) {
      count++;
      System.out.println(current);
      return;
    }

    // LOOP
    for (int i = 0; i < colors.length; i++) {
      if (!used[i]) {

        // CONSTRAINT
        if (current.size() == 2 && colors[i].equals("RED")) {
          continue;
        }

        used[i] = true;
        current.add(colors[i]);

        // Recursion
        backtrack(colors, used, current);

        //BACKTRACK
        current.removeLast();
        used[i] = false;


      }
    }
  }

}
