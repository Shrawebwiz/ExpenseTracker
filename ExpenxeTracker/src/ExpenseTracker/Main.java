package ExpenseTracker;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Ensure the UI uses the system look for a modern feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            ExpenseTrackerUI trackerUI = new ExpenseTrackerUI();
            trackerUI.setVisible(true);
        });
    }
}
