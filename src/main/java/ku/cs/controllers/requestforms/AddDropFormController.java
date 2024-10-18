package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.models.Course;
import ku.cs.models.collections.CourseList;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import java.util.Objects;

public class AddDropFormController implements RequestFormController {
    @FXML private Label semesterLabel;
    @FXML private Label academicYearLabel;
    @FXML private Label campusLabel;
    @FXML private Label studentIdLabel;
    @FXML private Label thaiProgramLabel;
    @FXML private Label interProgramLabel;
    @FXML private Label studentNameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label facultyLabel;
    @FXML private Label departmentLabel;
    @FXML private Label majorCodeLabel;
    @FXML private Label advisorNameLabel;
    @FXML private Label advisorCodeLabel;
    @FXML private Label courseNoLabel1;
    @FXML private Label courseCodeLabel1;
    @FXML private Label courseYearLabel1;
    @FXML private Label courseTypeLabel1;
    @FXML private Label courseLectureSectionLabel1;
    @FXML private Label courseLabSectionLabel1;
    @FXML private Label courseNameLabel1;
    @FXML private Label courseLectureCreditLabel1;
    @FXML private Label courseLabCreditLabel1;
    @FXML private Label courseNoLabel2;
    @FXML private Label courseCodeLabel2;
    @FXML private Label courseYearLabel2;
    @FXML private Label courseTypeLabel2;
    @FXML private Label courseLectureSectionLabel2;
    @FXML private Label courseLabSectionLabel2;
    @FXML private Label courseNameLabel2;
    @FXML private Label courseLectureCreditLabel2;
    @FXML private Label courseLabCreditLabel2;
    @FXML private Label courseNoLabel3;
    @FXML private Label courseCodeLabel3;
    @FXML private Label courseYearLabel3;
    @FXML private Label courseTypeLabel3;
    @FXML private Label courseLectureSectionLabel3;
    @FXML private Label courseLabSectionLabel3;
    @FXML private Label courseNameLabel3;
    @FXML private Label courseLectureCreditLabel3;
    @FXML private Label courseLabCreditLabel3;
    @FXML private Label courseNoLabel4;
    @FXML private Label courseCodeLabel4;
    @FXML private Label courseYearLabel4;
    @FXML private Label courseTypeLabel4;
    @FXML private Label courseLectureSectionLabel4;
    @FXML private Label courseLabSectionLabel4;
    @FXML private Label courseNameLabel4;
    @FXML private Label courseLectureCreditLabel4;
    @FXML private Label courseLabCreditLabel4;
    @FXML private Label courseNoLabel5;
    @FXML private Label courseCodeLabel5;
    @FXML private Label courseYearLabel5;
    @FXML private Label courseTypeLabel5;
    @FXML private Label courseLectureSectionLabel5;
    @FXML private Label courseLabSectionLabel5;
    @FXML private Label courseNameLabel5;
    @FXML private Label courseLectureCreditLabel5;
    @FXML private Label courseLabCreditLabel5;
    @FXML private Label courseNoLabel6;
    @FXML private Label courseCodeLabel6;
    @FXML private Label courseYearLabel6;
    @FXML private Label courseTypeLabel6;
    @FXML private Label courseLectureSectionLabel6;
    @FXML private Label courseLabSectionLabel6;
    @FXML private Label courseNameLabel6;
    @FXML private Label courseLectureCreditLabel6;
    @FXML private Label courseLabCreditLabel6;
    @FXML private Label courseNoLabel7;
    @FXML private Label courseCodeLabel7;
    @FXML private Label courseYearLabel7;
    @FXML private Label courseTypeLabel7;
    @FXML private Label courseLectureSectionLabel7;
    @FXML private Label courseLabSectionLabel7;
    @FXML private Label courseNameLabel7;
    @FXML private Label courseLectureCreditLabel7;
    @FXML private Label courseLabCreditLabel7;
    @FXML private Label courseNoLabel8;
    @FXML private Label courseCodeLabel8;
    @FXML private Label courseYearLabel8;
    @FXML private Label courseTypeLabel8;
    @FXML private Label courseLectureSectionLabel8;
    @FXML private Label courseLabSectionLabel8;
    @FXML private Label courseNameLabel8;
    @FXML private Label courseLectureCreditLabel8;
    @FXML private Label courseLabCreditLabel8;
    @FXML private Label courseNoLabel9;
    @FXML private Label courseCodeLabel9;
    @FXML private Label courseYearLabel9;
    @FXML private Label courseTypeLabel9;
    @FXML private Label courseLectureSectionLabel9;
    @FXML private Label courseLabSectionLabel9;
    @FXML private Label courseNameLabel9;
    @FXML private Label courseLectureCreditLabel9;
    @FXML private Label courseLabCreditLabel9;
    @FXML private Label courseNoLabel10;
    @FXML private Label courseCodeLabel10;
    @FXML private Label courseYearLabel10;
    @FXML private Label courseTypeLabel10;
    @FXML private Label courseLectureSectionLabel10;
    @FXML private Label courseLabSectionLabel10;
    @FXML private Label courseNameLabel10;
    @FXML private Label courseLectureCreditLabel10;
    @FXML private Label courseLabCreditLabel10;
    @FXML private Label courseNoLabel11;
    @FXML private Label courseCodeLabel11;
    @FXML private Label courseYearLabel11;
    @FXML private Label courseTypeLabel11;
    @FXML private Label courseLectureSectionLabel11;
    @FXML private Label courseLabSectionLabel11;
    @FXML private Label courseNameLabel11;
    @FXML private Label courseLectureCreditLabel11;
    @FXML private Label courseLabCreditLabel11;
    @FXML private Label courseNoLabel12;
    @FXML private Label courseCodeLabel12;
    @FXML private Label courseYearLabel12;
    @FXML private Label courseTypeLabel12;
    @FXML private Label courseLectureSectionLabel12;
    @FXML private Label courseLabSectionLabel12;
    @FXML private Label courseNameLabel12;
    @FXML private Label courseLectureCreditLabel12;
    @FXML private Label courseLabCreditLabel12;
    @FXML private Label addCreditLabel;
    @FXML private Label dropCreditLabel;
    @FXML private Label creditAfterLabel;

