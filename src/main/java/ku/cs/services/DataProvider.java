package ku.cs.services;

import ku.cs.models.collections.*;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Admin;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.User;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.datahandle.DataSourceReader;
import ku.cs.services.datahandle.DataSourceWriter;
import ku.cs.services.datasource.*;

import java.util.ArrayList;

public class DataProvider {

    private static DataProvider dataProvider;
    private DepartmentList departmentList;
    private FacultyList facultyList;
    private RequestFormList allRequestFormList;
    private AdvisorList advisorList;
    private StudentList studentList;
    private DepartmentOfficerList departmentOfficerList;
    private FacultyOfficerList facultyOfficerList;
    private DepartmentApproverList departmentApproverList;
    private FacultyApproverList facultyApproverList;
    private UserList allUsers;


    public DataProvider() {
        this.facultyList = getFacultyList();
        this.departmentList = getDepartmentList();
        this.studentList = getStudentList();
        this.allUsers=loadUserList();
        this.allRequestFormList = getRequestFormList();
    }

    public static DataProvider getDataProvider(){
        if (dataProvider==null){
            dataProvider=new DataProvider();
        }
        return dataProvider;

    }

    public User setDataByRole(String username,String password) throws Exception {
        User user = allUsers.login(username, password);
        if (user==null) throw (new Exception("ไม่มีผู้ใช้ในระบบ"));
        else if (user.getStatus()==false) throw (new Exception("ไม่มีสิทธิ์เข้าใช้งาน"));
        String role = user.getRole();

        switch (role) {
            case "admin":
                return setAdminData((Admin) user);
            case "advisor": {
                return setAdvisorData((Advisor) user);
            }
            case "student": {
                return setStudentData((Student) user);
            }
            case "departmentOfficer": {
                return setDepartmentOfficerData((DepartmentOfficer) user);
            }
            case "facultyOfficer": {
                return setFacultyOfficerData((FacultyOfficer) user);
            }
        }
        return null;
    }

    // set data
    private User setAdvisorData(Advisor user){
        RequestFormList requestFormList = getRequestFormList().findRequestFormsByAdvisor(user).findRequestFormsByStatus(RequestForm.Status.PENDING_TO_ADVISOR);
        user.setRequestFormList(requestFormList);
        StudentList allStudent = getStudentList();
        ArrayList<Student> students= new ArrayList<>();
        for (Student student:allStudent.getStudents()){
            if (student.getAdvisor()==null) continue;
            if(student.getAdvisor().equals(user)){
                students.add(student);
            }
        }
        StudentList studentList = new StudentList();
        studentList.setStudents(students);
        user.setStudents(studentList);
        user.setRequestFormList(requestFormList);
        return user;
    }

    private Student setStudentData(Student user){
        RequestFormList requestFormList = getRequestFormList().findRequestFormsByStudent(user);
        user.setRequestFormList(requestFormList);

        return user;
    }

    private DepartmentOfficer setDepartmentOfficerData(DepartmentOfficer departmentOfficer){
        RequestFormList requestFormList = getRequestFormList().findRequestFormsByDepartment(departmentOfficer.getDepartment());

        RequestFormList targetRequestForms = requestFormList.findRequestFormsByStatus(RequestForm.Status.PENDING_TO_DEPARTMENT);

        StudentList studentList = getStudentList();

        StudentList studentInDepartment = new StudentList();
        for (Student student : studentList.getStudents()) {
            if (student.getDepartment().equals(departmentOfficer.getDepartment())) {
                studentInDepartment.addStudent(student);
            }
        }

        departmentOfficer.setStudentList(studentList);
        departmentOfficer.setStudentListWriter(new PermissionControlledCollection<>(studentList.getStudents(),studentList.getStudents()));


        departmentOfficer.setRequestFormList(targetRequestForms);

        DepartmentApproverList departmentApproverList=getDepartmentApproverList();
        departmentOfficer.setDepartmentApproverList(departmentApproverList);

        AdvisorList newAdvisorList = new AdvisorList();
        for (Advisor advisor : advisorList.getAdvisors()) {
            if (advisor.getDepartment().isId(departmentOfficer.getDepartment().getId())) {
                newAdvisorList.addAdvisor(advisor);
            }
        }

        departmentOfficer.setAdvisorList(newAdvisorList);
        return departmentOfficer;
    }

