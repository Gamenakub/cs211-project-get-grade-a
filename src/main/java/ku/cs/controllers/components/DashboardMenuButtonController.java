package ku.cs.controllers.components;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import ku.cs.controllers.admin.AdminDashboardPageController;
import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.collections.UserList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Admin;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.User;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.Session;

import java.util.ArrayList;

public class DashboardMenuButtonController {

    private final AdminDashboardPageController adminDashboardPageController;
    private Faculty formFaculty = null;
    private Department formDepartment = null;
    private Faculty userFaculty = null;
    private Department userDepartment = null;

    public  DashboardMenuButtonController(AdminDashboardPageController adminDashboardPageController){
        this.adminDashboardPageController = adminDashboardPageController;
    }

    public void addItemsDepartmentDashBoard(MenuButton departmentMenuButton, ArrayList<Department> departments, Faculty faculty, String mode) {

        MenuItem allDepartmentMenuItem = departmentMenuButton.getItems().getFirst();
        allDepartmentMenuItem.setOnAction(event -> {
            if (mode.equals("User")) {
                setUsersDataByFaculty(faculty);
                userDepartment = null;
            } else if (mode.equals("Form")) {
                setTotalFormByFaculty(faculty);
                formDepartment = null;
            }
            departmentMenuButton.setText("ทั้งหมด");
            update();
        });
        departmentMenuButton.getItems().clear();
        departmentMenuButton.getItems().add(allDepartmentMenuItem);
        departmentMenuButton.setText("ทั้งหมด");

        for (Department department : departments) {
            MenuItem departmentItem = new MenuItem(department.getName());
            departmentMenuButton.getItems().add(departmentItem);
            departmentItem.setOnAction(event -> {
                departmentMenuButton.setText(department.getName());
                departmentMenuButton.setUserData(department);
                if (mode.equals("User")) {
                    userDepartment = department;
                } else if (mode.equals("Form")) {
                    formDepartment = department;
                }
                update();
            });
        }
    }

    public void addItemsDepartmentDashBoard(MenuButton facultyMenuButton, MenuButton departmentMenuButton, ArrayList<Faculty> faculties, String mode) {
        for (Faculty faculty : faculties) {
            MenuItem facultyItem = new MenuItem(faculty.getName());
            facultyMenuButton.getItems().add(facultyItem);
            facultyItem.setOnAction(event -> {
                facultyMenuButton.setText(facultyItem.getText());
                addItemsDepartmentDashBoard(departmentMenuButton, faculty.getDepartmentList().getDepartments(), faculty, mode);
                if (mode.equals("User")) {
                    userFaculty = faculty;
                    userDepartment = null;
                } else if (mode.equals("Form")) {
                    formFaculty = faculty;
                    formDepartment = null;
                }
                update();
            });
        }
    }

    private void setUsersDataByDepartment(Department department){
        UserList userList=((Admin)Session.getSession().getLoggedInUser()).getUserList();
        ArrayList<DepartmentOfficer> departmentOfficers = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<FacultyOfficer> facultyOfficers = new ArrayList<>();
        ArrayList<Advisor> advisors = new ArrayList<>();
        for (User user : userList.getUsers()) {
            if (user instanceof DepartmentOfficer departmentOfficer) {
                if (((DepartmentOfficer) user).getDepartment().equals(department)) departmentOfficers.add(departmentOfficer);
            } else if (user instanceof Student student) {
                if (student.getDepartment().equals(department)) students.add(student);
            } else if (user instanceof FacultyOfficer facultyOfficer) {
                if (facultyOfficer.getFaculty().equals(department.getFaculty())) facultyOfficers.add(facultyOfficer);
            } else if (user instanceof Advisor advisor) {
                if (((Advisor) user).getDepartment().equals(department)) advisors.add(advisor);
            }
        }

        adminDashboardPageController.setUserData(students.size(),advisors.size(),facultyOfficers.size(),departmentOfficers.size());
    }

    private void setUsersDataByFaculty(Faculty faculty) {
        UserList userList = ((Admin) Session.getSession().getLoggedInUser()).getUserList();
        ArrayList<DepartmentOfficer> departmentOfficers = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<FacultyOfficer> facultyOfficers = new ArrayList<>();
        ArrayList<Advisor> advisors = new ArrayList<>();
        for (User user : userList.getUsers()) {
            if (user instanceof DepartmentOfficer departmentOfficer) {
                if (((DepartmentOfficer) user).getFaculty().equals(faculty)) departmentOfficers.add(departmentOfficer);
            } else if (user instanceof Student student) {
                if (((Student) user).getFaculty().equals(faculty)) students.add(student);
            } else if (user instanceof FacultyOfficer facultyOfficer) {
                if (((FacultyOfficer) user).getFaculty().equals(faculty)) facultyOfficers.add(facultyOfficer);
            } else if (user instanceof Advisor advisor) {
                if (((Advisor) user).getFaculty().equals(faculty)) advisors.add(advisor);
            }
        }
        adminDashboardPageController.setUserData(students.size(),advisors.size(),facultyOfficers.size(),departmentOfficers.size());
    }

    private void setTotalFormByFaculty(Faculty faculty) {
        RequestFormList target = ((Admin)Session.getSession().getLoggedInUser()).getRequestFormList().findRequestFormsByFaculty(faculty);
        int size = target.findRequestFormsByStatus(RequestForm.Status.APPROVED_BY_DEPARTMENT).getRequestForms().size() + target.findRequestFormsByStatus(RequestForm.Status.APPROVED_BY_FACULTY).getRequestForms().size();
        adminDashboardPageController.setFormData(size);
    }

    private void setTotalFormByDepartment(Department department) {
        RequestFormList target = ((Admin)Session.getSession().getLoggedInUser()).getRequestFormList().findRequestFormsByDepartment(department);
        int size = target.findRequestFormsByStatus(RequestForm.Status.APPROVED_BY_DEPARTMENT).getRequestForms().size() + target.findRequestFormsByStatus(RequestForm.Status.APPROVED_BY_FACULTY).getRequestForms().size();
        adminDashboardPageController.setFormData(size);
    }

    public void clearFormFilter() {
        formFaculty = null;
        formDepartment = null;
    }

    public void clearUserFilter() {
        userFaculty = null;
        userDepartment = null;
    }

    public void update() {
        if (Session.getSession().getLoggedInUser() instanceof Admin admin) {
            if (userDepartment != null) {
                userDepartment = admin.getDepartmentList().findDepartmentById(userDepartment.getId());
                setUsersDataByDepartment(userDepartment);
            } else if (userFaculty != null) {
                userFaculty = admin.getFacultyList().findFacultyById(userFaculty.getId());
                setUsersDataByFaculty(userFaculty);
            }
            if (formDepartment != null) {
                formDepartment = admin.getDepartmentList().findDepartmentById(formDepartment.getId());
                setTotalFormByDepartment(formDepartment);
            } else if (formFaculty != null) {
                formFaculty = admin.getFacultyList().findFacultyById(formFaculty.getId());
                setTotalFormByFaculty(formFaculty);
            }
        }
    }

}
