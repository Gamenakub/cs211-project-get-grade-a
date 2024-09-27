package ku.cs.controllers.advisor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.controllers.components.tables.TableRowController;
import ku.cs.controllers.officer.RequestFormsTableDescriptor;
import ku.cs.models.FormDataModel;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.AlertService;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

public class AdvisorStudentRequestFormHistoryController {
    @FXML
    private Pane tablePane;

    @FXML
    private Pane navBarPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuButton filterMenuButton;

    @FXML
    private Label studentLabel;

    private Student student;

    private SearchController searchController;
    @FXML
    private TextField searchTextField;

    private TableComponentController tableController;


    @FXML
    public void initialize() {

        navBarPane.getChildren().clear();
        tablePane.getChildren().clear();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());

        navBarPane.getChildren().clear();
        student= (Student) FXRouter.getData();

        Session.getSession().setNavbar(navBarPane);
        Session.getSession().getTheme().setTheme(anchorPane);
        studentLabel.setText(student.getName()+" "+student.getSurname());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();

            tableController.setHeadHeight(80);
            tableController.setRowHeight(50);
            tableController.setDisplayRowCount(5);
            tableController.setTablePane(tablePane);
            tableController.setTableHeadDescriptor(new StudentRequestFormHistoryTableDescriptor());

            tableController.addEventListener("ดูคำร้อง", eventData -> {
                RequestForm obj = (RequestForm) eventData;
                FormDataModel formDataModel = new FormDataModel(true, Session.getSession().getLoggedInUser(),obj);
                formDataModel.getFormObject().getStatus().equals(RequestForm.Status.PENDING_TO_ADVISOR);
                String path = null;
                if (obj instanceof CoEnrollRequestForm){
                    path = "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page1.fxml";
                }
                else if (obj instanceof AddDropRequestForm){
                    if (((AddDropRequestForm) obj).isAdd()){
                        path = "/ku/cs/views/request-forms/student-add-drop-request-form-popup-page1.fxml";
                    }
                    else{
                        path = "/ku/cs/views/request-forms/student-add-drop-request-form-popup-page1.fxml";
                    }
                }
                else if (obj instanceof AbsenceRequestForm){
                    path = "/ku/cs/views/request-forms/student-absence-request-form-popup-page1.fxml";
                }
                PopupComponent<FormDataModel>requestActionPopup = new PopupComponent<>(formDataModel, path, "request-action-popup", tablePane.getScene().getWindow());

                requestActionPopup.show();
            });

            RequestFormList requestFormList = student.getRequestFormList();
            tableController.setDisplayModels(requestFormList.getRequestForms());
            tableController.updateTable();

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(0);
        }

        searchController=new SearchController(searchTextField,tableController,student.getRequestFormList());


    }

    @FXML
    void onAllFilterButtonClick(){
        filterMenuButton.setText("ทั้งหมด");
        searchController.resetFilter();
    }

    @FXML
    void onSuccessFormFilterButtonClick(){
        filterMenuButton.setText("สำเร็จแล้ว");
        searchController.setFilterContext("APPROVED");
        searchController.searchFilter();
    }

    @FXML
    void onInProgressFormFilterButtonClick(){
        filterMenuButton.setText("กำลังดำเนินการ");
        searchController.setFilterContext("PENDING");
        searchController.searchFilter();
    }

    @FXML
    void onRejectedFormFilterButtonClick(){
        filterMenuButton.setText("ปฏิเสธ");
        searchController.setFilterContext("REJECTED");
        searchController.searchFilter();
    }

    @FXML
    void onSearchButtonClick() {
        searchController.searchFilter();
    }






}