    private FacultyOfficer setFacultyOfficerData(FacultyOfficer facultyOfficer){
        RequestFormList requestFormList = getRequestFormList().findRequestFormsByFaculty(facultyOfficer.getFaculty()).findRequestFormsByStatus(RequestForm.Status.PENDING_TO_FACULTY);
        facultyOfficer.setRequestFormList(requestFormList);
        FacultyApproverList facultyApproverList=getFacultyApproverList();

        facultyOfficer.setFacultyApproverList(facultyApproverList);
        return facultyOfficer;
    }

    private Admin setAdminData(Admin admin){
        FacultyOfficerList facultyOfficerList=getFacultyOfficerList();
        DepartmentOfficerList departmentOfficerList=getDepartmentOfficerList();
        StudentList studentList=getStudentList();
        AdvisorList advisorList=getAdvisorList();
        DepartmentList departmentList=getDepartmentList();
        FacultyList facultyList=getFacultyList();
        RequestFormList requestFormList=getRequestFormList();

        admin.setFacultyOfficerList(facultyOfficerList);
        admin.setDepartmentOfficerList(departmentOfficerList);
        admin.setStudentList(studentList);
        admin.setAdvisorList(advisorList);
        admin.setDepartmentList(departmentList);
        admin.setFacultyList(facultyList);
        admin.setRequestFormList(requestFormList);
        return admin;
    }

    //get data
    private DepartmentApproverList getDepartmentApproverList(){
        if (departmentApproverList==null) departmentApproverList=loadDepartmentApproverList();
        return departmentApproverList;
    }

    private FacultyApproverList getFacultyApproverList(){
        if (facultyApproverList==null) facultyApproverList=loadFacultyApproverList();
        return facultyApproverList;
    }

    public RequestFormList getRequestFormList() {
        if (allRequestFormList == null) {
            loadRequestFormList();
        }
        return allRequestFormList;
    }

    private AdvisorList getAdvisorList() {
        if (advisorList == null) {
            loadAdvisorList();
        }
        return advisorList;
    }

    public StudentList getStudentList() {
        if (studentList == null) {
            loadStudentList();
        }
        return studentList;
    }

    private FacultyOfficerList getFacultyOfficerList() {
        if (facultyOfficerList == null) {
            loadFacultyOfficerList();
        }
        return facultyOfficerList;
    }

    private DepartmentOfficerList getDepartmentOfficerList() {
        if (departmentOfficerList == null) {
            loadDepartmentOfficerList();
        }
        return departmentOfficerList;
    }


    private FacultyList getFacultyList() {
        if (facultyList == null) {
            loadFacultyList();
        }
        return facultyList;
    }

    private DepartmentList getDepartmentList() {
        if (departmentList == null) {
            loadDepartmentList();
        }
        return departmentList;
    }


    private UserList loadUserList(){
        allUsers=loadAdmin();
        StudentList studentList=getStudentList();
        AdvisorList advisorList=getAdvisorList();
        FacultyOfficerList facultyOfficerList=getFacultyOfficerList();
        DepartmentOfficerList departmentOfficerList=getDepartmentOfficerList();
        allUsers.addUsers(studentList.getStudents());
        allUsers.addUsers(advisorList.getAdvisors());
        allUsers.addUsers(facultyOfficerList.getOfficers());
        allUsers.addUsers(departmentOfficerList.getOfficers());
        return allUsers;
    }
    private UserList loadAdmin(){
        AdminDataSource adminDataSource = new AdminDataSource(
                getStudentList(),
                getDepartmentList(),
                getFacultyList(),
                getAdvisorList(),
                getDepartmentOfficerList(),
                getFacultyOfficerList());
        DataSourceReader<UserList, Admin> adminDataSourceReader = new DataSourceReader<>(adminDataSource);
        UserList admin = adminDataSourceReader.readData();
        return admin;
    }
    private AdvisorList loadAdvisorList(){
        AdvisorDataSource advisorDataSource = new AdvisorDataSource(departmentList);
        DataSourceReader<AdvisorList, Advisor> advisorDataSourceReader = new DataSourceReader<>(advisorDataSource);
        advisorList = advisorDataSourceReader.readData();
        return  advisorList;
    }

    private RequestFormActionHistoryList getRequestFormApprovingHistoryList() {
        RequestFormApprovingHistoryDataSource requestFormApprovingHistoryDataSource = new RequestFormApprovingHistoryDataSource();
        DataSourceReader<RequestFormActionHistoryList, RequestFormActionHistory> requestFormApprovingHistoryDataSourceReader = new DataSourceReader<>(requestFormApprovingHistoryDataSource);
        RequestFormActionHistoryList requestFormActionHistoryList = requestFormApprovingHistoryDataSourceReader.readData();
        return requestFormActionHistoryList;
    }

