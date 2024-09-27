package ku.cs.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.DashboardMenuButtonController;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.collections.UserList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Admin;
import ku.cs.services.Session;

public class AdminDashboardPageController {

    public MenuButton facultyFormMenuButton;
    public MenuButton departmentFormMenuButton;
    public MenuButton facultyUserMenuButton;
    public MenuButton departmentUserMenuButton;
    @FXML
    private Pane navBarPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label totalFormLabel;
    @FXML
    private Label totalSuccessFormLabel;
    @FXML
    private Label totalInProgressFormLabel;
    @FXML
    private Label totalRejectedFormLabel;
    @FXML
    private Label totalSuccessFormFilteredLabel;
    @FXML
    private Label totalFacultyOfficerLabel;
    @FXML
    private Label totalDepartmentOfficerLabel;
    @FXML
    private Label totalAdvisorLabel;
    @FXML
    private Label totalStudentLabel;

    Admin admin=(Admin) Session.getSession().getLoggedInUser();
    UserList allUser;


    public void initialize() {
        allUser=admin.getUserList();
        Session.getSession().setNavbar(navBarPane);
        DashboardMenuButtonController dashBoardMenuButtonController=new DashboardMenuButtonController(totalFormLabel,
                totalFacultyOfficerLabel,
                totalDepartmentOfficerLabel,
                totalAdvisorLabel,
                totalStudentLabel,
                allUser,
                admin.getRequestFormList());
        dashBoardMenuButtonController.addItemsDepartmentDashBoard(facultyFormMenuButton, departmentFormMenuButton, admin.getFacultyList().getFaculties(),allUser,"Form");
        dashBoardMenuButtonController.addItemsDepartmentDashBoard(facultyUserMenuButton,departmentUserMenuButton,admin.getFacultyList().getFaculties(),allUser,"User");
        setUserDefault();
        setSuccessFormFilteredDefault();
        setFormInfo();

    }

    public void onAllDepartmentFormClick() {
        departmentFormMenuButton.setText("ทั้งหมด");
    }

    public void onAllFacultyFormClick(ActionEvent actionEvent) {

        facultyFormMenuButton.setText("ทั้งหมด");
        departmentFormMenuButton.setText("ทั้งหมด");
    }


    private void setUserDefault(){
        totalStudentLabel.setText(admin.getStudentList().getStudents().size()+"");
        totalAdvisorLabel.setText(admin.getAdvisorList().getAdvisors().size()+"");
        totalFacultyOfficerLabel.setText(admin.getFacultyOfficerList().getOfficers().size()+"");
        totalDepartmentOfficerLabel.setText(admin.getDepartmentOfficerList().getOfficers().size()+"");
    }

    private void setSuccessFormFilteredDefault(){
        totalSuccessFormFilteredLabel.setText(admin.getRequestFormList().findRequestFormsByStatus(RequestForm.Status.APPROVED_BY_DEPARTMENT).getRequestForms().size()+"");
    }

    public void onAllFacultyUserClick(ActionEvent actionEvent) {
        facultyUserMenuButton.setText("ทั้งหมด");
        departmentUserMenuButton.setText("ทั้งหมด");
        setUserDefault();
    }

    public void onAllDepartmentUserClick(ActionEvent actionEvent) {

    }

    private void setFormInfo(){
        RequestFormList allRequestForm=admin.getRequestFormList();
        int totalForm=allRequestForm.getRequestForms().size();
        int totalSuccessForm=allRequestForm.findRequestFormsByStatus(RequestForm.Status.APPROVED_BY_DEPARTMENT).getRequestForms().size();
        int totalDenyForm=allRequestForm.findRequestFormsByStatus(RequestForm.Status.REJECTED_BY_ADVISOR).getRequestForms().size()+allRequestForm.findRequestFormsByStatus(RequestForm.Status.REFUSED_BY_DEPARTMENT).getRequestForms().size()+allRequestForm.findRequestFormsByStatus(RequestForm.Status.CANCELED_BY_STUDENT).getRequestForms().size();
        int totalInProgressForm= totalForm-totalSuccessForm-totalDenyForm;
        totalFormLabel.setText(totalForm+"");
        totalSuccessFormLabel.setText(totalSuccessForm+"");
        totalInProgressFormLabel.setText(totalInProgressForm+"");
        totalRejectedFormLabel.setText(totalDenyForm+"");

    }
}
