package ku.cs.controllers.officer.faculty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.controllers.officer.RequestFormsTableDescriptor;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.Session;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

public class FacultyOfficerRequestManagementPageController {
    @FXML
    public TextField searchTextField;
    @FXML
    private Pane tablePane;
    @FXML
    private Pane navBarPane;
    @FXML
    private AnchorPane anchorPane;

    SearchController searchController;

    TableComponentController<RequestForm> tableController;

    @FXML
    public void initialize() {
        Session session = Session.getSession();
        session.setNavbar(navBarPane);
        FacultyOfficer facultyOfficer = (FacultyOfficer) session.getLoggedInUser();

        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/faculty-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().clear();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        tablePane.getChildren().clear();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setHeadHeight(80);
            tableController.setRowHeight(50);
            tableController.setDisplayRowCount(5);
            tableController.setTablePane(tablePane);
            tableController.setTableHeadDescriptor(new RequestFormsTableDescriptor());

            tableController.addEventListener("กดดำเนินการ", eventData -> {
                RequestForm obj = (RequestForm) eventData;
                FormDataModel formDataModel = new FormDataModel(true, Session.getSession().getLoggedInUser(),obj);

                PopupComponent<FormDataModel> requestActionPopup = new PopupComponent<>(formDataModel,
                        "/ku/cs/views/officer/officer-request-action-popup.fxml",
                        "request-action-popup",
                        tablePane.getScene().getWindow()
                );
                requestActionPopup.onEvent(
                        (eventName, eventData1) -> {
                            if (eventName.equals("close"))
                                tableController.updateTable();
                        });
                requestActionPopup.show();
            });

            tableController.setDisplayModels(facultyOfficer.getRequestFormList().getRequestForms());
            tableController.updateTable();
            searchController = new SearchController(searchTextField, tableController, facultyOfficer.getRequestFormList());

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDataSelectDropdownClick(ActionEvent actionEvent) {
    }

    public void onSearchButtonClick(ActionEvent actionEvent) {
        searchController.searchFilter();
    }
}
