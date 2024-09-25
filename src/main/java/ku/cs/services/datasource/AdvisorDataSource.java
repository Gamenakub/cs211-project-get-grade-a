package ku.cs.services.datasource;

import ku.cs.models.Department;
import ku.cs.models.collections.AdvisorList;
import ku.cs.models.collections.DepartmentList;
import ku.cs.models.users.Advisor;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;

public class AdvisorDataSource implements Readable<AdvisorList, Advisor>, Writable<AdvisorList, Advisor> {

    private DepartmentList departmentList;

    public AdvisorDataSource(DepartmentList departmentList) {
        this.departmentList = departmentList;
    }

    @Override
    public String getFileName() {
        return "advisor.csv";
    }

    @Override
    public String getDirectory() {
        return "data/users";
    }

    @Override
    public Advisor hashMapToModel(HashMap<String, String> row) {
        String username = row.get("username");
        String hashedPassword = row.get("hashedPassword");
        String nameTitle= row.get("nameTitle");
        String name = row.get("name");
        String surname = row.get("surname");
        String advisorId = row.get("advisorId");
        String recentTime = row.get("recentTime");
        String role = row.get("role");
        boolean status = Boolean.parseBoolean(row.get("status"));
        boolean activated = Boolean.parseBoolean(row.get("activated"));
        String profilePictureFileName = row.get("profilePictureFileName");
        Department department = departmentList.findDepartmentById(row.get("department"));


        // Assuming the requestForms and students are handled elsewhere
        Advisor advisor = new Advisor(username, hashedPassword,nameTitle, name, surname,role,recentTime,status,activated,profilePictureFileName,advisorId, department);

        return advisor;
    }

    @Override
    public AdvisorList collectionInitializer() {
        return new AdvisorList(new ArrayList<>());
    }

    @Override
    public void addModelToList(AdvisorList list, Advisor advisor) {
        list.addAdvisor(advisor);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("username");
        headers.add("hashedPassword");
        headers.add("nameTitle");
        headers.add("name");
        headers.add("surname");
        headers.add("advisorId");
        headers.add("recentTime");
        headers.add("status");
        headers.add("activated");
        headers.add("role");
        headers.add("profilePictureFileName");
        headers.add("department");
        return headers;
    }

//    @Override
//    public ArrayList<String> getTableHeader() {
//        ArrayList<String> headers = new ArrayList<>();
//        headers.add("advisorId");
//        headers.add("role");
//        headers.add("recentTime");
//        headers.add("hashedPassword");
//        headers.add("surname");
//        headers.add("name");
//        headers.add("department");
//        headers.add("username");
//
//        headers.add("status");
//        headers.add("profilePictureFileName");
//
//        headers.add("faculty");
//        return headers;
//    }

    @Override
    public HashMap<String, String> modelToHashMap(Advisor advisor) {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", advisor.getUsername());
        data.put("hashedPassword", advisor.getHashedPassword());
        data.put("nameTitle", advisor.getNameTitle());
        data.put("name", advisor.getName());
        data.put("surname", advisor.getSurname());
        data.put("advisorId", advisor.getAdvisorId());
        data.put("status",String.valueOf(advisor.getStatus()));
        data.put("activated",String.valueOf(advisor.getActivated()));
        data.put("role", advisor.getRole());
        data.put("recentTime", advisor.getRecentTimeString());
        data.put("department", advisor.getDepartment().getId());
        data.put("profilePictureFileName", advisor.getProfilePictureFileName());
        return data;
    }

    @Override
    public ArrayList<Advisor> getCollectionArrayList(AdvisorList collection) {
        return collection.getAdvisors();
    }
}
