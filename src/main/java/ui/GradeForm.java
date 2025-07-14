package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Grade;
import model.Student;
import model.Subject;
import service.GradeService;
import service.StudentService;
import service.SubjectService;

public class GradeForm extends JFrame {
    private final GradeService gradeService = new GradeService();
    private final StudentService studentService = new StudentService();
    private final SubjectService subjectService = new SubjectService();

    private JComboBox<Student> cboStudents;
    private JComboBox<Subject> cboSubjects;
    private JTextField txtScore;
    private JTable tblGrades;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete;

    public GradeForm() {
        setTitle("Record Grades");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadStudents();
        loadSubjects();
    }

    private void initComponents() {
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        cboStudents = new JComboBox<>();
        cboStudents.addActionListener(e -> loadGrades());

        cboSubjects = new JComboBox<>();

        txtScore = new JTextField();

        formPanel.add(new JLabel("Select Student:"));
        formPanel.add(cboStudents);
        formPanel.add(new JLabel("Select Subject:"));
        formPanel.add(cboSubjects);
        formPanel.add(new JLabel("Score:"));
        formPanel.add(txtScore);

        // Buttons
        btnAdd = new JButton("Add Grade");
        btnUpdate = new JButton("Update Grade");
        btnDelete = new JButton("Delete Grade");

        btnAdd.addActionListener(e -> addGrade());
        btnUpdate.addActionListener(e -> updateGrade());
        btnDelete.addActionListener(e -> deleteGrade());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Subject", "Score", "Date"}, 0);
        tblGrades = new JTable(tableModel);
        tblGrades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblGrades.getSelectionModel().addListSelectionListener(e -> populateGrade());

        JScrollPane scrollPane = new JScrollPane(tblGrades);

        // Layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadStudents() {
        java.util.List<Student> students = studentService.getAllStudents();
        DefaultComboBoxModel<Student> model = new DefaultComboBoxModel<>();
        // Use Stream API to populate combo box
        students.stream().forEach(model::addElement);
        cboStudents.setModel(model);
        if (!students.isEmpty()) {
            cboStudents.setSelectedIndex(0);
            loadGrades();
        }
    }

    private void loadSubjects() {
        java.util.List<Subject> subjects = subjectService.getAllSubjects();
        DefaultComboBoxModel<Subject> model = new DefaultComboBoxModel<>();
        // Use Stream API to populate combo box
        subjects.stream().forEach(model::addElement);
        cboSubjects.setModel(model);
    }

    private void loadGrades() {
        tableModel.setRowCount(0);
        Student student = (Student) cboStudents.getSelectedItem();
        if (student == null) return;

        List<Grade> grades = gradeService.getGradesByStudent(student.getId());
        // Use Stream API to populate table
        grades.stream().forEach(g -> {
            Subject subject = subjectService.findById(g.getSubjectId());
            tableModel.addRow(new Object[]{g.getId(), subject.getName(), g.getScore(), g.getDateRecorded()});
        });
    }

    private void populateGrade() {
        int row = tblGrades.getSelectedRow();
        if (row >= 0) {
            String subjectName = tableModel.getValueAt(row, 1).toString();
            for (int i = 0; i < cboSubjects.getItemCount(); i++) {
                if (cboSubjects.getItemAt(i).getName().equals(subjectName)) {
                    cboSubjects.setSelectedIndex(i);
                    break;
                }
            }
            txtScore.setText(tableModel.getValueAt(row, 2).toString());
        }
    }

    private void addGrade() {
        try {
            Grade grade = new Grade();
            grade.setStudentId(((Student) cboStudents.getSelectedItem()).getId());
            grade.setSubjectId(((Subject) cboSubjects.getSelectedItem()).getId());
            grade.setScore(Double.parseDouble(txtScore.getText()));
            grade.setDateRecorded(LocalDate.now());

            gradeService.addGrade(grade);
            JOptionPane.showMessageDialog(this, "Grade added!");
            loadGrades();
            txtScore.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateGrade() {
        int row = tblGrades.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a grade to update.");
            return;
        }

        try {
            int gradeId = (int) tableModel.getValueAt(row, 0);
            Grade grade = new Grade();
            grade.setId(gradeId);
            grade.setStudentId(((Student) cboStudents.getSelectedItem()).getId());
            grade.setSubjectId(((Subject) cboSubjects.getSelectedItem()).getId());
            grade.setScore(Double.parseDouble(txtScore.getText()));
            grade.setDateRecorded(LocalDate.now());

            gradeService.updateGrade(grade);
            JOptionPane.showMessageDialog(this, "Grade updated.");
            loadGrades();
            txtScore.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteGrade() {
        int row = tblGrades.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a grade to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int gradeId = (int) tableModel.getValueAt(row, 0);
            gradeService.deleteGrade(gradeId);
            JOptionPane.showMessageDialog(this, "Grade deleted.");
            loadGrades();
            txtScore.setText("");
        }
    }
}
