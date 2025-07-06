package factory;

import ui.GradeForm;
import ui.ReportExportForm;
import ui.StudentForm;
import ui.SubjectForm;

import javax.swing.*;

public class ViewFactory {
    public static JFrame createView(ViewType viewType) {
        switch (viewType) {
            case STUDENT:
                return new StudentForm();
            case SUBJECT:
                return new SubjectForm();
            case GRADE:
                return new GradeForm();
            case REPORT:
                return new ReportExportForm();
            default:
                throw new IllegalArgumentException("Unknown view type");
        }
    }

}
