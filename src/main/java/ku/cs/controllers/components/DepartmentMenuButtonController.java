package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import ku.cs.models.Department;

import java.util.ArrayList;

public class DepartmentMenuButtonController {
    @FXML
    public void setMenuButton(MenuButton departmentMenuButton, Department department) {
        departmentMenuButton.getItems().clear();
        departmentMenuButton.setText(department.getName());
        departmentMenuButton.setUserData(department);
    }

    @FXML
    public void addItems(MenuButton departmentMenuButton, ArrayList<Department> departments) {
        if (departments.isEmpty()) {
            setDefaultMenuButton(departmentMenuButton);
        } else {
            departmentMenuButton.getItems().clear();
            setMenuButton(departmentMenuButton, departments.getFirst());
            for (Department department : departments) {
                MenuItem departmentItem = new MenuItem(department.getName());
                departmentMenuButton.getItems().add(departmentItem);
                departmentItem.setOnAction(event -> setMenuButton(departmentMenuButton, department));
            }
        }
    }

    @FXML
    public void setDefaultMenuButton(MenuButton departmentMenuButton) {
        departmentMenuButton.getItems().clear();
        departmentMenuButton.setText("ไม่พบภาควิชาในฐานข้อมูล");
        departmentMenuButton.setUserData(null);
    }
}