package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

import model.Subject;
import service.SubjectService;

public class SubjectForm extends JFrame {
    private final SubjectService subjectService = new SubjectService();

    private JTable tblSubjects;
    private DefaultTableModel tableModel;

    private JTextField txtSubjectName;
    private JButton btnAdd, btnUpdate, btnDelete;

    public SubjectForm() {
        setTitle("Manage Subjects");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadSubjectData();
    }

    private void initComponents() {
        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Subject Name"}, 0);
        tblSubjects = new JTable(tableModel);
        tblSubjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblSubjects.getSelectionModel().addListSelectionListener(e -> populateForm());

        JScrollPane scrollPane = new JScrollPane(tblSubjects);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.add(new JLabel("Subject Name:"));
        txtSubjectName = new JTextField();
        formPanel.add(txtSubjectName);

        // Buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        btnAdd.addActionListener(e -> addSubject());
        btnUpdate.addActionListener(e -> updateSubject());
        btnDelete.addActionListener(e -> deleteSubject());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        // Layout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadSubjectData() {
        tableModel.setRowCount(0);
        List<Subject> subjects = subjectService.getAllSubjects();
        // Use Stream API to populate table
        subjects.stream().forEach(s -> tableModel.addRow(new Object[]{s.getId(), s.getName()}));
    }

    private void populateForm() {
        int row = tblSubjects.getSelectedRow();
        if (row >= 0) {
            txtSubjectName.setText(tableModel.getValueAt(row, 1).toString());
        }
    }

    private void addSubject() {
        try {
            Subject s = new Subject();
            s.setName(txtSubjectName.getText());
            subjectService.addSubject(s);
            JOptionPane.showMessageDialog(this, "Subject added.");
            loadSubjectData();
            txtSubjectName.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateSubject() {
        int row = tblSubjects.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a subject to update.");
            return;
        }

        try {
            int id = (int) tableModel.getValueAt(row, 0);
            Subject s = new Subject();
            s.setId(id);
            s.setName(txtSubjectName.getText());
            subjectService.updateSubject(s);
            JOptionPane.showMessageDialog(this, "Subject updated.");
            loadSubjectData();
            txtSubjectName.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteSubject() {
        int row = tblSubjects.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a subject to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            subjectService.deleteSubject(id);
            JOptionPane.showMessageDialog(this, "Subject deleted.");
            loadSubjectData();
            txtSubjectName.setText("");
        }
    }
}
