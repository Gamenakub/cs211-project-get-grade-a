module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;
    requires bcrypt;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.pdfbox;

    opens ku.cs.cs211671project to javafx.fxml;
    exports ku.cs.cs211671project;

    exports ku.cs.controllers;
    exports ku.cs.controllers.components;

    opens ku.cs.controllers to javafx.fxml;
    opens ku.cs.controllers.components to javafx.fxml;

    exports ku.cs.controllers.admin;
    opens ku.cs.controllers.admin to javafx.fxml;

    exports ku.cs.controllers.student;
    opens ku.cs.controllers.student to javafx.fxml;

    exports ku.cs.controllers.advisor;
    opens ku.cs.controllers.advisor to javafx.fxml;

    exports ku.cs.controllers.requestforms;
    opens ku.cs.controllers.requestforms to javafx.fxml;

    exports ku.cs.models.requestforms;
    opens ku.cs.models.requestforms to javafx.fxml;

    exports ku.cs.models.users;
    opens ku.cs.models.users to javafx.fxml;

    exports ku.cs.models.users.officers;
    opens ku.cs.models.users.officers to javafx.fxml;

    exports ku.cs.models.collections;
    opens ku.cs.models.collections to javafx.fxml;

    exports ku.cs.models;
    opens ku.cs.models to javafx.fxml;

    exports ku.cs.controllers.officer.department;
    opens ku.cs.controllers.officer.department to javafx.fxml;

    exports ku.cs.controllers.officer.faculty;
    opens ku.cs.controllers.officer.faculty to javafx.fxml;

    exports ku.cs.controllers.officer;
    opens ku.cs.controllers.officer to javafx.fxml;

    exports ku.cs.services to javafx.fxml;
    opens ku.cs.services to javafx.fxml;
    exports ku.cs.services.datasource to javafx.fxml;
    opens ku.cs.services.datasource to javafx.fxml;
    exports ku.cs.services.popup to javafx.fxml;
    opens ku.cs.services.popup to javafx.fxml;
    exports ku.cs.controllers.components.tables;
    opens ku.cs.controllers.components.tables to javafx.fxml;
    exports ku.cs.controllers.components.navigationbars;
    opens ku.cs.controllers.components.navigationbars to javafx.fxml;
}