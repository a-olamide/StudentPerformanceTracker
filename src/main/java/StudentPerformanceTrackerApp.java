import ui.LoginForm;
import util.DataSeeder;

import javax.swing.*;

public class StudentPerformanceTrackerApp {
    public static void main(String[] args) {
        DataSeeder.seed();
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }
}
