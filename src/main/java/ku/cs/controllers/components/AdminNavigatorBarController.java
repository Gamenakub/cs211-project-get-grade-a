package ku.cs.controllers.components;

import javafx.fxml.FXML;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class AdminNavigatorBarController {
    @FXML
    public void onPersonalInformationManagementButton(){
        try {
            FXRouter.goTo("user-personal-information-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onAdminUserManagementButton() {
        try {
            FXRouter.goTo("admin-user-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onFacultyManagementMenuItem() {
        try {
            FXRouter.goTo("admin-faculty-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onDepartmentManagementMenuItem() {
        try {
            FXRouter.goTo("admin-department-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onFacultyOfficerManagementMenuItem() {
        try {
            FXRouter.goTo("admin-faculty-officer-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onDepartmentOfficerManagementMenuItem() {
        try {
            FXRouter.goTo("admin-department-officer-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onAdvisorManagementMenuItem() {
        try {
            FXRouter.goTo("admin-advisor-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onDashboardButton() {

    }

    @FXML
    public void onLogoutButton() {
        try {
            FXRouter.goTo("user-login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
