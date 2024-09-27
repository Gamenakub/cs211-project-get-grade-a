package ku.cs.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.controllers.advisor.StudentRequestFormHistoryTableDescriptor;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.Session;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

public class StudentRequestFormTrackingPageController {
    @FXML private AnchorPane anchorPane;
    @FXML private Pane tablePane;
    @FXML private Pane navBarPane;
    @FXML private TextField searchTextField;
    @FXML private Circle searchButton;
    @FXML private MenuButton statusMenuButton;
    private TableComponentController tableController;
    private SearchController searchController;
    private Student student;

    @FXML public void initialize() throws IOException {
        Session session = Session.getSession();
        student = (Student) session.getLoggedInUser();


        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/student-navbar.fxml"));
        try {
            AnchorPane studentNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(studentNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image searchIcon = new Image(getClass().getResource("/images/search-icon.png").toString());
        searchButton.setFill(new ImagePattern(searchIcon));

        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());

        Session.getSession().setNavbar(navBarPane);
        Session.getSession().getTheme().setTheme(anchorPane);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setHeadHeight(80);
            tableController.setRowHeight(60);
            tableController.setDisplayRowCount(5);

            tableController.setTablePane(tablePane);
            tableController.setTableHeadDescriptor(new StudentRequestFormHistoryTableDescriptor());

            tableController.addEventListener("ดูคำร้อง", eventData -> {
                System.out.println("clicked");
                RequestForm obj = (RequestForm) eventData;
                FormDataModel formDataModel = new FormDataModel(true, Session.getSession().getLoggedInUser(),obj);

                String fxmlPath = "/ku/cs/views/request-forms/";
                if(obj instanceof AddDropRequestForm) {
                    fxmlPath = fxmlPath + "student-add-drop-request-form-popup-page1.fxml";
                } else if(obj instanceof AbsenceRequestForm) {
                    fxmlPath = fxmlPath + "student-absence-request-form-popup-page1.fxml";
                } else if(obj instanceof CoEnrollRequestForm) {
                    fxmlPath = fxmlPath + "student-co-enroll-request-form-popup-page1.fxml";
                }
                PopupComponent<FormDataModel> requestActionPopup = new PopupComponent<>(formDataModel,
                        fxmlPath,
                        "request-form",
                        tablePane.getScene().getWindow()
                );
                requestActionPopup.show();
            });
            tableController.setDisplayModels(student.getRequestFormList().getRequestForms());
            tableController.updateTable();

            tablePane.getChildren().add(table);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchController=new SearchController<>(searchTextField,tableController,student.getRequestFormList());
    }
    @FXML public void onCreateFormButton(){
        PopupComponent<Object> popup = new PopupComponent<>(new Object(), "/ku/cs/views/request-forms/student-create-request-form-popup.fxml","create form",navBarPane.getScene().getWindow());
        popup.show();
    }
    @FXML public void onSearchButtonClick(){
        searchController.searchFilter();
    }
    @FXML public void onPendingMenuButton(){
        statusMenuButton.setText("รอการอนุมัติ");
        searchController.setFilterContext("PENDING");
        searchController.searchFilter();
    }
    @FXML public void onApprovedMenuButton(){
        statusMenuButton.setText("ได้รับการอนุมัติ");
        searchController.setFilterContext("APPROVED");
        searchController.searchFilter();
    }
    @FXML public void onRejectedMenuButton(){
        statusMenuButton.setText("ได้รับการปฏิเสธ");
        searchController.setFilterContext("REJECTED");
        searchController.searchFilter();
    }
    @FXML public void onAllStatusMenuButton(){
        statusMenuButton.setText("สถานะคำร้อง");
        searchController.resetFilter();
    }
}
