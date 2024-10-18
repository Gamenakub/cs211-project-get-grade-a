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
    private final StudentList studentList;
    private final AdvisorList advisorList;

    public CoEnrollRequestFormDataSource(StudentList studentList, AdvisorList advisorList) {
        this.studentList = studentList;
        this.advisorList = advisorList;
    }

    @Override
    public String getFileName() {
        return "co_enroll_request_forms.csv";
    }

    @Override
    public String getDirectory() {
        return "data/request-forms/";
    }

    @Override
    public CoEnrollRequestForm hashMapToModel(HashMap<String, String> row) {
        String requestFormID = row.get("requestFormID");
        String studentYear = row.get("studentYear");
        RequestForm.Status status = RequestForm.Status.valueOf(row.get("status"));
        String address = row.get("address");
        String phoneNumber = row.get("phoneNumber");
        String courseName = row.get("courseName");
        String courseId = row.get("courseId");
        String courseSection = row.get("courseSection");
        String semester = row.get("semester");
        String academicYear = row.get("academicYear");
        String teacherName = row.get("teacherName");

        Student student = studentList.findStudentById(row.get("studentId"));
        Advisor advisor = advisorList.findAdvisorById(row.get("advisorId"));
        String requestFormCause = row.get("requestFormCause");
        String rejectedCause = row.get("rejectedCause");

        return new CoEnrollRequestForm(requestFormID, student, advisor, studentYear, status, address, phoneNumber, courseName, courseId, courseSection, semester, academicYear, teacherName, requestFormCause, rejectedCause);
    }

    @Override
    public RequestFormList collectionInitializer() {
        return new RequestFormList();
    }

    @Override
    public void addModelToList(RequestFormList list, CoEnrollRequestForm model) {
        list.addRequestForm(model);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("requestFormID");
        header.add("studentId");
        header.add("advisorId");
        header.add("studentYear");
        header.add("status");
        header.add("address");
        header.add("phoneNumber");
        header.add("courseName");
        header.add("courseId");
        header.add("courseSection");
        header.add("semester");
        header.add("academicYear");
        header.add("teacherName");
        header.add("requestFormCause");
        header.add("rejectedCause");
        return header;
    }

    @Override
    public HashMap<String, String> modelToHashMap(CoEnrollRequestForm model) {
        HashMap<String, String> row = new HashMap<>();
        row.put("requestFormID", model.getRequestFormId());
        row.put("studentId", model.getStudent().getStudentId());
        row.put("advisorId", model.getAdvisor().getAdvisorId());
        row.put("studentYear", model.getStudentYear());
        row.put("status", String.valueOf(model.getStatus()));
        row.put("address", model.getAddress());
        row.put("phoneNumber", model.getPhoneNumber());
        row.put("courseName", model.getCourseName());
        row.put("courseId", model.getCourseId());
        row.put("courseSection", model.getCourseSection());
        row.put("semester", model.getSemester());
        row.put("academicYear", model.getAcademicYear());
        row.put("teacherName", model.getTeacherName());
        row.put("requestFormCause", model.getRequestFormCause());
        row.put("rejectedCause", model.getRejectedCause());
        return row;
    }

    @Override
    public ArrayList<CoEnrollRequestForm> getCollectionArrayList(RequestFormList collection) {
        ArrayList<CoEnrollRequestForm> list = new ArrayList<>();
        for (RequestForm form : collection.getRequestForms()) {
            if (form instanceof CoEnrollRequestForm) {
                list.add((CoEnrollRequestForm) form);
            }
        }
        return list;
    }
}
