package ku.cs.services.datasource;

import ku.cs.models.Department;
import ku.cs.models.collections.AdvisorList;
import ku.cs.models.collections.DepartmentList;
import ku.cs.models.collections.StudentList;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentDataSource implements Writable<StudentList, Student>, Readable<StudentList, Student> {
    private static final String FILE_NAME = "student.csv";
    private static final String DIRECTORY = "data/users";
    private final AdvisorList advisorList;
    private final DepartmentList departmentList;

    public StudentDataSource(AdvisorList advisorList, DepartmentList departmentList) {
        this.advisorList = advisorList;
        this.departmentList = departmentList;
    }

    @Override
    public String getFileName() {
        return FILE_NAME;
    }

    @Override
    public String getDirectory() {
        return DIRECTORY;
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("username");
        headers.add("hashedPassword");
        headers.add("nameTitle");
        headers.add("name");
        headers.add("surname");
        headers.add("role");
        headers.add("recentTime");
        headers.add("status");
        headers.add("activated");
        headers.add("profilePictureFileName");
        headers.add("studentId");
        headers.add("studentEmail");
        headers.add("advisorId");
        headers.add("departmentId");
        return headers;
    }

    @Override
    public HashMap<String, String> modelToHashMap(Student student) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", student.getUsername());
        map.put("hashedPassword", student.getHashedPassword());
        map.put("nameTitle", student.getNameTitle());
        map.put("name", student.getName());
        map.put("surname", student.getSurname());
        map.put("role", student.getRole());
        map.put("recentTime", student.getRecentTimeString());
        map.put("status", String.valueOf(student.getStatus()));
        map.put("activated", String.valueOf(student.getActivated()));
        map.put("profilePictureFileName", student.getProfilePictureFileName());
        map.put("studentId", student.getStudentId());
        map.put("studentEmail", student.getStudentEmail());
        map.put("advisorId", student.getAdvisor() != null ? student.getAdvisor().getAdvisorId() : "");
        map.put("departmentId", student.getDepartment().getId());
        return map;
    }

    @Override
    public ArrayList<Student> getCollectionArrayList(StudentList collection) {
        return collection.getStudents();
    }

    @Override
    public Student hashMapToModel(HashMap<String, String> row) {
        String username = row.get("username");
        String password = row.get("hashedPassword");
        String nameTitle = row.get("nameTitle");
        String name = row.get("name");
        String surname = row.get("surname");
        String role = row.get("role");
        LocalDateTime recentTime = row.get("recentTime").equals("null") ? null : LocalDateTime.parse(row.get("recentTime"));
        boolean status = Boolean.parseBoolean(row.get("status"));
        boolean activated = Boolean.parseBoolean(row.get("activated"));
        String profilePictureFileName = row.get("profilePictureFileName");
        String studentId = row.get("studentId");
        String studentEmail = row.get("studentEmail");

        Advisor advisor = advisorList.findAdvisorById(row.get("advisorId"));
        Department department = departmentList.findDepartmentById(row.get("departmentId"));

        return new Student(username, password, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName, studentId, studentEmail, advisor, department);
    }

    @Override
    public StudentList collectionInitializer() {
        return new StudentList();
    }

    @Override
    public void addModelToList(StudentList list, Student model) {
        list.addStudent(model);
    }
}
