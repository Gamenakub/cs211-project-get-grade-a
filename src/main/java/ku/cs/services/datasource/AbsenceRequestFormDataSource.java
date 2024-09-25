package ku.cs.services.datasource;

import ku.cs.models.collections.AdvisorList;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.collections.StudentList;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AbsenceRequestFormDataSource implements Writable<RequestFormList, AbsenceRequestForm>, Readable<RequestFormList, AbsenceRequestForm> {
    private StudentList studentList;
    private AdvisorList advisorList;

    public AbsenceRequestFormDataSource(StudentList studentList, AdvisorList advisorList) {
        this.studentList = studentList;
        this.advisorList = advisorList;
    }
    @Override
    public String getFileName() {
        return "absence_request_forms.csv";
    }

    @Override
    public String getDirectory() {
        return "data/requestforms/";
    }

    @Override
    public AbsenceRequestForm hashMapToModel(HashMap<String, String> row) {

        // Convert a HashMap to an AbsenceRequestForm model object
        // Assuming necessary parsing and error-handling logic
        String requestFormId = row.get("requestFormId");
        Student student = this.studentList.findStudentById(row.get("studentId"));  // Assuming this constructor
        Advisor advisor = this.advisorList.findAdvisorById(row.get("advisorId"));  // Assuming this constructor
        RequestForm.Status status = RequestForm.Status.valueOf(row.get("status"));
        String program = row.get("program");
        String academicYear = row.get("academicYear");
        String phoneNumber = row.get("phoneNumber");
        String facebookID = row.get("facebookID");
        String lineID = row.get("lineID");
        String absenceType = row.get("absenceType");
        LocalDate absenceDateFrom = LocalDate.parse(row.get("absenceDateFrom"));
        LocalDate absenceDateUntil = LocalDate.parse(row.get("absenceDateUntil"));

        return new AbsenceRequestForm(requestFormId, student, advisor, status, program, academicYear, phoneNumber, facebookID, lineID, absenceType, absenceDateFrom, absenceDateUntil);
    }

    @Override
    public RequestFormList collectionInitializer() {
        return new RequestFormList();
    }

    @Override
    public void addModelToList(RequestFormList list, AbsenceRequestForm model) {
        list.addRequestForm(model);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("requestFormId");
        header.add("studentId");
        header.add("advisorId");
        header.add("status");
        header.add("program");
        header.add("academicYear");
        header.add("phoneNumber");
        header.add("facebookID");
        header.add("lineID");
        header.add("absenceType");
        header.add("absenceDateFrom");
        header.add("absenceDateUntil");
        return header;
    }

    @Override
    public HashMap<String, String> modelToHashMap(AbsenceRequestForm model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("requestFormId", model.getRequestFormId());
        map.put("studentId", model.getStudent().getStudentId());
        map.put("advisorId", model.getAdvisor().getAdvisorId());
        map.put("status", String.valueOf(model.getStatus()));
        map.put("program", model.getProgram());
        map.put("academicYear", model.getAcademicYear());
        map.put("phoneNumber", model.getPhoneNumber());
        map.put("facebookID", model.getFacebookID());
        map.put("lineID", model.getLineID());
        map.put("absenceType", model.getAbsenceType());
        map.put("absenceDateFrom", model.getAbsenceDateFrom().toString());
        map.put("absenceDateUntil", model.getAbsenceDateUntil().toString());
        return map;
    }

    @Override
    public ArrayList<AbsenceRequestForm> getCollectionArrayList(RequestFormList collection) {
        ArrayList<AbsenceRequestForm> formList = new ArrayList<>();
        for (RequestForm form : collection.getRequestForms()) {
            if (form instanceof AbsenceRequestForm) {
                formList.add((AbsenceRequestForm) form);
            }
        }
        return formList;
    }
}
