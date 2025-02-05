package ku.cs.cs211671project;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;

import java.io.IOException;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("/images/logo.png")));
        stage.setResizable(false);

        stage.setOnCloseRequest((WindowEvent event) -> {
            if (Session.getSession().getLoggedInUser() != null) {
                Session.getSession().clearSession();
            }
        });

        FXRouter.bind(this, stage, "FormXpress", 1080, 720);
        configRoutes();
        FXRouter.goTo("user-login");
    }

    private void configRoutes() {
        String viewPath = "ku/cs/views/";
        FXRouter.when("developer-information", viewPath + "developer-information-page.fxml");
        FXRouter.when("user-login", viewPath + "user-login-page.fxml");
        FXRouter.when("user-personal-information-management", viewPath + "user-personal-information-management-page.fxml");
        FXRouter.when("guide-pdf", viewPath + "guide-pdf.fxml");
        FXRouter.when("admin-user-management", viewPath + "admin/admin-user-management-page.fxml");
        FXRouter.when("admin-faculty-management", viewPath + "admin/admin-faculty-management-page.fxml");
        FXRouter.when("admin-department-management", viewPath + "admin/admin-department-management-page.fxml");
        FXRouter.when("admin-faculty-officer-management", viewPath + "admin/admin-faculty-officer-management-page.fxml");
        FXRouter.when("admin-department-officer-management", viewPath + "admin/admin-department-officer-management-page.fxml");
        FXRouter.when("admin-advisor-management", viewPath + "admin/admin-advisor-management-page.fxml");
        FXRouter.when("admin-dashboard", viewPath + "admin/admin-dashboard-page.fxml");
        FXRouter.when("student-register", viewPath + "student/student-register-page.fxml");
        FXRouter.when("student-request-form-tracking", viewPath + "student/student-request-form-tracking-page.fxml");
        FXRouter.when("advisor-request-form", viewPath + "advisor/advisor-request-form-page.fxml");
        FXRouter.when("advisor-student-information", viewPath + "advisor/advisor-student-information-page.fxml");
        FXRouter.when("advisor-student-request-form-history", viewPath + "advisor/advisor-student-request-form-history-page.fxml");
        FXRouter.when("faculty-officer-request-management", viewPath + "officer/faculty/faculty-officer-request-management-page.fxml");
        FXRouter.when("faculty-officer-request-approver-management", viewPath + "officer/faculty/faculty-officer-approver-management-page.fxml");
        FXRouter.when("department-officer-request-approver-management", viewPath + "officer/department/department-officer-approver-management-page.fxml");
        FXRouter.when("department-officer-request-management", viewPath + "officer/department/department-officer-request-management-page.fxml");
        FXRouter.when("department-officer-student-management", viewPath + "officer/department/department-officer-student-management-page.fxml");
    }
}