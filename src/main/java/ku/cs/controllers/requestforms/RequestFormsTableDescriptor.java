package ku.cs.controllers.requestforms;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import ku.cs.controllers.components.tables.ColumnFactory;
import ku.cs.controllers.components.tables.HeaderMode;
import ku.cs.controllers.components.tables.TableColumn;
import ku.cs.controllers.components.tables.TableHeaderDescriptor;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.services.ThaiStringConverter;

import java.util.Comparator;

public class RequestFormsTableDescriptor extends TableHeaderDescriptor<RequestForm> {
    @TableColumn(order = 0, name = "เลขที่ใบคำร้อง", size = 140, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> requestIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Label label = new Label(obj.getRequestFormId());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<RequestForm> getComparator() {
                return Comparator.comparing(RequestForm::getRequestFormId);
            }
        };
    }

    @TableColumn(order = 1, name = "รหัสนิสิต", size = 140, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> studentIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Label label = new Label(obj.getStudent().getStudentId());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<RequestForm> getComparator() {
                return Comparator.comparing(o -> o.getStudent().getStudentId());
            }
        };
    }

    @TableColumn(order = 2, name = "หัวข้อเรื่อง", size = 164, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> requestFormTitleColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Label label = new Label(obj.getRequestFormTitle());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<RequestForm> getComparator() {
                return Comparator.comparing(RequestForm::getRequestFormTitle);
            }
        };
    }

    @TableColumn(order = 3, name = "แก้ไขล่าสุด", size = 148, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> timeStampColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Label label = new Label(ThaiStringConverter.getThaiTimeStampFormattedString(obj.getTimeStamp()));
                label.getStyleClass().add("table-text");
                label.setWrapText(true);

                label.setLineSpacing(-9.0);
                Insets currentPadding = label.getPadding();
                Insets newPadding = new Insets(
                        currentPadding.getTop(),
                        currentPadding.getRight(),
                        currentPadding.getBottom() + 2,
                        currentPadding.getLeft()
                );
                label.requestLayout();
                label.setPadding(newPadding);

                return label;
            }

            @Override
            public Comparator<RequestForm> getComparator() {
                return Comparator.comparing(RequestForm::getTimeStamp);
            }
        };
    }

    @TableColumn(order = 4, name = "สถานะ", size = 198, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> statusColumn() {
        return obj -> {
            Label label = new Label(obj.getStatus().getLabel());
            label.setLineSpacing(-9.0);
            Insets currentPadding = label.getPadding();
            Insets newPadding = new Insets(
                    currentPadding.getTop(),
                    currentPadding.getRight(),
                    currentPadding.getBottom() + 2,
                    currentPadding.getLeft()
            );
            label.setPadding(newPadding);
            label.getStyleClass().add("table-text");
            label.setTextAlignment(TextAlignment.CENTER);
            return label;
        };
    }

    @TableColumn(order = 5, name = "", size = 109, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> actionColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Button actionButton = new Button("ดำเนินการ");
                actionButton.setOnAction(actionEvent -> getTableComponentController().issueEvent("กดดำเนินการ", obj));
                actionButton.getStyleClass().add("green-button");
                return actionButton;
            }

            @Override
            public Node getHeadNode() {
                return new Label("");
            }
        };
    }
}
