package ku.cs.controllers.advisor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.controllers.officer.RequestFormsTableDescriptor;
import ku.cs.models.FormDataModel;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.users.Advisor;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

public class AdvisorRequestFormController {
    @FXML
    private Pane tablePane;

    @FXML
    private Pane navBarPane;
    @FXML
    private TextField searchTextField;

    @FXML
    private AnchorPane anchorPane;

    private Advisor user;

    private SearchController searchController;
    private TableComponentController tableController;

    ObservableList<RequestForm> testRequestForms = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        user=(Advisor) Session.getSession().getLoggedInUser();

        navBarPane.getChildren().clear();
        tablePane.getChildren().clear();
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
            tableController.setTableHeadDescriptor(new RequestFormsTableDescriptor());

            tableController.addEventListener("กดดำเนินการ", eventData -> {
                RequestForm obj = (RequestForm) eventData;
                FormDataModel formDataModel = new FormDataModel(true, Session.getSession().getLoggedInUser(),obj);

                PopupComponent<FormDataModel> requestActionPopup = new PopupComponent<>(formDataModel,
                        "/ku/cs/views/advisor/advisor-request-form-operation-popup.fxml",
                        "request-action-popup",
                        tablePane.getScene().getWindow()
                );

                requestActionPopup.show();
                requestActionPopup.onEvent((eventName, event) -> {
                    try {
                        tableController.setDisplayModels(user.getRequestFormList().getRequestForms());
                    } catch (IOException e) {
                        AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
                        System.exit(0);
                    }
                });
            });
            printAllChildren(table);

            RequestFormList requestFormList = user.getRequestFormList();
            tableController.setDisplayModels(requestFormList.getRequestForms());
            tableController.updateTable();
            tablePane.getChildren().add(table);

        } catch (IOException e) {
            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(0);
        }

        searchController=new SearchController(searchTextField,tableController,user.getRequestFormList());


    }

    public void onSearchButtonClick(){
        searchController.searchFilter();
    }

    public static void printAllChildren(Node node) {
        // Print the current node
        System.out.println(node);
        // If the node is a Parent (e.g., Pane, AnchorPane), recursively get its children
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                printAllChildren(child); // Recursive call for nested children
            }
        }
    }







}

