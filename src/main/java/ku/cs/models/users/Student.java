package ku.cs.models.users;

import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.requestforms.RequestForm;

public class Student extends User{
    public static final String role = "student";
    private String studentId;
    private String studentEmail;
    private Advisor advisor;
    private Department department;
    private Faculty faculty;
    private final RequestFormList requestFormList;
    //Constructor For Read Data then Create Object & Officer to Create Student Object
    public Student(String username, String password, String name, String surname, String studentId, String studentEmail, Advisor advisor, Department department, Faculty faculty) {
        super(username, password, name, surname);
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.advisor = advisor;
        this.department = department;
        this.faculty = faculty;
        this.requestFormList = new RequestFormList();
    }

    public Student(String username, String password, String name, String surname, String studentId, String studentEmail) {
        super(username, password, name, surname);
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.advisor = null;
        this.department = null;
        this.faculty = null;
        this.requestFormList = new RequestFormList();
    }

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }
    public void setDepartment(Department department) { this.department = department; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public String getRole() { return role; }
    public String getStudentId() { return studentId; }
    public String getStudentEmail() { return studentEmail; }
    public Advisor getAdvisor() { return advisor; }
    public Department getDepartment() { return department; }
    public Faculty getFaculty() { return faculty; }
    public RequestFormList getRequestForms() { return requestFormList; }

    public void addRequestForm(RequestForm requestForm) {
        if(requestForm != null) {
            requestFormList.addRequestForm(requestForm);
        }
    }
    public boolean checkStudentById(String studentId) { return this.studentId.equals(studentId);}
    public boolean checkStudentByName(String studentName) { return this.getName().equals(studentName);}
}
