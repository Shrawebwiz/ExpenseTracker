package ExpenseTracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ExpenseTrackerUI extends JFrame {
    private ExpenseManager manager;
    private BudgetManager budgetManager;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel totalLabel;
    private JLabel budgetStatusLabel;

    private JTextField dateField;
    private JTextField amountField;
    private JTextField categoryField;
    private JTextField descriptionField;

    public ExpenseTrackerUI() {
        manager = new ExpenseManager();
        budgetManager = new BudgetManager();

        setTitle("Expense Tracker 💸");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Date", "Amount", "Category", "Description"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        dateField = new JTextField();
        amountField = new JTextField();
        categoryField = new JTextField();
        descriptionField = new JTextField();

        formPanel.add(new JLabel("Date (YYYY-MM-DD)"));
        formPanel.add(new JLabel("Amount"));
        formPanel.add(new JLabel("Category"));
        formPanel.add(new JLabel("Description"));
        formPanel.add(new JLabel()); // empty

        formPanel.add(dateField);
        formPanel.add(amountField);
        formPanel.add(categoryField);
        formPanel.add(descriptionField);

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> addExpense());
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        totalLabel = new JLabel();
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        budgetStatusLabel = new JLabel();
        budgetStatusLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        bottomPanel.add(budgetStatusLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton deleteButton = new JButton("Delete Selected Expense");
        deleteButton.addActionListener(e -> deleteSelectedExpense());
        buttonPanel.add(deleteButton);

        JButton setBudgetButton = new JButton("Set Budget");
        setBudgetButton.addActionListener(e -> setBudget());
        buttonPanel.add(setBudgetButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable(); // now safe, labels initialized
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Expense> expenses = manager.getAllExpenses();
        for (Expense e : expenses) {
            tableModel.addRow(new Object[]{
                    e.getId(),
                    e.getDate(),
                    e.getAmount(),
                    e.getCategory(),
                    e.getDescription()
            });
        }
        updateTotalLabel();
        updateBudgetStatusLabel();
    }

    private void updateTotalLabel() {
        totalLabel.setText("Total Spending: ₹" + manager.getTotalSpending());
    }

    private void updateBudgetStatusLabel() {
        double budget = budgetManager.getMonthlyBudget();
        double spent = manager.getTotalSpending();
        double remaining = budget - spent;
        budgetStatusLabel.setText("Budget: ₹" + budget + " | Remaining: ₹" + (remaining >= 0 ? remaining : 0));
    }

    private void addExpense() {
        try {
            String date = dateField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());
            String category = categoryField.getText().trim();
            String description = descriptionField.getText().trim();

            if (date.isEmpty() || category.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Expense expense = new Expense(date, amount, category, description);
            manager.addExpense(expense);
            refreshTable();

            dateField.setText("");
            amountField.setText("");
            categoryField.setText("");
            descriptionField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedExpense() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int expenseId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this expense?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                manager.deleteExpense(expenseId);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void setBudget() {
        String input = JOptionPane.showInputDialog(this, "Enter monthly budget (₹):", "Set Budget", JOptionPane.PLAIN_MESSAGE);
        if (input != null && !input.trim().isEmpty()) {
            try {
                double newBudget = Double.parseDouble(input.trim());
                if (newBudget < 0) {
                    JOptionPane.showMessageDialog(this, "Budget cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                budgetManager.setMonthlyBudget(newBudget);
                updateBudgetStatusLabel();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for the budget.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseTrackerUI().setVisible(true);
        });
    }
}
