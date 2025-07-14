package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import models.Student;
import service.StudentService;

public class StudentForm extends JFrame {
    private final StudentService studentService = new StudentService();

    private JTable tblStudents;
    private DefaultTableModel tableModel;

    private JTextField txtFirstName, txtLastName, txtGender, txtDob;
    private JButton btnAdd, btnUpdate, btnDelete;

    public StudentForm() {
        setTitle("Manage Students");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadStudentData();
    }

    private void initComponents() {
        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "Gender", "DOB"}, 0);
        tblStudents = new JTable(tableModel);
        tblStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblStudents.getSelectionModel().addListSelectionListener(e -> populateForm());

        JScrollPane scrollPane = new JScrollPane(tblStudents);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtGender = new JTextField();
        txtDob = new JTextField(); // Format: yyyy-mm-dd

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(txtFirstName);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(txtLastName);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(txtGender);
        formPanel.add(new JLabel("Date of Birth (yyyy-mm-dd):"));
        formPanel.add(txtDob);

        // Buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.SOUTH);
    }

    private void loadStudentData() {
        tableModel.setRowCount(0); // Clear table
        List<Student> students = studentService.getAllStudents();
        // Use Stream API to populate table
        students.stream().forEach(s -> tableModel.addRow(new Object[]{
                s.getId(), s.getFirstName(), s.getLastName(), s.getGender(), s.getDob()
        }));
    }

    private void populateForm() {
        int row = tblStudents.getSelectedRow();
        if (row >= 0) {
            txtFirstName.setText(tableModel.getValueAt(row, 1).toString());
            txtLastName.setText(tableModel.getValueAt(row, 2).toString());
            txtGender.setText(tableModel.getValueAt(row, 3).toString());
            txtDob.setText(tableModel.getValueAt(row, 4).toString());
        }
    }

    private void addStudent() {
        try {
            Student s = new Student();
            s.setFirstName(txtFirstName.getText());
            s.setLastName(txtLastName.getText());
            s.setGender(txtGender.getText());
            s.setDob(LocalDate.parse(txtDob.getText()));
            s.setUserId(1); // default user ID or fetch dynamically

            studentService.addStudent(s);
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            loadStudentData();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateStudent() {
        int row = tblStudents.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a student to update.");
            return;
        }

        try {
            int id = (int) tableModel.getValueAt(row, 0);
            Student s = new Student();
            s.setId(id);
            s.setFirstName(txtFirstName.getText());
            s.setLastName(txtLastName.getText());
            s.setGender(txtGender.getText());
            s.setDob(LocalDate.parse(txtDob.getText()));
            s.setUserId(1); // default user ID

            studentService.updateStudent(s);
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            loadStudentData();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteStudent() {
        int row = tblStudents.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a student to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            studentService.deleteStudent(id);
            JOptionPane.showMessageDialog(this, "Student deleted.");
            loadStudentData();
            clearForm();
        }
    }

    private void clearForm() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtGender.setText("");
        txtDob.setText("");
    }
}