    private void loadStudentList(){
        AdvisorList advisorList=getAdvisorList();
        DepartmentList departmentList=getDepartmentList();
        StudentDataSource studentDataSource = new StudentDataSource(advisorList,departmentList);
        DataSourceReader<StudentList, Student> studentDataSourceReader = new DataSourceReader<>(studentDataSource);
        studentList = studentDataSourceReader.readData();

        for (Student student:studentList.getStudents()){
            RequestFormList target=getRequestFormList().findRequestFormsByStudent(student);
            student.setRequestFormList(target);
        }
    }

    private FacultyApproverList loadFacultyApproverList(){
        FacultyApproverDataSource facultyApproverDataSource = new FacultyApproverDataSource(facultyList);
        DataSourceReader<FacultyApproverList, FacultyApprover> facultyApproverDataSourceReader = new DataSourceReader<>(facultyApproverDataSource);
        FacultyApproverList facultyApproverList = facultyApproverDataSourceReader.readData();
        return facultyApproverList;
    }

    private DepartmentApproverList loadDepartmentApproverList(){
        DepartmentApproverDataSource departmentApproverDataSource = new DepartmentApproverDataSource(departmentList);
        DataSourceReader<DepartmentApproverList, DepartmentApprover> departmentApproverDataSourceReader = new DataSourceReader<>(departmentApproverDataSource);
        DepartmentApproverList departmentApproverList = departmentApproverDataSourceReader.readData();
        return departmentApproverList;
    }

    private void loadFacultyOfficerList(){
        FacultyOfficerDataSource facultyOfficerDataSource=new FacultyOfficerDataSource(facultyList);
        DataSourceReader<FacultyOfficerList,FacultyOfficer> facultyOfficerDataSourceReader = new DataSourceReader<>(facultyOfficerDataSource);
        facultyOfficerList = facultyOfficerDataSourceReader.readData();
    }

    private void loadDepartmentOfficerList(){
        StudentList studentList=getStudentList();
        DepartmentList departmentList=getDepartmentList();
        DepartmentOfficerDataSource departmentOfficerDataSource = new DepartmentOfficerDataSource(departmentList,studentList);
        DataSourceReader<DepartmentOfficerList,DepartmentOfficer> departmentOfficerDataSourceReader = new DataSourceReader<>(departmentOfficerDataSource);
        departmentOfficerList = departmentOfficerDataSourceReader.readData();
    }

    private void loadFacultyList(){
        FacultyDataSource facultyDataSource = new FacultyDataSource();
        DataSourceReader<FacultyList, Faculty> facultyDataSourceReader = new DataSourceReader<>(facultyDataSource);
        facultyList = facultyDataSourceReader.readData();
    }

    private void loadDepartmentList(){
        FacultyList facultyList=getFacultyList();
        DepartmentDataSource departmentDataSource = new DepartmentDataSource(facultyList);
        DataSourceReader<DepartmentList, Department> departmentDataSourceReader = new DataSourceReader<>(departmentDataSource);
        departmentList = departmentDataSourceReader.readData();

        for (Faculty faculty:facultyList.getFaculties()) {
            DepartmentList targetDepartmentList=new DepartmentList();
            for (Department department:departmentList.getDepartments()) {
                if (department.getFaculty().equals(faculty)) {
                    targetDepartmentList.addDepartment(department);
                }
            }
            faculty.setDepartmentList(targetDepartmentList);
        }
    }

