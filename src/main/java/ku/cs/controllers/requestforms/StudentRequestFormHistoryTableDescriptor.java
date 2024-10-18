package ku.cs.controllers.requestforms;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ku.cs.controllers.components.tables.ColumnFactory;
import ku.cs.controllers.components.tables.HeaderMode;
import ku.cs.controllers.components.tables.TableColumn;
import ku.cs.models.requestforms.RequestForm;

public class StudentRequestFormHistoryTableDescriptor extends RequestFormsTableDescriptor {
    @Override
    @TableColumn(order = 5, name = "", size = 110, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> actionColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Button actionButton = new Button("ดูรายละเอียด");
                actionButton.getStyleClass().add("green-button");
                actionButton.setOnAction(actionEvent -> getTableComponentController().issueEvent("ดูคำร้อง", obj));
                return actionButton;
            }

            @Override
            public Node getHeadNode() {
                return new Label("");
            }
        };
    }
}
