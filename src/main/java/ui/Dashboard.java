package ui;

import factory.ViewFactory;
import factory.ViewType;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private final String username;

    public Dashboard(String username) {
        this.username = username;
        setTitle("EduTrack - Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnStudents = new JButton("Manage Students");
        JButton btnSubjects = new JButton("Manage Subjects");
        JButton btnGrades = new JButton("Record Grades");
        JButton btnReports = new JButton("Export Reports");

        btnStudents.addActionListener(e -> ViewFactory.createView(ViewType.STUDENT).setVisible(true));
        btnSubjects.addActionListener(e -> ViewFactory.createView(ViewType.SUBJECT).setVisible(true));
        btnGrades.addActionListener(e -> ViewFactory.createView(ViewType.GRADE).setVisible(true));
        btnReports.addActionListener(e -> ViewFactory.createView(ViewType.REPORT).setVisible(true));

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.add(welcomeLabel);
        panel.add(btnStudents);
        panel.add(btnSubjects);
        panel.add(btnGrades);
        panel.add(btnReports);

        add(panel, BorderLayout.CENTER);
    }
}