    private void loadRequestFormList(){
        allRequestFormList=new RequestFormList();
        StudentList studentList=getStudentList();
        AdvisorList advisorList=getAdvisorList();
        AbsenceRequestFormDataSource absenceDataSource=new AbsenceRequestFormDataSource(studentList,advisorList);
        DataSourceReader<RequestFormList, AbsenceRequestForm> absenceFormDataSourceReader = new DataSourceReader<>(absenceDataSource);
        RequestFormList absenseRequestForms=absenceFormDataSourceReader.readData();

        AddDropRequestFormDataSource addDropDataSource=new AddDropRequestFormDataSource(studentList,advisorList,new CourseList());
        DataSourceReader<RequestFormList, AddDropRequestForm> addDropFormDataSourceReader = new DataSourceReader<>(addDropDataSource);
        RequestFormList addDropRequestForms=addDropFormDataSourceReader.readData();

        CoEnrollRequestFormDataSource coEnrollDataSource=new CoEnrollRequestFormDataSource(studentList,advisorList);
        DataSourceReader<RequestFormList,CoEnrollRequestForm> coEnrollFormDataSourceReader = new DataSourceReader<>(coEnrollDataSource);
        RequestFormList coEnrollRequestForms=coEnrollFormDataSourceReader.readData();
        allRequestFormList.addRequestFormList(absenseRequestForms);
        allRequestFormList.addRequestFormList(addDropRequestForms);
        allRequestFormList.addRequestFormList(coEnrollRequestForms);
        allRequestFormList.applyRequestFormApprovingHistoryList(getRequestFormApprovingHistoryList());

        AppDataSource appDataSource = new AppDataSource();
        DataSourceReader<AddDataList, AppData> dataSourceReader = new DataSourceReader<>(appDataSource);
        AddDataList addDataList = dataSourceReader.readData();
        for (AppData appData : addDataList.getDataList()) {
            if (appData.isKey("absenceFormNumber")) {
                AbsenceRequestForm.latestRequestFormId = Integer.parseInt(appData.getValue());
            } else if (appData.isKey("addDropFormNumber")) {
                AddDropRequestForm.latestRequestFormId = Integer.parseInt(appData.getValue());
            } else if (appData.isKey("coEnrollFormNumber")) {
                CoEnrollRequestForm.latestRequestFormId = Integer.parseInt(appData.getValue());
            }
        }

    }


    //save
    public void saveUser(User user){
        String role = user.getRole();

        switch (role) {
            case "advisor": {
                saveAdvisor();
                saveForms();
                break;
            }
            case "student": {
                saveStudent();
                saveForms();
                break;
            }
            case "departmentOfficer": {
                saveDepartmentOfficer();
                saveDepartmentApprover();
                saveForms();
                saveStudent();
                saveAdvisor();
                break;
            }
            case "facultyOfficer": {
                saveFacultyOfficer();
                saveFacultyApprover();
                saveForms();
                break;
            }
            case "admin":{
                saveAdmin();
                saveAdvisor();
                saveStudent();
                saveDepartmentOfficer();
                saveFacultyOfficer();
                saveDepartmentList();
                saveFacultyList();
                break;
            }
        }
//        return null;
    }

    private void saveDepartmentApprover() {
        DepartmentApproverDataSource departmentApproverDataSource=new DepartmentApproverDataSource(departmentList);
        DataSourceWriter<DepartmentApproverList,DepartmentApprover> departmentApproverDataSourceWriter=new DataSourceWriter<>(departmentApproverDataSource);
        departmentApproverDataSourceWriter.writeData(departmentApproverList);
    }

    private void saveFacultyApprover() {
        FacultyApproverDataSource facultyApproverDataSource=new FacultyApproverDataSource(facultyList);
        DataSourceWriter<FacultyApproverList,FacultyApprover> facultyApproverDataSourceWriter=new DataSourceWriter<>(facultyApproverDataSource);
        facultyApproverDataSourceWriter.writeData(facultyApproverList);
    }

    private void saveAdvisor() {
        AdvisorDataSource advisorDataSource=new AdvisorDataSource(departmentList);
        DataSourceWriter<AdvisorList,Advisor> advisorDataSourceWriter = new DataSourceWriter<>(advisorDataSource);
        advisorDataSourceWriter.writeData(advisorList);
    }

    private void saveStudent() {
        StudentDataSource studentDataSource=new StudentDataSource(advisorList,departmentList);
        DataSourceWriter<StudentList,Student> studentDataSourceWriter = new DataSourceWriter<>(studentDataSource);
        studentDataSourceWriter.writeData(studentList);
    }

    private void saveDepartmentOfficer() {
        DepartmentOfficerDataSource departmentOfficerDataSource=new DepartmentOfficerDataSource(departmentList,studentList);
        DataSourceWriter<DepartmentOfficerList,DepartmentOfficer> departmentOfficerDataSourceWriter = new DataSourceWriter<>(departmentOfficerDataSource);
        departmentOfficerDataSourceWriter.writeData(departmentOfficerList);
    }

    private void saveFacultyOfficer() {
        FacultyOfficerDataSource facultyOfficerDataSource=new FacultyOfficerDataSource(facultyList);
        DataSourceWriter<FacultyOfficerList,FacultyOfficer> facultyOfficerListFacultyDataSourceWriter=new DataSourceWriter<>(facultyOfficerDataSource);
        facultyOfficerListFacultyDataSourceWriter.writeData(facultyOfficerList);
    }

