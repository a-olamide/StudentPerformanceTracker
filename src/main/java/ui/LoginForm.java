package ui;

import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    private final UserService userService = new UserService();

    public LoginForm() {
        setTitle("EduTrack - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> attemptLogin());
        panel.add(new JLabel()); // spacer
        panel.add(btnLogin);

        add(panel);
    }

    private void attemptLogin() {
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();

        boolean valid = userService.login(username, password);
        if (valid) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose(); // Close login window
            new Dashboard(username).setVisible(true); // Open main dashboard
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }
}
