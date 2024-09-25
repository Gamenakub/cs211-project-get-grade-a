package ku.cs.services.datasource;

import ku.cs.models.collections.AdvisorList;
import ku.cs.models.collections.CourseList;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.collections.StudentList;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;

public class AddDropRequestFormDataSource implements Readable<RequestFormList, AddDropRequestForm>, Writable<RequestFormList, AddDropRequestForm> {
    private StudentList studentList;
    private AdvisorList advisorList;
    private CourseList courseList;

    public AddDropRequestFormDataSource(StudentList studentList, AdvisorList advisorList, CourseList courseList) {
        this.studentList = studentList;
        this.advisorList = advisorList;
        this.courseList = courseList;
    }
    @Override
    public String getFileName() {
        return "add_drop_request_form_data.csv"; // Example file name
    }

    @Override
    public String getDirectory() {
        return "data/requestforms/"; // Example directory
    }

    @Override
    public AddDropRequestForm hashMapToModel(HashMap<String, String> row) {
        String mode = row.get("mode");
        String requestFormId = row.get("requestFormId");
        Student student = this.studentList.findStudentById(row.get("studentId"));  // Assuming this constructor
        Advisor advisor = this.advisorList.findAdvisorById(row.get("advisorId"));  // Assuming this constructor
        RequestForm.Status status = RequestForm.Status.valueOf(row.get("status"));
        CourseList relatedCourseList = courseList.getCourseByRelatedRequestFormId(requestFormId); // Assuming this constructor
        String program = row.get("program");
        String academicYear = row.get("academicYear");
        String term = row.get("term");
        String campus = row.get("campus");

        return new AddDropRequestForm(mode, requestFormId, student, advisor, status, relatedCourseList, program, academicYear, term, campus);
    }

    @Override
    public RequestFormList collectionInitializer() {
        return new RequestFormList();
    }

    @Override
    public void addModelToList(RequestFormList list, AddDropRequestForm model) {
        list.addRequestForm(model);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("mode");
        headers.add("requestFormId");
        headers.add("studentId");
        headers.add("advisorId");
        headers.add("status");
        headers.add("program");
        headers.add("academicYear");
        headers.add("term");
        headers.add("campus");
        return headers;
    }

    @Override
    public HashMap<String, String> modelToHashMap(AddDropRequestForm model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mode", model.getMode());
        map.put("requestFormId", model.getRequestFormId());
        map.put("studentId", model.getStudent().getStudentId());  // Assuming Student has a getId() method
        map.put("advisorId", model.getAdvisor().getAdvisorId());  // Assuming Advisor has a getId() method
        map.put("status", String.valueOf(model.getStatus()));
        map.put("program", model.getProgram());
        map.put("academicYear", model.getAcademicYear());
        map.put("term", model.getTerm());
        map.put("campus", model.getCampus());
        return map;
    }

    @Override
    public ArrayList<AddDropRequestForm> getCollectionArrayList(RequestFormList collection) {
        ArrayList<AddDropRequestForm> forms = new ArrayList<>();
        for (RequestForm requestForm : collection.getRequestForms()) {
            if (requestForm instanceof AddDropRequestForm) {
                forms.add((AddDropRequestForm) requestForm);
            }
        }
        return forms;
    }
}