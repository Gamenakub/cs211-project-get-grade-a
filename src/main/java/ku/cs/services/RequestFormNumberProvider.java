package ku.cs.services;

import ku.cs.models.ApplicationRecord;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.collections.ApplicationRecordList;
import ku.cs.models.collections.CourseList;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.datahandle.DataSourceReader;
import ku.cs.services.datahandle.DataSourceWriter;
import ku.cs.services.datasource.ApplicationRecordDataSource;

import java.io.IOException;
import java.time.LocalDate;

public class RequestFormNumberProvider {
    private static RequestFormNumberProvider instance;
    private final DataSourceWriter<ApplicationRecordList, ApplicationRecord> dataSourceWriter;
    private final DataSourceReader<ApplicationRecordList, ApplicationRecord> dataSourceReader;
    private int absenceRequestFormNumber;
    private int addDropRequestFormNumber;
    private int coEnrollRequestFormNumber;
    private ApplicationRecordList applicationRecordList;

    private RequestFormNumberProvider() {
        ApplicationRecordDataSource applicationRecordDataSource = new ApplicationRecordDataSource();
        dataSourceReader = new DataSourceReader<>(applicationRecordDataSource);
        dataSourceWriter = new DataSourceWriter<>(applicationRecordDataSource);
        loadData();
    }

    public static RequestFormNumberProvider getInstance() {
        if (instance == null) {
            instance = new RequestFormNumberProvider();
        }
        return instance;
    }

    private void loadData() {
        applicationRecordList = dataSourceReader.readData();
        absenceRequestFormNumber = 1;
        if (applicationRecordList.findDataByKey("absenceFormNumber") != null) {
            absenceRequestFormNumber = Integer.parseInt(applicationRecordList.findDataByKey("absenceFormNumber").getValue());
        }
        else{
            applicationRecordList.addData(new ApplicationRecord("absenceFormNumber", "1"));
        }
        addDropRequestFormNumber = 1;
        if (applicationRecordList.findDataByKey("addDropFormNumber") != null) {
            addDropRequestFormNumber = Integer.parseInt(applicationRecordList.findDataByKey("addDropFormNumber").getValue());
        }
        else{
            applicationRecordList.addData(new ApplicationRecord("addDropFormNumber", "1"));
        }
        coEnrollRequestFormNumber = 1;
        if (applicationRecordList.findDataByKey("coEnrollFormNumber") != null) {
            coEnrollRequestFormNumber = Integer.parseInt(applicationRecordList.findDataByKey("coEnrollFormNumber").getValue());
        }
        else{
            applicationRecordList.addData(new ApplicationRecord("coEnrollFormNumber", "1"));
        }
    }

    private void updateToAppData() {
        applicationRecordList.findDataByKey("absenceFormNumber").setValue(String.valueOf(absenceRequestFormNumber));
        applicationRecordList.findDataByKey("addDropFormNumber").setValue(String.valueOf(addDropRequestFormNumber));
        applicationRecordList.findDataByKey("coEnrollFormNumber").setValue(String.valueOf(coEnrollRequestFormNumber));
    }

    public void saveData() throws IOException {
        updateToAppData();
        dataSourceWriter.writeData(applicationRecordList);
    }

    public AddDropRequestForm createAddRequestFormNumber(Student student) {
        return new AddDropRequestForm("add", student, student.getAdvisor(), RequestForm.Status.CREATING, new CourseList(), "ไทย", "", "", "", "", "");
    }

    public AddDropRequestForm createDropRequestFormNumber(Student student) {
        return new AddDropRequestForm("drop", student, student.getAdvisor(), RequestForm.Status.CREATING, new CourseList(), "ไทย", "", "", "", "", "");
    }

    public AbsenceRequestForm createAbsenceRequestFormNumber(Student student) {
        return new AbsenceRequestForm(student, student.getAdvisor(), RequestForm.Status.CREATING, "", "", "", "", "", LocalDate.now(), LocalDate.now(), new CourseList(), "", "");
    }

    public CoEnrollRequestForm createCoEnrollRequestFormNumber(Student student) {
        return new CoEnrollRequestForm(student, student.getAdvisor(), "", RequestForm.Status.CREATING, "", "", "", "", "", "", "", "", "", "");
    }

    public void applyFormNumber(AddDropRequestForm form) {
        RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory("พถ." + addDropRequestFormNumber++, form.getStudent().getFullName(), RequestForm.Status.PENDING_TO_ADVISOR, "นิสิต");
        form.setStatus(requestFormActionHistory);
        form.getCourseList().setAllCourseRequestFormId(requestFormActionHistory.getRequestId());
        form.setRequestFormNumber(requestFormActionHistory.getRequestId());
    }

    public void applyFormNumber(AbsenceRequestForm form) {
        RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory("ขร." + absenceRequestFormNumber++, form.getStudent().getFullName(), RequestForm.Status.PENDING_TO_ADVISOR, "นิสิต");
        form.setStatus(requestFormActionHistory);
        form.getCourseList().setAllCourseRequestFormId(requestFormActionHistory.getRequestId());
        form.setRequestFormNumber(requestFormActionHistory.getRequestId());
    }

    public void applyFormNumber(CoEnrollRequestForm form) {
        RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory("ลร." + coEnrollRequestFormNumber++, form.getStudent().getFullName(), RequestForm.Status.PENDING_TO_ADVISOR, "นิสิต");
        form.setStatus(requestFormActionHistory);
        form.setRequestFormNumber(requestFormActionHistory.getRequestId());
    }
}
