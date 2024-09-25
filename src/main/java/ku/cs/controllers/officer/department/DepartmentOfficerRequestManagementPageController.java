package ku.cs.controllers.officer.department;

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
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.Session;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;
import java.util.Objects;

public class DepartmentOfficerRequestManagementPageController {
    @FXML
    public TextField searchTextField;
    @FXML
    private Pane tablePane;
    @FXML
    private Pane navBarPane;
    @FXML
    private AnchorPane anchorPane;

    private DepartmentOfficer departmentOfficer;

    private TableComponentController<RequestForm> tableController;

    SearchController<RequestForm> searchController;

    @FXML
    public void initialize() {
        Session session = Session.getSession();
        session.setNavbar(navBarPane);
        departmentOfficer = (DepartmentOfficer) session.getLoggedInUser();
        navBarPane.getChildren().clear();
        navBarPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ku/cs/views/styles/main-style.css")).toString());
        anchorPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ku/cs/views/styles/main-style.css")).toString());
        tablePane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ku/cs/views/styles/main-style.css")).toString());

        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
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

                for (RequestFormActionHistory history : obj.getRequestFormApprovingHistoryList().getRequestFormApprovingHistories()) {
                    System.out.println("sidfuhgojafpk "+history.getApproverIdentity());
                }

                FormDataModel formDataModel = new FormDataModel(true, Session.getSession().getLoggedInUser(),obj);

                PopupComponent<FormDataModel> requestActionPopup = new PopupComponent<>(formDataModel,
                        "/ku/cs/views/officer/officer-request-action-popup.fxml",
                        "request-action-popup",
                        tablePane.getScene().getWindow()
                );
                requestActionPopup.onEvent(
                        (eventName, eventData1) -> {
                            if (eventName.equals("close")) {
                                try {
                                    tableController.setDisplayModels(departmentOfficer.getRequestFormList().getRequestForms());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                requestActionPopup.show();
            });

            tableController.setDisplayModels(departmentOfficer.getRequestFormList().getRequestForms());
            tableController.updateTable();
            searchController = new SearchController<>(searchTextField, tableController, departmentOfficer.getRequestFormList());

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDataSelectDropdownClick(ActionEvent actionEvent) {
    }

    public void onSearchButtonClick(ActionEvent actionEvent) {
        System.out.println("search");
        searchController.searchFilter();
    }

}
