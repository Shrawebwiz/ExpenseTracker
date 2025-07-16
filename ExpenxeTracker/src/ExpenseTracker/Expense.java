package ExpenseTracker;

import java.io.Serializable;

/**
 * Expense class representing a single expense entry.
 * Uses Serializable if you want to add object-based file saving later.
 */
public class Expense implements Serializable {
    private static int counter = 1; // Auto-increment ID across objects

    private int id;
    private String date;         // Format: "YYYY-MM-DD"
    private double amount;
    private String category;     // Food, Travel, Shopping, etc.
    private String description;

    // Constructor
    public Expense(String date, double amount, String category, String description) {
        this.id = counter++;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    // Constructor with ID (used when loading from file)
    public Expense(int id, String date, double amount, String category, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    // Setters (used for editing expenses)
    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a CSV-friendly string for file saving:
     * id,date,amount,category,description
     */
    public String toCSV() {
        return id + "," + date + "," + amount + "," + category + "," + description;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
