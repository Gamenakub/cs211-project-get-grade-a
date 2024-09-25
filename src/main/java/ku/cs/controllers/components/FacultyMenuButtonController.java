package ku.cs.controllers.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import ku.cs.models.Department;
import ku.cs.models.Faculty;

import java.util.ArrayList;

public class FacultyMenuButtonController {

    private static class DepartmentMenuButtonController {
        public static void setMenuButton(MenuButton departmentMenuButton, Department department) {
            departmentMenuButton.getItems().clear();
            departmentMenuButton.setText(department.getName());
            departmentMenuButton.setUserData(department);
        }
        public static void addItems(MenuButton departmentMenuButton, ArrayList<Department> departments) {
            departmentMenuButton.getItems().clear();
            for(Department department : departments) {
                MenuItem departmentItem = new MenuItem(department.getName());
                departmentMenuButton.getItems().add(departmentItem);
                departmentItem.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        departmentMenuButton.setText(departmentItem.getText());
                        departmentMenuButton.setUserData(department);
                    }
                });
            }
            departmentMenuButton.getItems();
        }

    }

    public static void addItems(MenuButton facultyMenuButton, ArrayList<Faculty> faculties) {
        setMenuButton(facultyMenuButton, faculties.getFirst());
        for(Faculty faculty : faculties) {
            MenuItem facultyItem = new MenuItem(faculty.getName());
            facultyMenuButton.getItems().add(facultyItem);
            facultyItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    facultyMenuButton.setText(facultyItem.getText());
                    facultyMenuButton.setUserData(faculty);
                }
            });
        }
        facultyMenuButton.getItems();
    }

    public static void addItems(MenuButton facultyMenuButton, MenuButton departmentMenuButton, ArrayList<Faculty> faculties) {
        setMenuButton(facultyMenuButton, departmentMenuButton, faculties.getFirst(), faculties.getFirst().getDepartmentList().getDepartments().getFirst());
        for(Faculty faculty : faculties) {
            MenuItem facultyItem = new MenuItem(faculty.getName());
            facultyMenuButton.getItems().add(facultyItem);
            facultyItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    facultyMenuButton.setText(facultyItem.getText());
                    facultyMenuButton.setUserData(faculty);
                    DepartmentMenuButtonController.setMenuButton(departmentMenuButton, faculty.getDepartmentList().getDepartments().getFirst());
                    DepartmentMenuButtonController.addItems(departmentMenuButton, faculty.getDepartmentList().getDepartments());
                }
            });
        }
        facultyMenuButton.getItems();
    }

    public static void setMenuButton(MenuButton facultyMenuButton, MenuButton departmentMenuButton, Faculty faculty, Department department) {
        setMenuButton(facultyMenuButton, faculty);
        DepartmentMenuButtonController.setMenuButton(departmentMenuButton, department);
        DepartmentMenuButtonController.addItems(departmentMenuButton, faculty.getDepartmentList().getDepartments());
    }

    public static void setMenuButton(MenuButton facultyMenuButton, Faculty faculty) {
        facultyMenuButton.setText(faculty.getName());
        facultyMenuButton.setUserData(faculty);
    }

}
