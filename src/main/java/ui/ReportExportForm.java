package ui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Grade;
import models.Student;
import service.GradeService;
import service.StudentService;
import strategy.ConsoleReportStrategy;
import strategy.CsvReportStrategy;
import strategy.GradeReportContext;

public class ReportExportForm extends JFrame {

    private final GradeService gradeService = new GradeService();
    private final StudentService studentService = new StudentService();

    private JComboBox<Student> cboStudents;
    private JButton btnExportCsv, btnExportConsole;

    public ReportExportForm() {
        setTitle("Export Student Report");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadStudents();
    }

    private void initComponents() {
        cboStudents = new JComboBox<>();
        btnExportCsv = new JButton("Export to CSV");
        btnExportConsole = new JButton("Print to Console");

        btnExportCsv.addActionListener(e -> exportReport("csv"));
        btnExportConsole.addActionListener(e -> exportReport("console"));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(cboStudents);
        panel.add(btnExportCsv);
        panel.add(btnExportConsole);

        getContentPane().add(panel);
    }

    private void loadStudents() {
        java.util.List<Student> students = studentService.getAllStudents();
        DefaultComboBoxModel<Student> model = new DefaultComboBoxModel<>();
        // Use Stream API to populate combo box
        students.stream().forEach(model::addElement);
        cboStudents.setModel(model);
    }

    private void exportReport(String type) {
        Student student = (Student) cboStudents.getSelectedItem();
        if (student == null) return;

        List<Grade> grades = gradeService.getGradesByStudent(student.getId());

        GradeReportContext context = new GradeReportContext();
        if ("csv".equals(type)) {
            context.setStrategy(new CsvReportStrategy());
        } else {
            context.setStrategy(new ConsoleReportStrategy());
        }

        context.generateReport(student, grades);
        JOptionPane.showMessageDialog(this, "Report generated successfully.");
    }
}
