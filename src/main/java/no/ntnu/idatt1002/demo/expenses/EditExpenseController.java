package no.ntnu.idatt1002.demo.expenses;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idatt1002.demo.data.*;
import no.ntnu.idatt1002.demo.exceptions.ConformityException;
import no.ntnu.idatt1002.demo.exceptions.DuplicateNameException;

/**
 * Controller for the AddExpense.fxml window.
 */

public class EditExpenseController implements Initializable {
  @FXML
  private GridPane gridPane;
  @FXML
  private DatePicker datePicker;
  @FXML
  private TextField expenseName;
  @FXML
  private TextField amount;
  @FXML
  private ComboBox categoryBox;
  @FXML
  private TextField notes;
  @FXML
  private Button addExpense;
  private Register categoryRegister;
  private List<String> categories;
  private List<String> retrievedCategories;

  public EditExpenseController() throws DuplicateNameException, IOException, URISyntaxException, ConformityException {
    categoryRegister = RegisterController.readData(getClass().getClassLoader().getResource("database/register.json"));
    retrievedCategories = categoryRegister.getCategoriesByTransactionType(true);
    categories = new ArrayList<>();
    for (String category : retrievedCategories) {
      if (categoryRegister.getCategoryByName(category).isRecurring()) {
        category += " (recurring)";
      }
      categories.add(category);
    }
  }

  public void addExpensePressed() throws ConformityException, IOException {
    if (!isAmountEmpty() && !isNameEmpty() && !isCategoryBoxEmpty() && !isDateNotChosen()) {
      String name = expenseName.getText();
      double expenseAmount = Double.parseDouble(amount.getText());
      String categoryFetched = (String) categoryBox.getValue();
      String categoryChosen = categoryFetched.split(" ")[0].strip();
      String expenseNotes = notes.getText();
      String date = datePicker.getValue().toString();
      boolean isRecurring = categoryRegister.getCategoryByName(categoryChosen).isRecurring();

      if (isRecurring) {
        String recurringDate = date.substring(8);
        System.out.println(recurringDate);
        RecurringExpense newExpense = new RecurringExpense(name, expenseNotes, recurringDate,expenseAmount);
        categoryRegister.addTransactionToCategory(newExpense, categoryChosen);
        // TODO make into actual file write when the time comes
        System.out.println(RegisterController.writeData(categoryRegister));
      } else {
        Expense expense = new Expense(name, expenseNotes, date, expenseAmount);
        categoryRegister.addTransactionToCategory(expense, categoryChosen);
        System.out.println(RegisterController.writeData(categoryRegister));
      }
      expenseName.clear();
      amount.clear();
      notes.clear();
      categoryBox.valueProperty().set(null);

    } else {
      if (isAmountEmpty()) {
        amount.setPromptText("AMOUNT HERE");
      }
      if (isNameEmpty()) {
        expenseName.setPromptText("EXPENSE NAME HERE");
      }
      if (isCategoryBoxEmpty()) {
        categoryBox.setPromptText("CHOOSE CATEGORY");
      }
      if (isDateNotChosen()) {
        datePicker.setPromptText("CHOSE DATE");
      }
    }
  }

  /**
   * Method for handling button that is used to go to the home page.
   *
   * @param event The ActionEvent that triggered the method
   * @throws IOException If SpendWiseHomePage.fxml is not found in resources
   */

  public void goHome(ActionEvent event) throws IOException {
    BorderPane rootGoHome = (FXMLLoader.load(Objects.requireNonNull(
        getClass().getResource("/SpendWiseHomePage.fxml"))));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(rootGoHome);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    categoryBox.getItems().addAll(categories);
  }

  private boolean isNameEmpty() {
    return ((expenseName.getText() == null) || (expenseName.getText().isEmpty()));
  }
  private boolean isAmountEmpty() {
    return ((amount.getText() == null) || (amount).getText().isEmpty());
  }
  private boolean isCategoryBoxEmpty() {
    return categoryBox.getSelectionModel().isEmpty();
  }
  private boolean isDateNotChosen() {
    return (datePicker.getValue() == null);
  }
}
