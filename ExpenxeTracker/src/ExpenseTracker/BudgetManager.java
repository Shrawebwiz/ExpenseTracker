package ExpenseTracker;

import java.io.*;

public class BudgetManager {
    private double monthlyBudget;
    private final String budgetFile = "budget.txt";

    public BudgetManager() {
        loadBudget();
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(double budget) {
        this.monthlyBudget = budget;
        saveBudget();
    }

    private void saveBudget() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(budgetFile))) {
            writer.write(Double.toString(monthlyBudget));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBudget() {
        File file = new File(budgetFile);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null) {
                    monthlyBudget = Double.parseDouble(line);
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            monthlyBudget = 0.0; // default
        }
    }
}
