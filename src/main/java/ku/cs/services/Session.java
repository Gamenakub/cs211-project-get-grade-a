package ku.cs.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.models.users.User;

import java.io.IOException;

public class Session {
    private static Session session = null;
    private ThemeProvider themeProvider;
    private User loggedInUser;


    private Session() {
        themeProvider = new ThemeProvider();
    }

    public static Session getSession() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public void setUser(String username, String password) {
        User user;
        user = DataProvider.getDataProvider().setDataByRole(username, password);
        this.loggedInUser = user;
    }

    public ThemeProvider getThemeProvider() {
        return themeProvider;
    }


    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setNavbarByUserRole(Pane navbarPane) {
        String role = loggedInUser.getRole();
        FXMLLoader navbarPath = switch (role) {
            case "admin" -> new FXMLLoader(Session.class.getResource("/ku/cs/views/components/admin-navbar.fxml"));
            case "advisor" -> new FXMLLoader(Session.class.getResource("/ku/cs/views/components/advisor-navbar.fxml"));
            case "student" -> new FXMLLoader(Session.class.getResource("/ku/cs/views/components/student-navbar.fxml"));
            case "facultyOfficer" ->
                    new FXMLLoader(Session.class.getResource("/ku/cs/views/components/faculty-officer-navbar.fxml"));
            case "departmentOfficer" ->
                    new FXMLLoader(Session.class.getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
            default -> null;
        };

        try {
            AnchorPane navBar = null;
            if (navbarPath != null) {
                navBar = navbarPath.load();
            }
            navbarPane.getChildren().add(navBar);
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }


    public synchronized void clearSession() {
        DataProvider.getDataProvider().saveUser();
        DataProvider.getDataProvider().clearDataProvider();
        themeProvider=new ThemeProvider();
        loggedInUser = null;
    }


}
