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
    private final StudentList studentList;
    private final AdvisorList advisorList;
    private final CourseList courseList;

    public AddDropRequestFormDataSource(StudentList studentList, AdvisorList advisorList, CourseList courseList) {
        this.studentList = studentList;
        this.advisorList = advisorList;
        this.courseList = courseList;
    }

    @Override
    public String getFileName() {
        return "add_drop_request_forms.csv";
    }

    @Override
    public String getDirectory() {
        return "data/request-forms/";
    }

    @Override
    public AddDropRequestForm hashMapToModel(HashMap<String, String> row) {
        String mode = row.get("mode");
        String requestFormId = row.get("requestFormId");
        Student student = this.studentList.findStudentById(row.get("studentId"));
        Advisor advisor = this.advisorList.findAdvisorById(row.get("advisorId"));
        RequestForm.Status status = RequestForm.Status.valueOf(row.get("status"));
        CourseList relatedCourseList = courseList.getCourseByRelatedRequestFormId(requestFormId);
        String program = row.get("program");
        String academicYear = row.get("academicYear");
        String semester = row.get("semester");
        String campus = row.get("campus");
        String phoneNumber = row.get("phoneNumber");
        String rejectedCause = row.get("rejectedCause");

        return new AddDropRequestForm(mode, requestFormId, student, advisor, status, relatedCourseList, program, academicYear, semester, campus, phoneNumber, rejectedCause);
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
        headers.add("semester");
        headers.add("campus");
        headers.add("phoneNumber");
        headers.add("rejectedCause");
        return headers;
    }

    @Override
    public HashMap<String, String> modelToHashMap(AddDropRequestForm model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mode", model.getMode());
        map.put("requestFormId", model.getRequestFormId());
        map.put("studentId", model.getStudent().getStudentId());
        map.put("advisorId", model.getAdvisor().getAdvisorId());
        map.put("status", String.valueOf(model.getStatus()));
        map.put("program", model.getProgram());
        map.put("academicYear", model.getAcademicYear());
        map.put("semester", model.getSemester());
        map.put("campus", model.getCampus());
        map.put("phoneNumber", model.getPhoneNumber());
        map.put("rejectedCause", model.getRejectedCause());
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