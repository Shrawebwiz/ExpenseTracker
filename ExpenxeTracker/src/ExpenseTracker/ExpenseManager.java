package ExpenseTracker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ExpenseManager {
    private List<Expense> expenses;
    private final String fileName = "expenses.txt";


    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpensesFromFile();
    }

   
    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpensesToFile();
    }

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

   
    public List<Expense> getAllExpenses() {
        return expenses;
    }

   
    public double getTotalSpending() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

   
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
