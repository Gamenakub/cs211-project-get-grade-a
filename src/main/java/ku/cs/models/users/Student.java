package ku.cs.models.users;

import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.collections.RequestFormList;

import java.time.LocalDateTime;

public class Student extends User {
    private String studentId;
    private String studentEmail;
    private Advisor advisor;
    private final Department department;
    private RequestFormList requestFormList;

    public Student(String username, String nameTitle, String name, String surname, String studentId, String studentEmail, Advisor advisor, Department department) {
        super(username, "", nameTitle, name, surname, "student");
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.advisor = advisor;
        this.department = department;
        this.requestFormList = new RequestFormList();
    }

    public Student(String username, String hashedPassword, String nameTitle, String name, String surname, String role, LocalDateTime recentTime, boolean status, boolean activated, String profilePictureFileName, String studentId, String studentEmail, Advisor advisor, Department department) {
        super(username, hashedPassword, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName);
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.advisor = advisor;
        this.department = department;
        this.requestFormList = new RequestFormList();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public Department getDepartment() {
        return department;
    }

    public Faculty getFaculty() {
        return department.getFaculty();
    }

    public RequestFormList getRequestFormList() {
        return requestFormList;
    }

    public void setRequestFormList(RequestFormList requestFormList) {
        this.requestFormList = requestFormList;
    }

    public boolean checkStudentById(String studentId) {
        return this.studentId.equals(studentId);
    }

    public boolean checkStudentRegister(String studentNameTitle, String studentName, String studentSurname, String studentId, String studentEmail) {
        return this.getNameTitle().equals(studentNameTitle) && this.getName().equals(studentName) && this.getSurname().equals(studentSurname) && this.studentId.equals(studentId) && this.studentEmail.equals(studentEmail);
    }

    @Override
    public void setPassword(String password) {
        if (!this.getActivated()) {
            super.setPassword(password);
        }
    }
}
