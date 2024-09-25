package ku.cs.controllers.advisor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.controllers.officer.RequestFormsTableDescriptor;
import ku.cs.models.FormDataModel;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.collections.StudentList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.services.AlertService;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdvisorStudentInformationController {
    @FXML
    private Pane tablePane;
    @FXML
    private Pane navBarPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableComponentController tableController;

    @FXML
    private SearchController searchController;

    private Advisor user;
    @FXML
    public void initialize() {

        navBarPane.getChildren().clear();
        tablePane.getChildren().clear();
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());

        user=(Advisor) Session.getSession().getLoggedInUser();
        Session.getSession().setNavbar(navBarPane);
        Session.getSession().getTheme().setTheme(anchorPane);

        tablePane.getChildren().clear();



        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();

            tableController.setHeadHeight(80);
            tableController.setRowHeight(50);
            tableController.setDisplayRowCount(5);
            tableController.setTablePane(tablePane);
            tableController.setTableHeadDescriptor(new StudentTableDescriptor());

            tableController.addEventListener("ดูประวัติคำร้อง", eventData -> {
                Student obj = (Student) eventData;

                try {
                    FXRouter.goTo("advisor-student-request-form-history",obj);
                } catch (IOException e) {
                    AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
                    System.exit(0);
                }
            });
            StudentList studentList = user.getStudents();
            tableController.setDisplayModels(studentList.getStudents());
            tableController.updateTable();
            tablePane.getChildren().add(table);

        } catch (IOException e) {
            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(0);
        }

        searchController=new SearchController<>(searchTextField,tableController,user.getStudents());
    }

    @FXML
    public void onSearchButtonClick(){
        searchController.searchFilter();
    }


}
