package ku.cs.controllers.officer.department;

import javafx.event.ActionEvent;
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
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;
import java.util.ArrayList;

public class DepartmentOfficerStudentManagementPageController {
    @FXML
    public TextField searchTextField;
    @FXML
    private Pane tablePane;
    @FXML
    private Pane navBarPane;
    @FXML
    private AnchorPane anchorPane;

    private DepartmentOfficer officer;
    private TableComponentController<Student> tableController;
    SearchController<Student> searchController;
    @FXML
    public void initialize() {
        Session session = Session.getSession();
        session.setNavbar(navBarPane);
        officer = (DepartmentOfficer) Session.getSession().getLoggedInUser();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().clear();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Advisor advisor : officer.getAdvisorList().getAdvisors()) {
            System.out.println("OKASDUIASD XXXX"+advisor.getName());
        }

        tablePane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setHeadHeight(40);
            tableController.setRowHeight(60);
            tableController.setDisplayRowCount(5);
            tableController.addEventListener("แก้ไข", eventData -> {
                PopupComponent<Student> editPopup = new PopupComponent<>((Student) eventData,
                        "/ku/cs/views/officer/department/department-officer-student-modify-popup.fxml",
                        "modify",
                        tablePane.getScene().getWindow());
                editPopup.onEvent((eventName, eventData1) -> {
                    System.out.println(eventName+" olaskodsaopaspdfaksof");
                    if (eventName.equals("close")){
                        tableController.updateTable();
                    }
                });
                editPopup.show();
            });
            tableController.setTablePane(tablePane);
            tableController.setTableHeadDescriptor(new DepartmentOfficerStudentTableDescriptor());
            tableController.sortBy("รหัสนิสิต", SortDirection.DESCENDING);

            tableController.setDisplayModels(getStudents());
            searchController = new SearchController<>(searchTextField, tableController, getStudentList());
            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddNisit(ActionEvent actionEvent) {
        PopupComponent<DepartmentOfficer> editPopup = new PopupComponent<>(officer, "/ku/cs/views/officer/department/department-officer-student-create-popup.fxml","modify",navBarPane.getScene().getWindow());
        editPopup.onEvent("save", eventData -> {
            System.out.println("I AM SAVED");
            Student newStudent = (Student) eventData;
            officer.getStudentList().addStudent(newStudent);
            //officer.getStudentWriteOnlyCollection().add(newStudent);
            try {
                tableController.setDisplayModels(getStudents());
            } catch (IOException e) {
                throw new RuntimeException("Invalid Object Type");
            }
        });
        editPopup.show();
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (Student student : officer.getStudentList().getStudents()) {
            if (student.getDepartment().isId(officer.getDepartment().getId())){
                students.add(student);
            }
        }
        return students;
    }

    public StudentList getStudentList() {
        StudentList studentList = new StudentList();
        for (Student student : officer.getStudentList().getStudents()) {
            if (student.getDepartment().isId(officer.getDepartment().getId())){
                studentList.addStudent(student);
            }
        }
        return studentList;
    }

    public void onSearchButtonClick(ActionEvent actionEvent) {

    }
}
