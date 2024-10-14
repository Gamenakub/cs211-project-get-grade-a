package ku.cs.controllers.requestforms;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import ku.cs.controllers.components.tables.ColumnFactory;
import ku.cs.controllers.components.tables.HeaderMode;
import ku.cs.controllers.components.tables.TableColumn;
import ku.cs.controllers.components.tables.TableHeaderDescriptor;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.services.ThaiStringConverter;

import java.util.Comparator;

public class RequestFormHistoryTableDescriptor extends TableHeaderDescriptor<RequestFormActionHistory> {
    @TableColumn(order = 0, name = "ตำแหน่งผู้ดำเนินการ", size = 180, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestFormActionHistory> requestIdColumn() {
        return obj -> {
            Label label = new Label(obj.getApprovedByType());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 1, name = "ผู้ดำเนินการ", size = 180, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestFormActionHistory> studentIdColumn() {
        return obj -> {
            Label label = new Label(obj.getApproverIdentity());
            label.getStyleClass().add("table-text");
            label.setLineSpacing(-9.0);
            Insets currentPadding = label.getPadding();
            Insets newPadding = new Insets(
                    currentPadding.getTop(),
                    currentPadding.getRight(),
                    currentPadding.getBottom() + 2,
                    currentPadding.getLeft()
            );
            label.setPadding(newPadding);

            return label;
        };
    }

    @TableColumn(order = 2, name = "ผลการดำเนินการ", size = 180, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestFormActionHistory> requestFormTitleColumn() {
        return obj -> {
            Label label = new Label(obj.getAction().getLabel());
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
            label.setWrapText(true);
            return label;
        };
    }

    @TableColumn(order = 3, name = "เวลา", size = 180, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestFormActionHistory> timeStampColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestFormActionHistory obj) {
                Label label = new Label(ThaiStringConverter.getThaiTimeStampFormattedString(obj.getApprovedAt()));
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
            public Comparator<RequestFormActionHistory> getComparator() {
                return Comparator.comparing(RequestFormActionHistory::getApprovedAt);
            }
        };
    }

}
