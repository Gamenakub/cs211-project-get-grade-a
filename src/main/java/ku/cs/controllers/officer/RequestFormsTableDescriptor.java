package ku.cs.controllers.officer;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ku.cs.controllers.components.tables.ColumnFactory;
import ku.cs.controllers.components.tables.HeaderMode;
import ku.cs.controllers.components.tables.TableColumn;
import ku.cs.controllers.components.tables.TableHeaderDescriptor;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.services.SortDirection;
import ku.cs.services.SortToggleButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class RequestFormsTableDescriptor extends TableHeaderDescriptor<RequestForm> {
    @TableColumn(order = 0, name = "เลขที่ใบคำร้อง", size = 150,headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> requestIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Label label=new Label(obj.getRequestFormId());
                label.getStyleClass().add("table-text");
                return label;
            }


            @Override
            public Comparator<RequestForm> getComparator() {
                return Comparator.comparing(RequestForm::getRequestFormId);
            }
        };
    }

    @TableColumn(order = 1, name = "รหัสนิสิต/คณะ", size = 150,headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> studentIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Label label=new Label(obj.getStudent().getStudentId() + "\n" + obj.getStudent().getFaculty().getName());
                label.getStyleClass().add("table-text");
                return label;
            }


            @Override
            public Comparator<RequestForm> getComparator() {
                return Comparator.comparing(o -> o.getStudent().getStudentId());
            }
        };
    }

    @TableColumn(order = 2, name = "หัวข้อเรื่อง", size = 150,headerMode = HeaderMode.DEFAULT)
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

    @TableColumn(order = 3, name = "แก้ไขล่าสุด", size = 150)
    public ColumnFactory<RequestForm> timeStampColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Label label = new Label(obj.getTimeStamp());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                SortToggleButton sortButton = new SortToggleButton("แก้ไขล่าสุด");
                sortButton.getStyleClass().add("table-text");

                sortButton.onToggle(sortDirection -> {
                    getTableComponentController().sortBy("แก้ไขล่าสุด", (SortDirection) sortDirection);
                });
                getTableComponentController().addEventListener("resetSort", (event) -> {
                    sortButton.setToggle(false);
                });
                return sortButton;
            }

            @Override
            public Comparator<RequestForm> getComparator() {
                // compare as date time
                return (o1, o2) -> {
                    String timeStamp1 = o1.getTimeStamp();
                    String timeStamp2 = o2.getTimeStamp();
                    // timestamp in format dd/MM/yyyy HH:mm:ss
                    LocalDateTime dateTime1 = LocalDateTime.parse(timeStamp1, DateTimeFormatter.ofPattern("dd/MM/yyyy\nHH:mm:ss"));
                    LocalDateTime dateTime2 = LocalDateTime.parse(timeStamp2, DateTimeFormatter.ofPattern("dd/MM/yyyy\nHH:mm:ss"));
                    return dateTime1.compareTo(dateTime2);
                };
            }
        };
    }

    @TableColumn(order = 4, name = "สถานะ", size = 200, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> statusColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                if (obj.getStatus() == RequestForm.Status.PENDING_TO_DEPARTMENT) {
                    Label label =new Label("อนุมัติโดยอาจารย์ที่ปรึกษา\nคำร้องส่งต่อให้หัวหน้าภาควิชา");
                    label.getStyleClass().add("table-text");
                    return label;
                } else if (obj.getStatus() == RequestForm.Status.REJECTED_BY_ADVISOR) {
                    Label label = new Label("ไม่อนุมัติโดยอาจารย์ที่ปรึกษา");
                    label.getStyleClass().add("table-text");
                    return label;
                } else if (obj.getStatus() == RequestForm.Status.PENDING_TO_ADVISOR) {
                    Label label = new Label("รอการอนุมัติจากอาจารย์ที่ปรึกษา");
                    label.getStyleClass().add("table-text");
                    return label;
                } else if (obj.getStatus() == RequestForm.Status.CANCELED_BY_STUDENT) {
                    Label label = new Label("ยกเลิกโดยนิสิต");
                    label.getStyleClass().add("table-text");
                    return label;
                } else if (obj.getStatus() == RequestForm.Status.APPROVED_BY_DEPARTMENT){
                    Label label = new Label("อนุมัติโดยหัวหน้าภาควิชา");
                    label.getStyleClass().add("table-text");
                    return label;
                } else if (obj.getStatus() == RequestForm.Status.REFUSED_BY_DEPARTMENT){
                    Label label = new Label("ไม่อนุมัติโดยหัวหน้าภาควิชา");
                    label.getStyleClass().add("table-text");
                    return label;
                } else if (obj.getStatus() == RequestForm.Status.PENDING_TO_FACULTY){
                    Label label = new Label("รอการอนุมัติจากคณบดี");
                    label.getStyleClass().add("table-text");
                    return label;
                }else if (obj.getStatus() == RequestForm.Status.REFUSED_BY_FACULTY){
                    Label label = new Label("ไม่อนุมัติโดยคณบดี");
                    label.getStyleClass().add("table-text");
                    return label;
                } else if (obj.getStatus() == RequestForm.Status.APPROVED_BY_FACULTY){
                    Label label = new Label("อนุมัติโดยคณบดี");
                    label.getStyleClass().add("table-text");
                    return label;
                }
                else {
                    Label unknowLabel = new Label("ไม่ทราบสถานะ");
                    unknowLabel.getStyleClass().add("table-text");
                    return unknowLabel;
                }

            }
        };
    }

    @TableColumn(order = 5, name = "", size = 110, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<RequestForm> actionColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(RequestForm obj) {
                Button actionButton = new Button("ดำเนินการ");
                actionButton.setOnAction(actionEvent -> {
                    getTableComponentController().issueEvent("กดดำเนินการ", obj);
                });
                return actionButton;
            }

            @Override
            public Node getHeadNode() {
                return new Label("");
            }
        };
    }
}
