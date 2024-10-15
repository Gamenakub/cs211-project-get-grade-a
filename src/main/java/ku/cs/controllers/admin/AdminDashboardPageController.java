package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.DashboardMenuButtonController;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.users.Admin;
import ku.cs.services.DataProvider;
import ku.cs.services.FileWatcher;
import ku.cs.services.Session;

import java.util.HashSet;
import java.util.Set;

public class AdminDashboardPageController {

    @FXML private MenuButton facultyFormMenuButton;
    @FXML private MenuButton departmentFormMenuButton;
    @FXML private MenuButton facultyUserMenuButton;
    @FXML private MenuButton departmentUserMenuButton;
    @FXML private Pane navBarPane;
    @FXML private AnchorPane anchorPane;
    @FXML private Label totalFormLabel;
    @FXML private Label totalSuccessFormLabel;
    @FXML private Label totalInProgressFormLabel;
    @FXML private Label totalRejectedFormLabel;
    @FXML private Label totalSuccessFormFilteredLabel;
    @FXML private Label totalFacultyOfficerLabel;
    @FXML private Label totalDepartmentOfficerLabel;
    @FXML private Label totalAdvisorLabel;
    @FXML private Label totalStudentLabel;

    private Admin admin;
    private DashboardMenuButtonController dashBoardMenuButtonController;
    private FileWatcher fileWatcher;

    @FXML
    public void initialize() {
        Session session = Session.getSession();
        session.setNavbarByUserRole(navBarPane);
        session.getThemeProvider().setTheme(anchorPane);
        admin = (Admin) session.getLoggedInUser();
        DataProvider.getDataProvider().updateAdminData();
        dashBoardMenuButtonController = new DashboardMenuButtonController(this);
        dashBoardMenuButtonController.addItemsDepartmentDashBoard(facultyFormMenuButton, departmentFormMenuButton, admin.getFacultyList().getFaculties(), "Form");
        dashBoardMenuButtonController.addItemsDepartmentDashBoard(facultyUserMenuButton, departmentUserMenuButton, admin.getFacultyList().getFaculties(), "User");
        setUserDefault();
        setFormInfo();
        setSuccessFormFilteredDefault();

        Set<String> filesToWatch = new HashSet<>();
        filesToWatch.add("data/users/advisor.csv");
        filesToWatch.add("data/users/student.csv");
        filesToWatch.add("data/users/department_officer.csv");
        filesToWatch.add("data/users/faculty_officer.csv");
        filesToWatch.add("data/request-forms/absence_request_forms.csv");
        filesToWatch.add("data/request-forms/add_drop_request_forms.csv");
        filesToWatch.add("data/request-forms/co_enroll_request_forms.csv");
        startFileWatcher(filesToWatch);

        anchorPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene == null) {
                stopWatcher();
            }
        });
    }

    private void startFileWatcher(Set<String> csvFilePaths) {
        fileWatcher = new FileWatcher(this, csvFilePaths);
        Thread watcherThread = new Thread(fileWatcher);
        watcherThread.setDaemon(true);
        watcherThread.start();
    }

    private void stopWatcher() {
        if (fileWatcher != null) {
            fileWatcher.stopWatching();
            fileWatcher = null;
        }
    }

    private void setUserDefault() {
        totalStudentLabel.setText(String.valueOf(admin.getStudentList().getStudents().size()));
        totalAdvisorLabel.setText(String.valueOf(admin.getAdvisorList().getAdvisors().size()));
        totalFacultyOfficerLabel.setText(String.valueOf(admin.getFacultyOfficerList().getOfficers().size()));
        totalDepartmentOfficerLabel.setText(String.valueOf(admin.getDepartmentOfficerList().getOfficers().size()));
    }

    private void setSuccessFormFilteredDefault() {
        totalSuccessFormFilteredLabel.setText(String.valueOf(admin.getRequestFormList().filter("APPROVED").size()));
    }

    @FXML
    public void onAllFacultyUserMenuItem() {
        facultyUserMenuButton.setText("ทั้งหมด");
        departmentUserMenuButton.setText("ทั้งหมด");
        setUserDefault();
        dashBoardMenuButtonController.clearUserFilter();
    }

    private void setFormInfo() {
        RequestFormList allRequestForm = admin.getRequestFormList();
        int totalForm = allRequestForm.getRequestForms().size();
        int totalSuccessForm = allRequestForm.filter("APPROVED").size();
        int totalRejectedForm = allRequestForm.filter("REJECTED").size();
        int totalInProgressForm = allRequestForm.filter("PENDING").size();
        totalFormLabel.setText(String.valueOf(totalForm));
        totalSuccessFormLabel.setText(String.valueOf(totalSuccessForm));
        totalInProgressFormLabel.setText(String.valueOf(totalInProgressForm));
        totalRejectedFormLabel.setText(String.valueOf(totalRejectedForm));
    }

    @FXML
    public void onAllFacultyFormItem() {
        facultyFormMenuButton.setText("ทั้งหมด");
        departmentFormMenuButton.setText("ทั้งหมด");
        departmentFormMenuButton.getItems().clear();
        setSuccessFormFilteredDefault();
        dashBoardMenuButtonController.clearFormFilter();
    }

    public void updateData() {
        setSuccessFormFilteredDefault();
        setFormInfo();
        setUserDefault();
        dashBoardMenuButtonController.update();
    }

    public void setUserData(int studentCount, int advisorCount, int facultyOfficerCount, int departmentOfficerCount) {
        this.totalStudentLabel.setText(String.valueOf(studentCount));
        this.totalAdvisorLabel.setText(String.valueOf(advisorCount));
        this.totalFacultyOfficerLabel.setText(String.valueOf(facultyOfficerCount));
        this.totalDepartmentOfficerLabel.setText(String.valueOf(departmentOfficerCount));
    }

    public void setFormData(int totalForm) {
        totalFormLabel.setText(String.valueOf(totalForm));
    }
}