    private void saveAdmin() {
        AdminDataSource adminDataSource=new AdminDataSource(studentList,departmentList,facultyList,advisorList,departmentOfficerList,facultyOfficerList);
        DataSourceWriter<UserList,Admin> adminDataSourceWriter = new DataSourceWriter<>(adminDataSource);
        adminDataSourceWriter.writeData(allUsers);
    }

    private void saveForms(){
        RequestFormList absenceRequestFormList=new RequestFormList();
        RequestFormList addDropRequestFormList=new RequestFormList();
        RequestFormList coEnrollpRequestFormList=new RequestFormList();
        RequestFormActionHistoryList requestFormActionHistoryList = new RequestFormActionHistoryList();
        for (RequestForm requestForm : allRequestFormList.getRequestForms()){
            for (RequestFormActionHistory requestFormActionHistory : requestForm.getRequestFormActionHistoryList().getRequestFormApprovingHistories()){
                requestFormActionHistoryList.addRequestFormApprovingHistory(requestFormActionHistory);
            }
            if( requestForm instanceof AbsenceRequestForm){
                absenceRequestFormList.addRequestForm((AbsenceRequestForm)requestForm);
            }
            if( requestForm instanceof AddDropRequestForm){
                addDropRequestFormList.addRequestForm((AddDropRequestForm)requestForm);
            }
            if( requestForm instanceof CoEnrollRequestForm){
                coEnrollpRequestFormList.addRequestForm((CoEnrollRequestForm)requestForm);
            }
        }
        AbsenceRequestFormDataSource absenceDataSource=new AbsenceRequestFormDataSource(studentList,advisorList);
        DataSourceWriter<RequestFormList, AbsenceRequestForm> writerAbsenceForm = new DataSourceWriter<>(absenceDataSource);
        writerAbsenceForm.writeData(absenceRequestFormList);

        AddDropRequestFormDataSource addDropDataSource=new AddDropRequestFormDataSource(studentList,advisorList,new CourseList());
        DataSourceWriter<RequestFormList, AddDropRequestForm> writerCoEnrollForm = new DataSourceWriter<>(addDropDataSource);
        writerCoEnrollForm.writeData(addDropRequestFormList);

        CoEnrollRequestFormDataSource coEnrollDataSource=new CoEnrollRequestFormDataSource(studentList,advisorList);
        DataSourceWriter<RequestFormList, CoEnrollRequestForm> writerAddDrop = new DataSourceWriter<>(coEnrollDataSource);
        writerAddDrop.writeData(coEnrollpRequestFormList);

        RequestFormApprovingHistoryDataSource requestFormApprovingHistoryDataSource = new RequestFormApprovingHistoryDataSource();
        DataSourceWriter<RequestFormActionHistoryList, RequestFormActionHistory> requestFormApprovingHistoryDataSourceWriter = new DataSourceWriter<>(requestFormApprovingHistoryDataSource);
        requestFormApprovingHistoryDataSourceWriter.writeData(requestFormActionHistoryList);

        AddDataList addDataList = new AddDataList();
        addDataList.addData(new AppData("absenceFormNumber", String.valueOf(AbsenceRequestForm.latestRequestFormId)));
        addDataList.addData(new AppData("addDropFormNumber", String.valueOf(AddDropRequestForm.latestRequestFormId)));
        addDataList.addData(new AppData("coEnrollFormNumber", String.valueOf(CoEnrollRequestForm.latestRequestFormId)));
        AppDataSource appDataSource = new AppDataSource();
        DataSourceWriter<AddDataList, AppData> dataSourceWriter = new DataSourceWriter<>(appDataSource);
        dataSourceWriter.writeData(addDataList);
    }

    private void saveFacultyList(){
        FacultyDataSource facultyDataSource = new FacultyDataSource();
        DataSourceWriter<FacultyList,Faculty> facultyDataSourceWriter = new DataSourceWriter<>(facultyDataSource);
        facultyDataSourceWriter.writeData(facultyList);
    }

    private void saveDepartmentList(){
        DepartmentDataSource departmentDataSource = new DepartmentDataSource(facultyList);
        DataSourceWriter<DepartmentList,Department> departmentDataSourceWriter = new DataSourceWriter<>(departmentDataSource);
        departmentDataSourceWriter.writeData(departmentList);
    }

}
