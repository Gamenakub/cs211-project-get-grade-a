module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;

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
}