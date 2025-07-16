package ExpenseTracker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages CRUD operations and file handling for expenses.
 */
public class ExpenseManager {
    private List<Expense> expenses;
    private final String fileName = "expenses.txt";

    // Constructor
    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpensesFromFile();
    }

    /**
     * Add a new expense.
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpensesToFile();
    }

    /**
     * Edit an existing expense by ID.
     */
    public boolean editExpense(int id, String date, double amount, String category, String description) {
        for (Expense e : expenses) {
            if (e.getId() == id) {
                e.setDate(date);
                e.setAmount(amount);
                e.setCategory(category);
                e.setDescription(description);
                saveExpensesToFile();
                return true;
            }
        }
        return false;
    }

    /**
     * Delete an expense by ID.
     */
    public boolean deleteExpense(int id) {
        for (Expense e : expenses) {
            if (e.getId() == id) {
                expenses.remove(e);
                saveExpensesToFile();
                return true;
            }
        }
        return false;
    }

    /**
     * Get all expenses.
     */
    public List<Expense> getAllExpenses() {
        return expenses;
    }

    /**
     * Calculate total spending.
     */
    public double getTotalSpending() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    /**
     * Save expenses to file in CSV format.
     */
    private void saveExpensesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Expense e : expenses) {
                writer.write(e.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    /**
     * Load expenses from the file.
     */
    private void loadExpensesFromFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String date = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    String category = parts[3];
                    String description = parts[4];
                    expenses.add(new Expense(id, date, amount, category, description));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}
