package ku.cs.services;

import ku.cs.models.*;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

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
    private CourseList courseList;

    private DataProvider() {
        facultyList = getFacultyList();
        departmentList = getDepartmentList();
        studentList = getStudentList();
        allUsers = getUserList();
        allRequestFormList = getRequestFormList();
        advisorList = getAdvisorList();
        departmentOfficerList = getDepartmentOfficerList();
        facultyApproverList = getFacultyApproverList();
        departmentApproverList=getDepartmentApproverList();
        facultyOfficerList = getFacultyOfficerList();
        courseList = getCourseList();
    }

    private void reloadData(){
        facultyList = getFacultyList();
        departmentList = getDepartmentList();
        studentList = getStudentList();
        allUsers = getUserList();
        advisorList = getAdvisorList();
        allRequestFormList = getRequestFormList();
        departmentOfficerList = getDepartmentOfficerList();
        facultyApproverList = getFacultyApproverList();
        departmentApproverList=getDepartmentApproverList();
        facultyOfficerList = getFacultyOfficerList();
        courseList = getCourseList();
    }

    public static DataProvider getDataProvider() {
        if (dataProvider == null) {
            dataProvider = new DataProvider();
        }
        return dataProvider;

    }

    public User setDataByRole(String username, String password) {
        reloadData();
        User user = allUsers.login(username, password);
        if (user == null) {
            throw new NoSuchElementException("ไม่มีผู้ใช้ในระบบ");
        }
        String role = user.getRole();

        return switch (role) {
            case "admin" -> setAdminData((Admin) user);
            case "advisor" -> setAdvisorData((Advisor) user);
            case "student" -> setStudentData((Student) user);
            case "departmentOfficer" -> setDepartmentOfficerData((DepartmentOfficer) user);
            case "facultyOfficer" -> setFacultyOfficerData((FacultyOfficer) user);
            default -> null;
        };
    }
    private User setAdvisorData(Advisor user) {
        RequestFormList requestFormList = getRequestFormList().findRequestFormsByAdvisor(user).findRequestFormsByStatus(RequestForm.Status.PENDING_TO_ADVISOR);
        user.setRequestFormList(requestFormList);
        StudentList allStudent = getStudentList();
        ArrayList<Student> students = new ArrayList<>();
        for (Student student : allStudent.getStudents()) {
            if (student.getAdvisor() == null) continue;
            if (student.getAdvisor().equals(user)) {
                students.add(student);
            }
        }
        StudentList studentList = new StudentList();
        studentList.setStudents(students);
        user.setStudents(studentList);
        user.setRequestFormList(requestFormList);
        return user;
    }

    private Student setStudentData(Student user) {
        RequestFormList requestFormList = getRequestFormList().findRequestFormsByStudent(user);
        user.setRequestFormList(requestFormList);
        return user;
    }

    private DepartmentOfficer setDepartmentOfficerData(DepartmentOfficer departmentOfficer) {
        RequestFormList requestFormList = getRequestFormList().findRequestFormsByDepartment(departmentOfficer.getDepartment());
        RequestFormList targetRequestForms = requestFormList.findRequestFormsByStatus(RequestForm.Status.PENDING_TO_DEPARTMENT);
        StudentList studentList = getStudentList();
        StudentList studentInDepartment = new StudentList();
        for (Student student : studentList.getStudents()) {
            if (student.getDepartment().equals(departmentOfficer.getDepartment())) {
                studentInDepartment.addStudent(student);
            }
        }
        departmentOfficer.setStudentList(studentInDepartment);
        departmentOfficer.setRequestFormList(targetRequestForms);

        AdvisorList newAdvisorList = new AdvisorList();
        for (Advisor advisor : advisorList.getAdvisors()) {
            if (advisor.getDepartment().isId(departmentOfficer.getDepartment().getId())) {
                newAdvisorList.addAdvisor(advisor);
            }
        }

        departmentOfficer.setAdvisorList(newAdvisorList);
        return departmentOfficer;
    }

    private FacultyOfficer setFacultyOfficerData(FacultyOfficer facultyOfficer) {
        RequestFormList requestFormList = getRequestFormList().findRequestFormsByFaculty(facultyOfficer.getFaculty()).findRequestFormsByStatus(RequestForm.Status.PENDING_TO_FACULTY);
        facultyOfficer.setRequestFormList(requestFormList);
        return facultyOfficer;
    }

    private Admin setAdminData(Admin admin) {
        FacultyOfficerList facultyOfficerList = getFacultyOfficerList();
        DepartmentOfficerList departmentOfficerList = getDepartmentOfficerList();
        StudentList studentList = getStudentList();
        AdvisorList advisorList = getAdvisorList();
        DepartmentList departmentList = getDepartmentList();
        FacultyList facultyList = getFacultyList();
        RequestFormList requestFormList = getRequestFormList();
        admin.setFacultyOfficerList(facultyOfficerList);
        admin.setDepartmentOfficerList(departmentOfficerList);
        admin.setStudentList(studentList);
        admin.setAdvisorList(advisorList);
        admin.setDepartmentList(departmentList);
        admin.setFacultyList(facultyList);
        admin.setRequestFormList(requestFormList);
        return admin;
    }
    private DepartmentApproverList getDepartmentApproverList() {
        if (departmentApproverList == null) departmentApproverList = loadDepartmentApproverList();
        return departmentApproverList;
    }

    private FacultyApproverList getFacultyApproverList() {
        if (facultyApproverList == null) facultyApproverList = loadFacultyApproverList();
        return facultyApproverList;
    }

    private RequestFormList getRequestFormList() {
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

    private UserList getUserList() {
        if (allUsers == null) {
            loadUserList();
        }
        return allUsers;
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

    private CourseList getCourseList() {
        if (courseList == null) {
            loadCourseList();
        }
        return courseList;
    }

    private void loadUserList() {
        loadAdmin();
        StudentList studentList = getStudentList();
        AdvisorList advisorList = getAdvisorList();
        FacultyOfficerList facultyOfficerList = getFacultyOfficerList();
        DepartmentOfficerList departmentOfficerList = getDepartmentOfficerList();
        allUsers.addUsers(studentList.getStudents());
        allUsers.addUsers(advisorList.getAdvisors());
        allUsers.addUsers(facultyOfficerList.getOfficers());
        allUsers.addUsers(departmentOfficerList.getOfficers());
    }

    private void loadAdmin() {
        AdminDataSource adminDataSource = new AdminDataSource(
                getStudentList(),
                getDepartmentList(),
                getFacultyList(),
                getAdvisorList(),
                getDepartmentOfficerList(),
                getFacultyOfficerList());
        DataSourceReader<UserList, Admin> adminDataSourceReader = new DataSourceReader<>(adminDataSource);
        allUsers = adminDataSourceReader.readData();
    }

    private void loadAdvisorList() {
        AdvisorDataSource advisorDataSource = new AdvisorDataSource(getDepartmentList());
        DataSourceReader<AdvisorList, Advisor> advisorDataSourceReader = new DataSourceReader<>(advisorDataSource);
        advisorList = advisorDataSourceReader.readData();
    }

    private RequestFormActionHistoryList getRequestFormApprovingHistoryList() {
        RequestFormApprovingHistoryDataSource requestFormApprovingHistoryDataSource = new RequestFormApprovingHistoryDataSource();
        DataSourceReader<RequestFormActionHistoryList, RequestFormActionHistory> requestFormApprovingHistoryDataSourceReader = new DataSourceReader<>(requestFormApprovingHistoryDataSource);
        return requestFormApprovingHistoryDataSourceReader.readData();
    }

    private void loadStudentList() {
        AdvisorList advisorList = getAdvisorList();
        DepartmentList departmentList = getDepartmentList();
        StudentDataSource studentDataSource = new StudentDataSource(advisorList, departmentList);
        DataSourceReader<StudentList, Student> studentDataSourceReader = new DataSourceReader<>(studentDataSource);
        studentList = studentDataSourceReader.readData();

        for (Student student : studentList.getStudents()) {
            RequestFormList target = getRequestFormList().findRequestFormsByStudent(student);
            student.setRequestFormList(target);
        }
    }

    private FacultyApproverList loadFacultyApproverList() {
        FacultyApproverDataSource facultyApproverDataSource = new FacultyApproverDataSource(getFacultyList());
        DataSourceReader<FacultyApproverList, FacultyApprover> facultyApproverDataSourceReader = new DataSourceReader<>(facultyApproverDataSource);
        return facultyApproverDataSourceReader.readData();
    }

    private DepartmentApproverList loadDepartmentApproverList() {
        DepartmentApproverDataSource departmentApproverDataSource = new DepartmentApproverDataSource(departmentList);
        DataSourceReader<DepartmentApproverList, DepartmentApprover> departmentApproverDataSourceReader = new DataSourceReader<>(departmentApproverDataSource);
        return departmentApproverDataSourceReader.readData();
    }

    private void loadFacultyOfficerList() {
        FacultyOfficerDataSource facultyOfficerDataSource = new FacultyOfficerDataSource(getFacultyList());
        DataSourceReader<FacultyOfficerList, FacultyOfficer> facultyOfficerDataSourceReader = new DataSourceReader<>(facultyOfficerDataSource);
        facultyOfficerList = facultyOfficerDataSourceReader.readData();
        getFacultyApproverList();
        for(FacultyOfficer facultyOfficer : facultyOfficerList.getFacultyOfficers()){
            facultyOfficer.getFacultyApproverList().getApprovers().clear();
            for(FacultyApprover facultyApprover : facultyApproverList.getApprovers()) {
                if(facultyApprover.getFaculty().equals(facultyOfficer.getFaculty()) && !facultyOfficer.getFacultyApproverList().getApprovers().contains(facultyApprover)) {
                    facultyOfficer.getFacultyApproverList().addApprover(facultyApprover);
                }
            }
        }
    }

    private void loadDepartmentOfficerList() {
        DepartmentOfficerDataSource departmentOfficerDataSource = new DepartmentOfficerDataSource(getDepartmentList(), getStudentList());
        DataSourceReader<DepartmentOfficerList, DepartmentOfficer> departmentOfficerDataSourceReader = new DataSourceReader<>(departmentOfficerDataSource);
        departmentOfficerList = departmentOfficerDataSourceReader.readData();
        getDepartmentApproverList();
        for(DepartmentOfficer departmentOfficer : departmentOfficerList.getDepartmentOfficers()){
            departmentOfficer.getDepartmentApproverList().clear();
            for(DepartmentApprover departmentApprover : departmentApproverList.getApprovers()){
                if(departmentOfficer.getDepartment().equals(departmentApprover.getDepartment()) && !departmentOfficer.getDepartmentApproverList().getApprovers().contains(departmentApprover)){
                    departmentOfficer.getDepartmentApproverList().addApprover(departmentApprover);
                }
            }
        }
    }

    private void loadFacultyList() {
        FacultyDataSource facultyDataSource = new FacultyDataSource();
        DataSourceReader<FacultyList, Faculty> facultyDataSourceReader = new DataSourceReader<>(facultyDataSource);
        facultyList = facultyDataSourceReader.readData();
    }

    private void loadDepartmentList() {
        FacultyList facultyList = getFacultyList();
        DepartmentDataSource departmentDataSource = new DepartmentDataSource(facultyList);
        DataSourceReader<DepartmentList, Department> departmentDataSourceReader = new DataSourceReader<>(departmentDataSource);
        departmentList = departmentDataSourceReader.readData();

        for (Faculty faculty : facultyList.getFaculties()) {
            DepartmentList targetDepartmentList = new DepartmentList();
            for (Department department : departmentList.getDepartments()) {
                if (department.getFaculty().equals(faculty)) {
                    targetDepartmentList.addDepartment(department);
                }
            }
            faculty.setDepartmentList(targetDepartmentList);
        }
    }

    private void loadCourseList() {
        courseList = new CourseList();
        CourseDataSource courseDataSource = new CourseDataSource();
        DataSourceReader<CourseList, Course> courseDataSourceReader = new DataSourceReader<>(courseDataSource);
        courseList = courseDataSourceReader.readData();
    }

    private void loadRequestFormList() {
        allRequestFormList = new RequestFormList();
        StudentList studentList = getStudentList();
        AdvisorList advisorList = getAdvisorList();
        AbsenceRequestFormDataSource absenceDataSource = new AbsenceRequestFormDataSource(studentList, advisorList, getCourseList());
        DataSourceReader<RequestFormList, AbsenceRequestForm> absenceFormDataSourceReader = new DataSourceReader<>(absenceDataSource);
        RequestFormList absenseRequestForms = absenceFormDataSourceReader.readData();

        AddDropRequestFormDataSource addDropDataSource = new AddDropRequestFormDataSource(studentList, advisorList, getCourseList());
        DataSourceReader<RequestFormList, AddDropRequestForm> addDropFormDataSourceReader = new DataSourceReader<>(addDropDataSource);
        RequestFormList addDropRequestForms = addDropFormDataSourceReader.readData();

        CoEnrollRequestFormDataSource coEnrollDataSource = new CoEnrollRequestFormDataSource(studentList, advisorList);
        DataSourceReader<RequestFormList, CoEnrollRequestForm> coEnrollFormDataSourceReader = new DataSourceReader<>(coEnrollDataSource);
        RequestFormList coEnrollRequestForms = coEnrollFormDataSourceReader.readData();

        allRequestFormList.addRequestFormList(absenseRequestForms);
        allRequestFormList.addRequestFormList(addDropRequestForms);
        allRequestFormList.addRequestFormList(coEnrollRequestForms);
        allRequestFormList.applyRequestFormApprovingHistoryList(getRequestFormApprovingHistoryList());
    }

    public void saveUser() {
        User user = Session.getSession().getLoggedInUser();
        String role = user.getRole();
        try {
            switch (role) {
                case "advisor": {
                    saveAdvisor();
                    saveForms();
                    break;
                }
                case "student": {
                    saveStudent();
                    saveForms();
                    RequestFormNumberProvider.getInstance().saveData();
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
                case "admin": {
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
        } catch (IOException e) {
            reloadData();
            getCourseList();
            getDepartmentApproverList();
            saveUser();
            System.exit(1);
        }
    }

    private void saveDepartmentApprover() throws IOException {
        DepartmentApproverList newDepartmentApproverList = new DepartmentApproverList();
        DepartmentOfficer currentDepartmentOfficer = (DepartmentOfficer)Session.getSession().getLoggedInUser();
        for (DepartmentOfficer departmentOfficer : getDepartmentOfficerList().getDepartmentOfficers()){
            for (DepartmentApprover departmentApprover : departmentOfficer.getDepartmentApproverList().getApprovers()){
                if (newDepartmentApproverList.getApprovers().contains(departmentApprover) || departmentOfficer.getDepartment().equals(currentDepartmentOfficer.getDepartment())) continue;
                newDepartmentApproverList.addApprover(departmentApprover);
            }
        }

        for (DepartmentApprover departmentApprover : currentDepartmentOfficer.getDepartmentApproverList().getApprovers()){
            if (newDepartmentApproverList.getApprovers().contains(departmentApprover)) {continue;}
            newDepartmentApproverList.addApprover(departmentApprover);
        }

        DepartmentApproverDataSource departmentApproverDataSource = new DepartmentApproverDataSource(departmentList);
        DataSourceWriter<DepartmentApproverList, DepartmentApprover> departmentApproverDataSourceWriter = new DataSourceWriter<>(departmentApproverDataSource);
        departmentApproverDataSourceWriter.writeData(newDepartmentApproverList);
    }

    private void saveFacultyApprover() throws IOException {
        FacultyApproverList newFacultyApproverList = new FacultyApproverList();
        for (FacultyOfficer facultyOfficer : getFacultyOfficerList().getFacultyOfficers()){
            for (FacultyApprover facultyApprover : facultyOfficer.getFacultyApproverList().getApprovers()){
                if (newFacultyApproverList.getApprovers().contains(facultyApprover)) continue;
                newFacultyApproverList.addApprover(facultyApprover);
            }
        }
        FacultyApproverDataSource facultyApproverDataSource = new FacultyApproverDataSource(facultyList);
        DataSourceWriter<FacultyApproverList, FacultyApprover> facultyApproverDataSourceWriter = new DataSourceWriter<>(facultyApproverDataSource);
        facultyApproverDataSourceWriter.writeData(newFacultyApproverList);
    }

    private void saveAdvisor() throws IOException {
        AdvisorDataSource advisorDataSource = new AdvisorDataSource(departmentList);
        DataSourceWriter<AdvisorList, Advisor> advisorDataSourceWriter = new DataSourceWriter<>(advisorDataSource);
        advisorDataSourceWriter.writeData(advisorList);
    }

    public void saveStudent() throws IOException {
        StudentList newStudentList = new StudentList();
        for (DepartmentOfficer departmentOfficer : getDepartmentOfficerList().getDepartmentOfficers()) {
            for (Student student : departmentOfficer.getStudentList().getStudents()) {
                if (newStudentList.getStudents().contains(student)) continue;
                newStudentList.addStudent(student);
            }
        }

        StudentDataSource studentDataSource = new StudentDataSource(advisorList, departmentList);
        DataSourceWriter<StudentList, Student> studentDataSourceWriter = new DataSourceWriter<>(studentDataSource);
        studentDataSourceWriter.writeData(newStudentList);
    }

    private void saveDepartmentOfficer() throws IOException {
        DepartmentOfficerDataSource departmentOfficerDataSource = new DepartmentOfficerDataSource(departmentList, studentList);
        DataSourceWriter<DepartmentOfficerList, DepartmentOfficer> departmentOfficerDataSourceWriter = new DataSourceWriter<>(departmentOfficerDataSource);
        departmentOfficerDataSourceWriter.writeData(departmentOfficerList);
    }

    private void saveFacultyOfficer() throws IOException {
        FacultyOfficerDataSource facultyOfficerDataSource = new FacultyOfficerDataSource(facultyList);
        DataSourceWriter<FacultyOfficerList, FacultyOfficer> facultyOfficerListFacultyDataSourceWriter = new DataSourceWriter<>(facultyOfficerDataSource);
        facultyOfficerListFacultyDataSourceWriter.writeData(facultyOfficerList);
    }

    private void saveAdmin() throws IOException {
        AdminDataSource adminDataSource = new AdminDataSource(studentList, departmentList, facultyList, advisorList, departmentOfficerList, facultyOfficerList);
        DataSourceWriter<UserList, Admin> adminDataSourceWriter = new DataSourceWriter<>(adminDataSource);
        adminDataSourceWriter.writeData(allUsers);
    }

    private void saveForms() throws IOException {
        RequestFormList absenceRequestFormList = new RequestFormList();
        RequestFormList addDropRequestFormList = new RequestFormList();
        RequestFormList coEnrollpRequestFormList = new RequestFormList();
        CourseList newCourseList = new CourseList();
        RequestFormActionHistoryList requestFormActionHistoryList = new RequestFormActionHistoryList();

        for (Student student: getStudentList().getStudents()) {
            for (RequestForm requestForm : student.getRequestFormList().getRequestForms()) {
                for (RequestFormActionHistory requestFormActionHistory : requestForm.getRequestFormActionHistoryList().getRequestFormApprovingHistories()) {
                    requestFormActionHistoryList.addRequestFormApprovingHistory(requestFormActionHistory);
                }
                if (requestForm instanceof AbsenceRequestForm) {
                    for (Course course : ((AbsenceRequestForm) requestForm).getCourseList().getCourses()){
                        newCourseList.addCourse(course);
                    }
                    absenceRequestFormList.addRequestForm(requestForm);
                }
                if (requestForm instanceof AddDropRequestForm) {
                    for (Course course : ((AddDropRequestForm) requestForm).getCourseList().getCourses()){
                        newCourseList.addCourse(course);
                    }
                    addDropRequestFormList.addRequestForm(requestForm);
                }
                if (requestForm instanceof CoEnrollRequestForm) {
                    coEnrollpRequestFormList.addRequestForm(requestForm);
                }
            }
        }

        AbsenceRequestFormDataSource absenceDataSource = new AbsenceRequestFormDataSource(studentList, advisorList, newCourseList);
        DataSourceWriter<RequestFormList, AbsenceRequestForm> writerAbsenceForm = new DataSourceWriter<>(absenceDataSource);
        writerAbsenceForm.writeData(absenceRequestFormList);

        AddDropRequestFormDataSource addDropDataSource = new AddDropRequestFormDataSource(studentList, advisorList, newCourseList);
        DataSourceWriter<RequestFormList, AddDropRequestForm> writerAddDrop = new DataSourceWriter<>(addDropDataSource);
        writerAddDrop.writeData(addDropRequestFormList);

        CoEnrollRequestFormDataSource coEnrollDataSource = new CoEnrollRequestFormDataSource(studentList, advisorList);
        DataSourceWriter<RequestFormList, CoEnrollRequestForm> writerCoEnroll = new DataSourceWriter<>(coEnrollDataSource);
        writerCoEnroll.writeData(coEnrollpRequestFormList);

        RequestFormApprovingHistoryDataSource requestFormApprovingHistoryDataSource = new RequestFormApprovingHistoryDataSource();
        DataSourceWriter<RequestFormActionHistoryList, RequestFormActionHistory> requestFormApprovingHistoryDataSourceWriter = new DataSourceWriter<>(requestFormApprovingHistoryDataSource);
        requestFormApprovingHistoryDataSourceWriter.writeData(requestFormActionHistoryList);

        CourseDataSource courseDataSource = new CourseDataSource();
        DataSourceWriter<CourseList, Course> courseDataSourceWriter = new DataSourceWriter<>(courseDataSource);
        courseDataSourceWriter.writeData(newCourseList);
    }

    private void saveFacultyList() throws IOException {
        FacultyDataSource facultyDataSource = new FacultyDataSource();
        DataSourceWriter<FacultyList, Faculty> facultyDataSourceWriter = new DataSourceWriter<>(facultyDataSource);
        facultyDataSourceWriter.writeData(facultyList);
    }

    private void saveDepartmentList() throws IOException {
        DepartmentDataSource departmentDataSource = new DepartmentDataSource(facultyList);
        DataSourceWriter<DepartmentList, Department> departmentDataSourceWriter = new DataSourceWriter<>(departmentDataSource);
        departmentDataSourceWriter.writeData(departmentList);
    }

    public void clearDataProvider() {
        departmentList=null;
        facultyList=null;
        allRequestFormList=null;
        advisorList=null;
        studentList=null;
        departmentOfficerList=null;
        facultyOfficerList=null;
        departmentApproverList=null;
        facultyApproverList=null;
        allUsers=null;
        courseList=null;
    }

    public void updateAdminData() {
        if (Session.getSession().getLoggedInUser() instanceof Admin) {
            DataProvider.getDataProvider().clearDataProvider();
            DataProvider.getDataProvider().reloadData();
            setAdminData((Admin) Session.getSession().getLoggedInUser());
        }
    }

    public boolean doesUsernameExist(String username) {
        for (User user : getUserList().getUsers()) {
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }

    public boolean doesStudentIdExist(String studentId) {
        for (Student student : getStudentList().getStudents()) {
            if (student.getStudentId().equals(studentId)) return true;
        }
        return false;
    }

    public boolean doesAdvisorIdExist(String advisorId) {
        for (Advisor advisor : getAdvisorList().getAdvisors()) {
            if (advisor.getAdvisorId().equals(advisorId)) return true;
        }
        return false;
    }

}