    private AddDropRequestForm addDropRequestForm;

    @Override
    public void initializeForm() {
        Student student = addDropRequestForm.getStudent();
        semesterLabel.setText(addDropRequestForm.getSemester());
        academicYearLabel.setText(addDropRequestForm.getAcademicYear());
        campusLabel.setText(addDropRequestForm.getCampus());
        StringBuilder studentId = new StringBuilder();
        for (char c : student.getStudentId().toCharArray()) {
            studentId.append(c).append("    ");
        }
        studentIdLabel.setText(studentId.toString());
        if (addDropRequestForm.getProgram().equals("ไทย")) {
            interProgramLabel.setVisible(false);
        } else if (addDropRequestForm.getProgram().equals("นานาชาติ")) {
            thaiProgramLabel.setVisible(false);
        }
        studentNameLabel.setText(student.getNameTitle() + student.getName() + " " + student.getSurname());
        phoneLabel.setText(addDropRequestForm.getPhoneNumber());
        facultyLabel.setText(student.getFaculty().getName());
        departmentLabel.setText(student.getDepartment().getName());
        majorCodeLabel.setText(student.getDepartment().getId());
        advisorNameLabel.setText(student.getAdvisor().getNameTitle() + student.getAdvisor().getName() + " " + student.getAdvisor().getSurname());
        advisorCodeLabel.setText(student.getAdvisor().getAdvisorId());
        CourseList courseList = addDropRequestForm.getCourseList();
        int courseCount = courseList.getCourses().size();
        boolean isAddMode = addDropRequestForm.getMode().equalsIgnoreCase("add");

        Label[] courseNoLabels = {
                courseNoLabel1, courseNoLabel2, courseNoLabel3, courseNoLabel4,
                courseNoLabel5, courseNoLabel6, courseNoLabel7, courseNoLabel8,
                courseNoLabel9, courseNoLabel10, courseNoLabel11, courseNoLabel12
        };
        Label[] courseCodeLabels = {
                courseCodeLabel1, courseCodeLabel2, courseCodeLabel3, courseCodeLabel4,
                courseCodeLabel5, courseCodeLabel6, courseCodeLabel7, courseCodeLabel8,
                courseCodeLabel9, courseCodeLabel10, courseCodeLabel11, courseCodeLabel12
        };
        Label[] courseYearLabels = {
                courseYearLabel1, courseYearLabel2, courseYearLabel3, courseYearLabel4,
                courseYearLabel5, courseYearLabel6, courseYearLabel7, courseYearLabel8,
                courseYearLabel9, courseYearLabel10, courseYearLabel11, courseYearLabel12
        };
        Label[] courseTypeLabels = {
                courseTypeLabel1, courseTypeLabel2, courseTypeLabel3, courseTypeLabel4,
                courseTypeLabel5, courseTypeLabel6, courseTypeLabel7, courseTypeLabel8,
                courseTypeLabel9, courseTypeLabel10, courseTypeLabel11, courseTypeLabel12
        };
        Label[] courseLectureSectionLabels = {
                courseLectureSectionLabel1, courseLectureSectionLabel2, courseLectureSectionLabel3, courseLectureSectionLabel4,
                courseLectureSectionLabel5, courseLectureSectionLabel6, courseLectureSectionLabel7, courseLectureSectionLabel8,
                courseLectureSectionLabel9, courseLectureSectionLabel10, courseLectureSectionLabel11, courseLectureSectionLabel12
        };
        Label[] courseLabSectionLabels = {
                courseLabSectionLabel1, courseLabSectionLabel2, courseLabSectionLabel3, courseLabSectionLabel4,
                courseLabSectionLabel5, courseLabSectionLabel6, courseLabSectionLabel7, courseLabSectionLabel8,
                courseLabSectionLabel9, courseLabSectionLabel10, courseLabSectionLabel11, courseLabSectionLabel12
        };
        Label[] courseNameLabels = {
                courseNameLabel1, courseNameLabel2, courseNameLabel3, courseNameLabel4,
                courseNameLabel5, courseNameLabel6, courseNameLabel7, courseNameLabel8,
                courseNameLabel9, courseNameLabel10, courseNameLabel11, courseNameLabel12
        };
        Label[] courseLectureCreditLabels = {
                courseLectureCreditLabel1, courseLectureCreditLabel2, courseLectureCreditLabel3, courseLectureCreditLabel4,
                courseLectureCreditLabel5, courseLectureCreditLabel6, courseLectureCreditLabel7, courseLectureCreditLabel8,
                courseLectureCreditLabel9, courseLectureCreditLabel10, courseLectureCreditLabel11, courseLectureCreditLabel12
        };
        Label[] courseLabCreditLabels = {
                courseLabCreditLabel1, courseLabCreditLabel2, courseLabCreditLabel3, courseLabCreditLabel4,
                courseLabCreditLabel5, courseLabCreditLabel6, courseLabCreditLabel7, courseLabCreditLabel8,
                courseLabCreditLabel9, courseLabCreditLabel10, courseLabCreditLabel11, courseLabCreditLabel12
        };

        int startIndex = isAddMode ? 0 : 6;
        int endIndex = isAddMode ? 6 : 12;
        int creditCount = 0;
        for (int i = 0; i < 12; i++) {
            courseNoLabels[i].setText("");
            courseCodeLabels[i].setText("");
            courseYearLabels[i].setText("");
            courseTypeLabels[i].setText("");
            courseLectureSectionLabels[i].setText("");
            courseLabSectionLabels[i].setText("");
            courseNameLabels[i].setText("");
            courseLectureCreditLabels[i].setText("");
            courseLabCreditLabels[i].setText("");
        }
        for (int i = startIndex; i < endIndex && i - startIndex < courseCount; i++) {
            Course course = courseList.getCourses().get(i - startIndex);
            courseNoLabels[i].setText(String.valueOf(i + 1 - startIndex));
            StringBuilder courseId = new StringBuilder();
            for (char c : course.getCourseId().toCharArray()) {
                courseId.append(c).append("  ");
            }
            courseCodeLabels[i].setText(courseId.toString());
            courseYearLabels[i].setText(Objects.requireNonNullElse(course.getCourseYear(), ""));
            courseTypeLabels[i].setText(Objects.requireNonNullElse(course.getCourseType(), ""));
            courseLectureSectionLabels[i].setText(Objects.requireNonNullElse(course.getLectureSection(), ""));
            courseLabSectionLabels[i].setText(Objects.requireNonNullElse(course.getPracticeSection(), ""));
            courseNameLabels[i].setText(Objects.requireNonNullElse(course.getCourseName(), ""));
            courseLectureCreditLabels[i].setText(Objects.requireNonNullElse(course.getLectureCredit(), ""));
            courseLabCreditLabels[i].setText(Objects.requireNonNullElse(course.getPracticeCredit(), ""));

            String lectureCredit = course.getLectureCredit();
            String labCredit = course.getPracticeCredit();

            if (isNumber(lectureCredit) && !lectureCredit.equals("-")) {
                creditCount += Integer.parseInt(lectureCredit);
            }
            if (isNumber(labCredit) && !labCredit.equals("-")) {
                creditCount += Integer.parseInt(labCredit);
            }
        }
        for (int i = courseCount + startIndex; i < endIndex; i++) {
            courseNoLabels[i].setText("");
            courseCodeLabels[i].setText("");
            courseYearLabels[i].setText("");
            courseTypeLabels[i].setText("");
            courseLectureSectionLabels[i].setText("");
            courseLabSectionLabels[i].setText("");
            courseNameLabels[i].setText("");
            courseLectureCreditLabels[i].setText("");
            courseLabCreditLabels[i].setText("");
        }
        if (isAddMode) {
            addCreditLabel.setText(creditCount + "");
            dropCreditLabel.setText("");
        } else {
            dropCreditLabel.setText(creditCount + "");
            addCreditLabel.setText("");
        }
        creditAfterLabel.setText(creditCount + "");
    }

    @Override
    public void prepareDataForPDF(RequestForm requestForm) {
        this.addDropRequestForm = (AddDropRequestForm) requestForm;
    }

    public boolean isNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
