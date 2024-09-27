package ku.cs.services.datasource;

import ku.cs.models.collections.*;
import ku.cs.models.users.Admin;
import ku.cs.models.users.User;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;


public class AdminDataSource implements Readable<UserList, Admin>, Writable<UserList, Admin> {
    private StudentList studentList;
    private DepartmentList departments;
    private FacultyList faculty;
    private StudentList students;
    private AdvisorList advisors;
    private DepartmentOfficerList departmentOfficers;
    private FacultyOfficerList facultyOfficers;

    public AdminDataSource(StudentList studentList, DepartmentList departments, FacultyList faculty, AdvisorList advisors, DepartmentOfficerList departmentOfficers, FacultyOfficerList facultyOfficers) {
        // Initialize the data source with the collections
        this.studentList = studentList;
        this.departments = departments;
        this.faculty = faculty;
        this.advisors = advisors;
        this.departmentOfficers = departmentOfficers;
        this.facultyOfficers = facultyOfficers;
    }

    @Override
    public String getFileName() {
        return "admin.csv";  // Example file name for storage
    }

    @Override
    public String getDirectory() {
        return "data/users";  // Example directory where the file will be stored
    }

    @Override
    public Admin hashMapToModel(HashMap<String, String> row) {
        // Convert a HashMap representation of a row back to an Admin object
        Admin admin =  new Admin(
                row.get("username"),
                row.get("hashedPassword"),
                row.get("nameTitle"),
                row.get("name"),
                row.get("surname"),
                row.get("role"),
                row.get("recentTime"),
                Boolean.parseBoolean(row.get("status")),
                Boolean.parseBoolean(row.get("activated")),
                row.get("profilePictureFileName")
        );
        admin.setStudentList(studentList);
        admin.setDepartmentList(departments);
        admin.setFacultyList(faculty);
        admin.setStudentList(students);
        admin.setAdvisorList(advisors);
        admin.setDepartmentOfficerList(departmentOfficers);
        admin.setFacultyOfficerList(facultyOfficers);
        return admin;
    }

    @Override
    public UserList collectionInitializer() {
        return new UserList();  // Initialize a new UserList collection
    }

    @Override
    public void addModelToList(UserList list, Admin model) {
        list.addUser(model);  // Add the model (Admin) to the collection (UserList)
    }

    @Override
    public ArrayList<String> getTableHeader() {
        // Define the header for the CSV file
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
        // Convert an Admin object to a HashMap representation for saving
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
        // Cast the users in the collection to Admin objects and return them as a list
        ArrayList<Admin> admins = new ArrayList<>();
        for (User user : collection.getUsers()) {
            if (user instanceof Admin) {
                admins.add((Admin) user);
            }
        }
        return admins;
    }
}
