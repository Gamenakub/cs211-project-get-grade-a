package ku.cs.controllers.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.collections.UserList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.User;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;

import java.util.ArrayList;

public class DashboardMenuButtonController {

    private Label totalSuccessFormFilteredLabel;
    private Label totalFacultyOfficerLabel;
    private Label totalDepartmentOfficerLabel;
    private Label totalAdvisorLabel;
    private Label totalStudentLabel;
    private UserList allUser;
    private RequestFormList allRequestForm;


    public DashboardMenuButtonController(Label totalSuccessFormFilteredLabel, Label totalFacultyOfficerLabel, Label totalDepartmentOfficerLabel, Label totalAdvisorLabel, Label totalStudentLabel, UserList userList, RequestFormList requestFormList) {
        this.totalSuccessFormFilteredLabel = totalSuccessFormFilteredLabel;
        this.totalFacultyOfficerLabel = totalFacultyOfficerLabel;
        this.totalDepartmentOfficerLabel = totalDepartmentOfficerLabel;
        this.totalAdvisorLabel = totalAdvisorLabel;
        this.totalStudentLabel = totalStudentLabel;
        this.allUser = userList;
        this.allRequestForm = requestFormList;
    }


    public void addItemsDepartmentDashBoard(MenuButton departmentMenuButton, ArrayList<Department> departments, Faculty faculty,String mode) {
        MenuItem allDepartmentMenuItem=departmentMenuButton.getItems().getFirst();
        allDepartmentMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(mode=="User") setUsersDataByFaculty(faculty);
                else if (mode=="Form") setTotalFormByFaculty(faculty);
                departmentMenuButton.setText("ทั้งหมด");
            }
        });
        departmentMenuButton.getItems().clear();
        departmentMenuButton.getItems().add(allDepartmentMenuItem);
        departmentMenuButton.setText("ทั้งหมด");
        for(Department department : departments) {
            MenuItem departmentItem = new MenuItem(department.getName());
            departmentMenuButton.getItems().add(departmentItem);
            departmentItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    departmentMenuButton.setText(departmentItem.getText());
                    departmentMenuButton.setUserData(department);
                    if (mode == "User") setUsersDataByDepartment(department);
                    else if (mode == "Form") setTotalFormByDepartment(department);
                }
            });
        }
        departmentMenuButton.getItems();
    }

    public void addItemsDepartmentDashBoard(MenuButton facultyMenuButton, MenuButton departmentMenuButton, ArrayList<Faculty> faculties, UserList userList, String mode) {
        for(Faculty faculty : faculties) {
            MenuItem facultyItem = new MenuItem(faculty.getName());
            facultyMenuButton.getItems().add(facultyItem);
            facultyItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    facultyMenuButton.setText(facultyItem.getText());
                    facultyMenuButton.setUserData(faculty);
                    addItemsDepartmentDashBoard(departmentMenuButton, faculty.getDepartmentList().getDepartments(),faculty,mode);
                    if (mode == "User") setUsersDataByFaculty(faculty);
                    else if (mode == "Form") setTotalFormByFaculty(faculty);
                }
            });
        }
        facultyMenuButton.getItems();
    }


    private void setUsersDataByDepartment(Department department){
        UserList userList=allUser;
        ArrayList<DepartmentOfficer> departmentOfficers = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<FacultyOfficer> facultyOfficers = new ArrayList<>();
        ArrayList<Advisor> advisors = new ArrayList<>();
        for (User user : userList.getUsers()) {
            if (user instanceof DepartmentOfficer) {
                DepartmentOfficer departmentOfficer = (DepartmentOfficer) user;
                if (((DepartmentOfficer) user).getDepartment().equals(department)) departmentOfficers.add(departmentOfficer);
            } else if (user instanceof Student) {
                Student student = (Student) user;
                if (((Student) user).getDepartment().equals(department)) students.add(student);
            } else if (user instanceof FacultyOfficer) {
                FacultyOfficer facultyOfficer = (FacultyOfficer) user;
                if (((FacultyOfficer) user).getFaculty().equals(department.getFaculty())) facultyOfficers.add(facultyOfficer);
            } else if (user instanceof Advisor) {
                Advisor advisor = (Advisor) user;
                if (((Advisor) user).getDepartment().equals(department)) advisors.add(advisor);
            }
        }
        totalStudentLabel.setText(students.size() + "");
        totalFacultyOfficerLabel.setText(facultyOfficers.size() + "");
        totalAdvisorLabel.setText(advisors.size() + "");
        totalDepartmentOfficerLabel.setText(departmentOfficers.size() + "");
    }

    private void setUsersDataByFaculty(Faculty faculty){
        UserList userList=allUser;
        ArrayList<DepartmentOfficer> departmentOfficers = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<FacultyOfficer> facultyOfficers = new ArrayList<>();
        ArrayList<Advisor> advisors = new ArrayList<>();
        for (User user : userList.getUsers()) {
            if (user instanceof DepartmentOfficer) {
                DepartmentOfficer departmentOfficer = (DepartmentOfficer) user;
                if (((DepartmentOfficer) user).getFaculty().equals(faculty)) departmentOfficers.add(departmentOfficer);
            } else if (user instanceof Student) {
                Student student = (Student) user;
                if (((Student) user).getFaculty().equals(faculty)) students.add(student);
            } else if (user instanceof FacultyOfficer) {
                FacultyOfficer facultyOfficer = (FacultyOfficer) user;
                if (((FacultyOfficer) user).getFaculty().equals(faculty)) facultyOfficers.add(facultyOfficer);
            } else if (user instanceof Advisor) {
                Advisor advisor = (Advisor) user;
                if (((Advisor) user).getFaculty().equals(faculty)) advisors.add(advisor);
            }
        }
        totalStudentLabel.setText(students.size() + "");
        totalFacultyOfficerLabel.setText(facultyOfficers.size() + "");
        totalAdvisorLabel.setText(advisors.size() + "");
        totalDepartmentOfficerLabel.setText(departmentOfficers.size() + "");
    }

    // TODO: SET FACULTY TOO
    private void setTotalFormByFaculty(Faculty faculty){

        int size=allRequestForm.findRequestFormsByFaculty(faculty).findRequestFormsByStatus(RequestForm.Status.APPROVED_BY_DEPARTMENT).getRequestForms().size();
        totalSuccessFormFilteredLabel.setText("" + size);

    }

    private void setTotalFormByDepartment(Department department){
        int size=allRequestForm.findRequestFormsByDepartment(department).findRequestFormsByStatus(RequestForm.Status.APPROVED_BY_DEPARTMENT).getRequestForms().size();
        totalSuccessFormFilteredLabel.setText("" + size);
    }

}
