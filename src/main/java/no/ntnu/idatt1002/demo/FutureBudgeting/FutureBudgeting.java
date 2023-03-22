package no.ntnu.idatt1002.demo.FutureBudgeting;

import java.util.ArrayList;

public class FutureBudgeting {
  ArrayList<String> unusedCategories = new ArrayList<>();
  public FutureBudgeting() {

  }

  public void initiateUnused () {
    unusedCategories.add("Choose category...");
    unusedCategories.add("Housing");
    unusedCategories.add("Groceries");
    unusedCategories.add("Entertainment");
    unusedCategories.add("Recurring/Bills");
    unusedCategories.add("Other...");
  }

  public String getUnusedCategoriesByEnumeration(int n) {
    return unusedCategories.get(n);
  }
}
