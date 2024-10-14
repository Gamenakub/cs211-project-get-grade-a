package ku.cs.controllers.advisor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.collections.StudentList;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.services.AlertService;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;

import java.io.IOException;

public class AdvisorStudentInformationPageController {
    @FXML private Pane tablePane;
    @FXML private Pane navBarPane;
    @FXML private AnchorPane anchorPane;
    @FXML private TextField searchTextField;
    @FXML private TableComponentController<Student> tableController;
    @FXML private SearchController<Student> searchController;

    @FXML
    public void initialize() {
        Advisor user = (Advisor) Session.getSession().getLoggedInUser();
        Session session = Session.getSession();
        session.setNavbarByUserRole(navBarPane);
        session.getThemeProvider().setTheme(anchorPane);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setTableAttributes(tablePane, 40, 60, 5, new StudentTableDescriptor());
            tableController.addEventListener("ดูประวัติคำร้อง", eventData -> {
                Student obj = (Student) eventData;

                try {
                    FXRouter.goTo("advisor-student-request-form-history", obj);
                } catch (IOException e) {
                    AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
                    System.exit(1);
                }
            });
            StudentList studentList = user.getStudents();
            tableController.setDisplayModels(studentList.getStudents());
            tableController.updateTable();
            tablePane.getChildren().add(table);
        } catch (IOException e) {
            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }

        searchController = new SearchController<>(searchTextField, tableController, user.getStudents());
    }

    @FXML
    public void onSearchButton() {
        searchController.searchFilter();
    }


}
