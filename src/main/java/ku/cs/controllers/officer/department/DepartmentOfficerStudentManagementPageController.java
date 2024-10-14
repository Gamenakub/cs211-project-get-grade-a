package ku.cs.controllers.officer.department;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.collections.StudentList;
import ku.cs.models.users.Student;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;
import java.util.ArrayList;

public class DepartmentOfficerStudentManagementPageController {
    @FXML private SearchController<Student> searchController;
    @FXML private TextField searchTextField;
    @FXML private Pane tablePane;
    @FXML private Pane navBarPane;
    @FXML private AnchorPane anchorPane;
    private DepartmentOfficer officer;
    private TableComponentController<Student> tableController;

    @FXML
    public void initialize() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        Session.getSession();
        Session.getSession().setNavbarByUserRole(navBarPane);
        officer = (DepartmentOfficer) Session.getSession().getLoggedInUser();
        
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().clear();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            AlertService.showError("เกิดข้อผิดพลาดในขณะอ่านไฟล์โปรแกรม กรุณาตรวจสอบความสมบูรณ์ของไฟล์โปรแกรมของท่าน");
        }

        tablePane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.addEventListener("แก้ไข", eventData -> {
                PopupComponent<Student> editPopup = null;
                try {
                    editPopup = new PopupComponent<>((Student) eventData,
                            "/ku/cs/views/officer/department/department-officer-student-modify-popup.fxml",
                            tablePane.getScene().getWindow());
                } catch (IOException e) {
                    AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
                    System.exit(1);
                }

                editPopup.addEventListener("close", eventData1 -> tableController.updateTable());
                editPopup.show();
            });
            tableController.sortBy("รหัสนิสิต", SortDirection.DESCENDING);
            tableController.setTableAttributes(tablePane, 40, 60, 6, new DepartmentOfficerStudentTableDescriptor());
            tableController.setDisplayModels(getStudents());
            searchController = new SearchController<>(searchTextField, tableController, getStudentList());
            tablePane.getChildren().add(table);

        } catch (IOException e) {
            AlertService.showError("เกิดข้อผิดพลาดในการแสดงผล กรุณาติดต่อผู้พัฒนา");
        }
    }

    public void onAddStudentButton() {
        PopupComponent<DepartmentOfficer> editPopup = null;
        try {
            editPopup = new PopupComponent<>(officer, "/ku/cs/views/officer/department/department-officer-student-create-popup.fxml", navBarPane.getScene().getWindow());
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
        editPopup.onEvent("save", eventData -> {
            Student newStudent = (Student) eventData;
            officer.getStudentList().addStudent(newStudent);
            DataProvider.getDataProvider().saveUser();
            tableController.setDisplayModels(getStudents());
        });
        editPopup.show();
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (Student student : officer.getStudentList().getStudents()) {
            if (student.getDepartment().isId(officer.getDepartment().getId())) {
                students.add(student);
            }
        }
        return students;
    }

    public StudentList getStudentList() {
        StudentList studentList = new StudentList();
        for (Student student : officer.getStudentList().getStudents()) {
            if (student.getDepartment().isId(officer.getDepartment().getId())) {
                studentList.addStudent(student);
            }
        }
        return studentList;
    }

    public void onSearchButton() {
        searchController.searchFilter();

    }
}
