package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import ku.cs.models.Department;
import ku.cs.models.Faculty;

import java.util.ArrayList;

public class FacultyMenuButtonController {

    @FXML
    public static void addItems(MenuButton facultyMenuButton, ArrayList<Faculty> faculties) {
        if (faculties.isEmpty()) {
            setDefaultMenuButton(facultyMenuButton);
        } else {
            setMenuButton(facultyMenuButton, faculties.getFirst());
            for (Faculty faculty : faculties) {
                MenuItem facultyItem = new MenuItem(faculty.getName());
                facultyMenuButton.getItems().add(facultyItem);
                facultyItem.setOnAction(event -> setMenuButton(facultyMenuButton, faculty));
            }
        }
    }

    @FXML
    public static void addItems(MenuButton facultyMenuButton, MenuButton departmentMenuButton, ArrayList<Faculty> faculties) {
        if (faculties.isEmpty()) {
            setDefaultMenuButton(facultyMenuButton, departmentMenuButton);
        } else {
            setMenuButton(facultyMenuButton, departmentMenuButton, faculties.getFirst());
            for (Faculty faculty : faculties) {
                MenuItem facultyItem = new MenuItem(faculty.getName());
                facultyMenuButton.getItems().add(facultyItem);
                facultyItem.setOnAction(event -> setMenuButton(facultyMenuButton, departmentMenuButton, faculty));
            }
        }
    }

    @FXML
    public static void setMenuButton(MenuButton facultyMenuButton, MenuButton departmentMenuButton, Faculty faculty, Department department) {
        setMenuButton(facultyMenuButton, faculty);
        DepartmentMenuButtonController.setMenuButton(departmentMenuButton, department);
    }

    @FXML
    public static void setMenuButton(MenuButton facultyMenuButton, MenuButton departmentMenuButton, Faculty faculty) {
        setMenuButton(facultyMenuButton, faculty);
        DepartmentMenuButtonController.addItems(departmentMenuButton, faculty.getDepartmentList().getDepartments());
    }

    @FXML
    public static void setMenuButton(MenuButton facultyMenuButton, Faculty faculty) {
        facultyMenuButton.setText(faculty.getName());
        facultyMenuButton.setUserData(faculty);
    }

    @FXML
    private static void setDefaultMenuButton(MenuButton facultyMenuButton, MenuButton departmentMenuButton) {
        setDefaultMenuButton(facultyMenuButton);
        DepartmentMenuButtonController.setDefaultMenuButton(departmentMenuButton);
    }

    @FXML
    private static void setDefaultMenuButton(MenuButton facultyMenuButton) {
        facultyMenuButton.setText("ไม่พบคณะในฐานข้อมูล");
        facultyMenuButton.setUserData(null);
    }
}
