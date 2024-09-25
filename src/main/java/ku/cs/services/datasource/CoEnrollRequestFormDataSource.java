package ku.cs.services.datasource;

import ku.cs.models.collections.AdvisorList;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.collections.StudentList;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;


public class CoEnrollRequestFormDataSource implements Readable<RequestFormList, CoEnrollRequestForm>, Writable<RequestFormList, CoEnrollRequestForm> {
    private StudentList studentList;
    private AdvisorList advisorList;

    public CoEnrollRequestFormDataSource(StudentList studentList, AdvisorList advisorList) {
        this.studentList = studentList;
        this.advisorList = advisorList;
    }
    @Override
    public String getFileName() {
        // Return the file name for storing the data (e.g., a CSV or JSON file)
        return "co_enroll_request_forms.csv";
    }

    @Override
    public String getDirectory() {
        // Return the directory path where the file is located
        return "data/requestforms/";
    }

    @Override
    public CoEnrollRequestForm hashMapToModel(HashMap<String, String> row) {
        // Convert a HashMap of key-value pairs to a CoEnrollRequestForm model
        String requestFormID = row.get("requestFormID");
        String program = row.get("program");
        RequestForm.Status status = RequestForm.Status.valueOf(row.get("status"));
        String address = row.get("address");

        Student student = studentList.findStudentById(row.get("studentId")); // Create a Student object (assuming a simple constructor)
        Advisor advisor = advisorList.findAdvisorById(row.get("advisorId")); // Create an Advisor object (assuming a simple constructor)

        CoEnrollRequestForm form = new CoEnrollRequestForm(requestFormID, student, advisor, program, status, address);
        // Additional logic to set subjects and teacher can be added here if necessary

        return form;
    }

    @Override
    public RequestFormList collectionInitializer() {
        // Initialize and return an empty RequestFormList collection
        return new RequestFormList();
    }

    @Override
    public void addModelToList(RequestFormList list, CoEnrollRequestForm model) {
        // Add a model (CoEnrollRequestForm) to the collection (RequestFormList)
        list.addRequestForm(model);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        // Return the table header, e.g., a list of column names for a CSV file
        ArrayList<String> header = new ArrayList<>();
        header.add("requestFormID");
        header.add("studentId");
        header.add("advisorId");
        header.add("program");
        header.add("status");
        header.add("address");
        return header;
    }

    @Override
    public HashMap<String, String> modelToHashMap(CoEnrollRequestForm model) {
        // Convert a CoEnrollRequestForm model to a HashMap of key-value pairs
        HashMap<String, String> row = new HashMap<>();
        row.put("requestFormID", model.getRequestFormId());
        row.put("studentId", model.getStudent().getStudentId()); // Assuming getName() returns the student's name
        row.put("advisorId", model.getAdvisor().getAdvisorId()); // Assuming getName() returns the advisor's name
        row.put("program", model.getProgram());
        row.put("status", String.valueOf(model.getStatus()));
        row.put("address", model.getAddress());
        return row;
    }

    @Override
    public ArrayList<CoEnrollRequestForm> getCollectionArrayList(RequestFormList collection) {
        // Convert the RequestFormList collection to an ArrayList of CoEnrollRequestForm objects
        ArrayList<CoEnrollRequestForm> list = new ArrayList<>();
        for (RequestForm form : collection.getRequestForms()) {
            if (form instanceof CoEnrollRequestForm) {
                list.add((CoEnrollRequestForm) form);
            }
        }
        return list;
    }
}
