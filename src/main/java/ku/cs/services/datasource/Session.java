package ku.cs.services.datasource;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.models.users.User;
import ku.cs.services.ThemeProvider;

import java.io.IOException;

public class Session {
    private static Session session=null;
    private User loggedInUser;
//    private DataProvider dataProvider;
    private ThemeProvider themeProvider=new ThemeProvider();

    public static Session getSession() {
        if(session==null) {
            session = new Session();
        }
        return session;
    }

    public void setUser(String username,String password) throws Exception {
        User user;
        try {
            user=DataProvider.getDataProvider().setDataByRole(username,password);
        }catch (Exception e) {throw new Exception(e.getMessage());}

        this.loggedInUser=user;
    }

    public ThemeProvider getTheme(){
        return themeProvider;
    }


    public User getLoggedInUser() {
        return loggedInUser;
    }

    public FXMLLoader setNavbar(Pane navbarPane) {
        String role = loggedInUser.getRole();
        FXMLLoader navbarPath;
        switch (role) {
            case "admin":
                navbarPath = new FXMLLoader(Session.class.getResource("/ku/cs/views/components/admin-navbar.fxml"));
                break;
            case "advisor":
                navbarPath = new FXMLLoader(Session.class.getResource("/ku/cs/views/components/advisor-navbar.fxml"));
                break;
            case "student":
                navbarPath = new FXMLLoader(Session.class.getResource("/ku/cs/views/components/student-navbar.fxml"));
                break;
            case "facultyOfficer":
                navbarPath = new FXMLLoader(Session.class.getResource("/ku/cs/views/components/faculty-officer-navbar.fxml"));
                break;
            case "departmentOfficer":
                navbarPath = new FXMLLoader(Session.class.getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
                break;
            default:
                navbarPath = null;
                break;
        }
        FXMLLoader navBarFxmlLoader=navbarPath;

        try {
            AnchorPane advisorNavBar = navBarFxmlLoader.load();
            navbarPane.getChildren().add(advisorNavBar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return navbarPath;
    }


    public void clearSession(){
        DataProvider.getDataProvider().saveUser(loggedInUser);
        session = null;
    }


}
