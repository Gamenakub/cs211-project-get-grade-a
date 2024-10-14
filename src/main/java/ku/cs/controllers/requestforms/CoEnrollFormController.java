package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.ThaiStringConverter;

public class CoEnrollFormController implements RequestFormController {
    @FXML private Label departmentLabel;
    @FXML private Label dateLabel;
    @FXML private Label monthLabel;
    @FXML private Label yearLabel;
    @FXML private Label headerCourseNameLabel;
    @FXML private Label headerCourseSectionLabel;
    @FXML private Label advisorNameLabel;
    @FXML private Line mrLine;
    @FXML private Line mrsLine;
    @FXML private Line msLine;
    @FXML private Label studentNameLabel;
    @FXML private Label studentIdLabel;
    @FXML private Label studentYearLabel;
    @FXML private Label studentDepartmentLabel;
    @FXML private Label facultyLabel;
    @FXML private Label addressLabel;
    @FXML private Label addressLabel2;
    @FXML private Label phoneLabel;
    @FXML private Label courseNameLabel;
    @FXML private Label courseSectionLabel;
    @FXML private Label courseIdLabel;
    @FXML private Label courseTeacherLabel;
    @FXML private Label teacherDepartmentLabel;
    @FXML private Label teacherFacultyLabel;
    @FXML private Label semesterLabel;
    @FXML private Label academicYearLabel;
    @FXML private Label reasonLabel;
    @FXML private Label reasonLabel2;

    private CoEnrollRequestForm coEnrollRequestForm;

    @Override
    public void initializeForm() {
        Student student = coEnrollRequestForm.getStudent();
        departmentLabel.setText(student.getDepartment().getName());
        dateLabel.setText(String.valueOf(coEnrollRequestForm.getTimeStamp().getDayOfMonth()));
        monthLabel.setText(ThaiStringConverter.getThaiMonthString(coEnrollRequestForm.getTimeStamp().getMonthValue()));
        yearLabel.setText(ThaiStringConverter.getThaiYearString(coEnrollRequestForm.getTimeStamp().getYear()));
        headerCourseNameLabel.setText(coEnrollRequestForm.getCourseName());
        headerCourseSectionLabel.setText(coEnrollRequestForm.getCourseSection());
        advisorNameLabel.setText(coEnrollRequestForm.getAdvisor().getNameTitle() + coEnrollRequestForm.getAdvisor().getName() + " " + coEnrollRequestForm.getAdvisor().getSurname());
        switch (student.getNameTitle()) {
            case "นาย" -> {
                mrLine.setVisible(false);
                studentNameLabel.setText(student.getName() + " " + student.getSurname());
            }
            case "นาง" -> {
                mrsLine.setVisible(false);
                studentNameLabel.setText(student.getName() + " " + student.getSurname());
            }
            case "นางสาว" -> {
                msLine.setVisible(false);
                studentNameLabel.setText(student.getName() + " " + student.getSurname());
            }
            default ->
                    studentNameLabel.setText(student.getNameTitle() + student.getName() + " " + student.getSurname());
        }
        studentIdLabel.setText(student.getStudentId());
        studentYearLabel.setText(coEnrollRequestForm.getStudentYear());
        studentDepartmentLabel.setText(student.getDepartment().getName());
        facultyLabel.setText(student.getFaculty().getName());
        String address = coEnrollRequestForm.getAddress();
        if (address != null && !address.isEmpty()) {
            int midpoint = address.length() / 2;
            int splitIndex = address.lastIndexOf(" ", midpoint);

            if (splitIndex == -1) {
                splitIndex = address.length();
            }

            String part1 = address.substring(0, splitIndex).trim();
            String part2 = address.substring(splitIndex).trim();

            addressLabel.setText(part1);
            addressLabel2.setText(part2);
        } else {
            addressLabel.setText("");
            addressLabel2.setText("");
        }
        phoneLabel.setText(coEnrollRequestForm.getPhoneNumber());
        courseNameLabel.setText(coEnrollRequestForm.getCourseName());
        courseIdLabel.setText(coEnrollRequestForm.getCourseId());
        courseSectionLabel.setText(coEnrollRequestForm.getCourseSection());
        courseTeacherLabel.setText(coEnrollRequestForm.getTeacherName());
        teacherDepartmentLabel.setText(coEnrollRequestForm.getStudent().getDepartment().getName());
        teacherFacultyLabel.setText(coEnrollRequestForm.getStudent().getFaculty().getName());
        semesterLabel.setText(coEnrollRequestForm.getSemester());
        academicYearLabel.setText(coEnrollRequestForm.getAcademicYear());
        String requestFormCause = coEnrollRequestForm.getRequestFormCause();
        if (requestFormCause != null && !requestFormCause.isEmpty()) {
            int midpoint = requestFormCause.length() / 2;
            int splitIndex = requestFormCause.lastIndexOf(" ", midpoint);

            if (splitIndex == -1) {
                splitIndex = requestFormCause.length();
            }

            String part1 = requestFormCause.substring(0, splitIndex).trim();
            String part2 = requestFormCause.substring(splitIndex).trim();

            reasonLabel.setText(part1);
            reasonLabel2.setText(part2);
        } else {
            reasonLabel.setText("");
            reasonLabel2.setText("");
        }
    }

    @Override
    public void prepareDataForPDF(RequestForm requestForm) {
        this.coEnrollRequestForm = (CoEnrollRequestForm) requestForm;
    }
}
