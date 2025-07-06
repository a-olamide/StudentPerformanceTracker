package ui;

import model.Grade;
import model.Student;
import repository.GradeRepository;
import repository.StudentRepository;
import repository.impl.GradeRepositoryImpl;
import repository.impl.StudentRepositoryImpl;
import service.GradeService;
import service.StudentService;
import service.SubjectService;
import strategy.ConsoleReportStrategy;
import strategy.CsvReportStrategy;
import strategy.GradeReportContext;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        for (Student s : students) {
            model.addElement(s);
        }
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
