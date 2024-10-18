package ku.cs.services.datasource;

import ku.cs.models.collections.*;
import ku.cs.models.users.Admin;
import ku.cs.models.users.User;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminDataSource implements Readable<UserList, Admin>, Writable<UserList, Admin> {
    private final StudentList studentList;
    private final DepartmentList departments;
    private final FacultyList faculty;
    private final AdvisorList advisors;
    private final DepartmentOfficerList departmentOfficers;
    private final FacultyOfficerList facultyOfficers;

    public AdminDataSource(StudentList studentList, DepartmentList departments, FacultyList faculty, AdvisorList advisors, DepartmentOfficerList departmentOfficers, FacultyOfficerList facultyOfficers) {
        this.studentList = studentList;
        this.departments = departments;
        this.faculty = faculty;
        this.advisors = advisors;
        this.departmentOfficers = departmentOfficers;
        this.facultyOfficers = facultyOfficers;
    }

    @Override
    public String getFileName() {
        return "admin.csv";
    }

    @Override
    public String getDirectory() {
        return "data/users";
    }

    @Override
    public Admin hashMapToModel(HashMap<String, String> row) {
        Admin admin = new Admin(
                row.get("username"),
                row.get("hashedPassword"),
                row.get("nameTitle"),
                row.get("name"),
                row.get("surname"),
                row.get("role"),
                row.get("recentTime").equals("null") ? null : LocalDateTime.parse(row.get("recentTime")),
                Boolean.parseBoolean(row.get("status")),
                Boolean.parseBoolean(row.get("activated")),
                row.get("profilePictureFileName")
        );
        admin.setStudentList(studentList);
        admin.setDepartmentList(departments);
        admin.setFacultyList(faculty);
        admin.setStudentList(studentList);
        admin.setAdvisorList(advisors);
        admin.setDepartmentOfficerList(departmentOfficers);
        admin.setFacultyOfficerList(facultyOfficers);
        return admin;
    }

    @Override
    public UserList collectionInitializer() {
        return new UserList();
    }

    @Override
    public void addModelToList(UserList list, Admin model) {
        list.addUser(model);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("username");
        header.add("hashedPassword");
        header.add("nameTitle");
        header.add("name");
        header.add("surname");
        header.add("role");
        header.add("recentTime");
        header.add("status");
        header.add("activated");
        header.add("profilePictureFileName");
        return header;
    }

    @Override
    public HashMap<String, String> modelToHashMap(Admin model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", model.getUsername());
        map.put("hashedPassword", model.getHashedPassword());
        map.put("nameTitle", model.getNameTitle());
        map.put("name", model.getName());
        map.put("surname", model.getSurname());
        map.put("role", model.getRole());
        map.put("recentTime", model.getRecentTimeString());
        map.put("status", String.valueOf(model.getStatus()));
        map.put("activated", String.valueOf(model.getActivated()));
        map.put("profilePictureFileName", model.getProfilePictureFileName());
        return map;
    }

    @Override
    public ArrayList<Admin> getCollectionArrayList(UserList collection) {
        ArrayList<Admin> admins = new ArrayList<>();
        for (User user : collection.getUsers()) {
            if (user instanceof Admin) {
                admins.add((Admin) user);
            }
        }
        return admins;
    }
}
