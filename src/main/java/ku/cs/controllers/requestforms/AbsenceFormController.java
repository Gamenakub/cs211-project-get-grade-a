package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import ku.cs.models.Course;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.ThaiStringConverter;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class AbsenceFormController implements RequestFormController {
    @FXML private Label facultyAndUniversityLabel;
    @FXML private Label advisorNameLabel;
    @FXML private Label dateLabel;
    @FXML private Label monthLabel;
    @FXML private Label yearLabel;
    @FXML private Line mrLine;
    @FXML private Line mrsLine;
    @FXML private Line msLine;
    @FXML private Label studentNameLabel;
    @FXML private Label studentIdLabel;
    @FXML private Label studentYearLabel;
    @FXML private Label facultyLabel;
    @FXML private Label departmentLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label facebookLabel;
    @FXML private Label lineLabel;
    @FXML private Label sickLeaveLabel;
    @FXML private Label personalLeaveLabel;
    @FXML private Label absenceDurationLabel;
    @FXML private Label dateFromLabel;
    @FXML private Label monthFromLabel;
    @FXML private Label yearFromLabel;
    @FXML private Label dateToLabel;
    @FXML private Label monthToLabel;
    @FXML private Label yearToLabel;
    @FXML private Label absenceReasonLabel;
    @FXML private Label absenceReasonLabel2;
    @FXML private Label courseIdLabel1;
    @FXML private Label courseNameLabel1;
    @FXML private Label courseSectionLabel1;
    @FXML private Label courseTeacherLabel1;
    @FXML private Label courseIdLabel2;
    @FXML private Label courseNameLabel2;
    @FXML private Label courseSectionLabel2;
    @FXML private Label courseTeacherLabel2;
    @FXML private Label courseIdLabel3;
    @FXML private Label courseNameLabel3;
    @FXML private Label courseSectionLabel3;
    @FXML private Label courseTeacherLabel3;
    @FXML private Label courseIdLabel4;
    @FXML private Label courseNameLabel4;
    @FXML private Label courseSectionLabel4;
    @FXML private Label courseTeacherLabel4;
    @FXML private Label courseIdLabel5;
    @FXML private Label courseNameLabel5;
    @FXML private Label courseSectionLabel5;
    @FXML private Label courseTeacherLabel5;
    @FXML private Label courseIdLabel6;
    @FXML private Label courseNameLabel6;
    @FXML private Label courseSectionLabel6;
    @FXML private Label courseTeacherLabel6;
    @FXML private Label courseIdLabel7;
    @FXML private Label courseNameLabel7;
    @FXML private Label courseSectionLabel7;
    @FXML private Label courseTeacherLabel7;
    @FXML private Label courseIdLabel8;
    @FXML private Label courseNameLabel8;
    @FXML private Label courseSectionLabel8;
    @FXML private Label courseTeacherLabel8;
    @FXML private Label courseIdLabel9;
    @FXML private Label courseNameLabel9;
    @FXML private Label courseSectionLabel9;
    @FXML private Label courseTeacherLabel9;

    private AbsenceRequestForm absenceRequestForm;

    @Override
    public void initializeForm() {
        Student student = absenceRequestForm.getStudent();
        String faculty = student.getFaculty().getName().equals("วิทยาลัยบูรณาการศาสตร์") ? student.getFaculty().getName() : "คณะ" + student.getFaculty().getName();
        facultyAndUniversityLabel.setText(faculty + " มหาวิทยาลัยเกษตรศาสตร์");
        advisorNameLabel.setText(absenceRequestForm.getAdvisor().getNameTitle() + absenceRequestForm.getAdvisor().getName() + " " + absenceRequestForm.getAdvisor().getSurname());
        dateLabel.setText(String.valueOf(absenceRequestForm.getTimeStamp().getDayOfMonth()));
        monthLabel.setText(ThaiStringConverter.getThaiMonthString(absenceRequestForm.getTimeStamp().getMonthValue()));
        yearLabel.setText(ThaiStringConverter.getThaiYearString(absenceRequestForm.getTimeStamp().getYear()));
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
        StringBuilder studentId = new StringBuilder();
        for (char c : student.getStudentId().toCharArray()) {
            studentId.append(c).append("    ");
        }
        studentIdLabel.setText(studentId.toString());
        studentYearLabel.setText(absenceRequestForm.getStudentYear());
        facultyLabel.setText(faculty);
        departmentLabel.setText("สาขาวิชา" + student.getDepartment().getName());
        phoneLabel.setText(absenceRequestForm.getPhoneNumber());
        facebookLabel.setText(absenceRequestForm.getFacebookID());
        emailLabel.setText(student.getStudentEmail());
        lineLabel.setText(absenceRequestForm.getLineID());
        if (absenceRequestForm.getAbsenceType().equals("ลาป่วย")) {
            personalLeaveLabel.setVisible(false);
        } else if (absenceRequestForm.getAbsenceType().equals("ลากิจ")) {
            sickLeaveLabel.setVisible(false);
        }
        absenceDurationLabel.setText(String.valueOf(ChronoUnit.DAYS.between(absenceRequestForm.getAbsenceDateFrom(), absenceRequestForm.getAbsenceDateUntil()) + 1));
        dateFromLabel.setText(String.valueOf(absenceRequestForm.getAbsenceDateFrom().getDayOfMonth()));
        monthFromLabel.setText(ThaiStringConverter.getThaiMonthString(absenceRequestForm.getAbsenceDateFrom().getMonthValue()));
        yearFromLabel.setText(ThaiStringConverter.getThaiYearString(absenceRequestForm.getAbsenceDateFrom().getYear()));
        dateToLabel.setText(String.valueOf(absenceRequestForm.getAbsenceDateUntil().getDayOfMonth()));
        monthToLabel.setText(ThaiStringConverter.getThaiMonthString(absenceRequestForm.getAbsenceDateUntil().getMonthValue()));
        yearToLabel.setText(ThaiStringConverter.getThaiYearString(absenceRequestForm.getAbsenceDateUntil().getYear()));
        String requestFormCause = absenceRequestForm.getRequestFormCause();

        if (requestFormCause != null && !requestFormCause.isEmpty()) {
            int midpoint = requestFormCause.length() / 2;
            int splitIndex = requestFormCause.lastIndexOf(" ", midpoint);

            if (splitIndex == -1) {
                splitIndex = requestFormCause.length();
            }

            String part1 = requestFormCause.substring(0, splitIndex).trim();
            String part2 = requestFormCause.substring(splitIndex).trim();

            absenceReasonLabel.setText(part1);
            absenceReasonLabel2.setText(part2);
        } else {
            absenceReasonLabel.setText("");
            absenceReasonLabel2.setText("");
        }

        Label[] courseIdLabels = {
                courseIdLabel1, courseIdLabel2, courseIdLabel3, courseIdLabel4,
                courseIdLabel5, courseIdLabel6, courseIdLabel7, courseIdLabel8,
                courseIdLabel9
        };
        Label[] courseNameLabels = {
                courseNameLabel1, courseNameLabel2, courseNameLabel3, courseNameLabel4,
                courseNameLabel5, courseNameLabel6, courseNameLabel7, courseNameLabel8,
                courseNameLabel9
        };
        Label[] courseSectionLabels = {
                courseSectionLabel1, courseSectionLabel2, courseSectionLabel3, courseSectionLabel4,
                courseSectionLabel5, courseSectionLabel6, courseSectionLabel7, courseSectionLabel8,
                courseSectionLabel9
        };
        Label[] courseTeacherLabels = {
                courseTeacherLabel1, courseTeacherLabel2, courseTeacherLabel3, courseTeacherLabel4,
                courseTeacherLabel5, courseTeacherLabel6, courseTeacherLabel7, courseTeacherLabel8,
                courseTeacherLabel9
        };
        List<Course> courses = absenceRequestForm.getCourseList().getCourses();
        int courseCount = courses.size();

        for (int i = 0; i < courseCount; i++) {
            Course course = courses.get(i);
            courseIdLabels[i].setText(Objects.requireNonNullElse(course.getCourseId(), ""));
            courseNameLabels[i].setText(Objects.requireNonNullElse(course.getCourseName(), ""));
            courseSectionLabels[i].setText(Objects.requireNonNullElse(course.getLectureSection(), ""));
            courseTeacherLabels[i].setText(Objects.requireNonNullElse(course.getProfessorName(), ""));
        }

        for (int i = courseCount; i < courseIdLabels.length; i++) {
            courseIdLabels[i].setText("");
            courseNameLabels[i].setText("");
            courseSectionLabels[i].setText("");
            courseTeacherLabels[i].setText("");
        }
    }

    @Override
    public void prepareDataForPDF(RequestForm requestForm) {
        this.absenceRequestForm = (AbsenceRequestForm) requestForm;
    }
}
