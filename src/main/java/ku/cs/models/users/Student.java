package ku.cs.models.users;

import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.requestforms.RequestForm;

public class Student extends User {
    private String studentId;
    private String studentEmail;
    private Advisor advisor;
    private Department department;
    private RequestFormList requestFormList;

    //constructor สำหรับสร้าง object ใหม่
    public Student(String username, String nameTitle, String name, String surname, String studentId, String studentEmail, Advisor advisor, Department department) {
        super(username, "", nameTitle, name, surname, "student"); //default password is empty string for now (waiting for consulting)
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.advisor = advisor;
        this.department = department;
        this.requestFormList = new RequestFormList();
    }
    //constructor สำหรับดึง object เดิม
    public Student(String username, String hashedPassword, String nameTitle, String name, String surname, String role, String recentTime, boolean status, boolean activated, String profilePictureFileName, String studentId, String studentEmail, Advisor advisor, Department department) {
        super(username, hashedPassword, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName);
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.advisor = advisor;
        this.department = department;
        this.requestFormList = new RequestFormList();
    }

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }
    public void setDepartment(Department department) { this.department = department; }
    public void setRequestFormList(RequestFormList requestFormList) {
        this.requestFormList=requestFormList;
    }

    public String getStudentId() { return studentId; }
    public String getStudentEmail() { return studentEmail; }
    public Advisor getAdvisor() { return advisor; }
    public Department getDepartment() { return department; }
    public Faculty getFaculty() { return department.getFaculty(); }
    public RequestFormList getRequestFormList() { return requestFormList; }

    public void addRequestForm(RequestForm requestForm) {
        if(requestForm != null) {
            requestFormList.addRequestForm(requestForm);
        }
    }

    public boolean checkStudentById(String studentId) { return this.studentId.equals(studentId);}
    public boolean checkStudentByName(String studentName) { return this.getName().equals(studentName);}
    public boolean checkStudentRegister(String studentName, String surname, String studentId, String studentEmail) {
        return this.getName().equals(studentName) && this.getSurname().equals(surname) && this.getStudentId().equals(studentId) && this.getStudentEmail().equals(studentEmail);
    }
}
