package ku.cs.controllers.requestforms;

import javafx.animation.PauseTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import ku.cs.config.Configuration;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.services.AlertService;
import ku.cs.services.PdfManager;
import ku.cs.services.Session;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PreviewPdfPopupPageController extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private AnchorPane pdfAnchorPane;

    private RequestForm requestForm;
    private String pathFXML;

    @Override
    public void onPopupOpen() {
        requestForm = getModel().getFormObject();
        String pdfFileName = requestForm.getStudent().getStudentId() + "_" + requestForm.getRequestFormId() + ".pdf";
        File pdfDirectory = new File(Configuration.getConfiguration().getRequestFormsPdfPath());
        File outputPdfFile = new File(pdfDirectory, pdfFileName);

        if (outputPdfFile.exists()) {
            displayPDF(outputPdfFile);
        } else {
            generatePDF();
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> savePdfBySystem());
            pause.play();
        }
    }

    @FXML
    public void initialize() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
    }

    @FXML
    public void onClosePopupButton() {
        if (Session.getSession().getLoggedInUser() instanceof Advisor){
            this.close();
            return;
        }
        back();
    }

    public void savePdfBySystem() {
        try {
            PdfManager.saveByDefault(this.pdfAnchorPane, requestForm.getStudent().getStudentId() + "_" + requestForm.getRequestFormId());
        } catch (IOException e) {
            AlertService.showInfo("เกิดข้อผิดพลาดในสร้างไฟล์ PDF ลงระบบ");
        }
    }

    @FXML
    public void onSaveButtonClicked() {
        try {
            File outputPdfFile = PdfManager.saveByFileChooser(this.pdfAnchorPane, requestForm.getStudent().getStudentId() + "_" + requestForm.getRequestFormId());

            if (AlertService.showInfo("ดาวน์โหลดไฟล์ PDF เรียบร้อย")) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(outputPdfFile.toURI());
                } else {
                    AlertService.showWarning("ไม่สามารถเปิดไฟล์ PDF ผ่านบราวเซอร์ได้");
                }
            }
        } catch (IOException e) {
            AlertService.showInfo("คุณได้ยกเลิกการดาวน์โหลดไฟล์ PDF");
        }
    }

    private void displayPDF(File pdfFile) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            int targetWidth = 1654;
            int targetHeight = 2339;

            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 400);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(targetWidth);
            imageView.setFitHeight(targetHeight);
            imageView.setPreserveRatio(true);

            imageView.setScaleX(0.48367593712);
            imageView.setScaleY(0.48367593712);

            StackPane wrapperPane = new StackPane();
            wrapperPane.setAlignment(Pos.CENTER);
            wrapperPane.getChildren().add(imageView);

            AnchorPane.setTopAnchor(wrapperPane, 0.0);
            AnchorPane.setBottomAnchor(wrapperPane, 0.0);
            AnchorPane.setLeftAnchor(wrapperPane, 0.0);
            AnchorPane.setRightAnchor(wrapperPane, 0.0);
            pdfAnchorPane.setPrefHeight(1036);
            pdfAnchorPane.getChildren().clear();
            pdfAnchorPane.getChildren().setAll(wrapperPane);
        } catch (IOException e) {
            AlertService.showError("ไม่สามารถดาวน์โหลดไฟล์ PDF ได้: " + e.getMessage());
        }
    }


    private void generatePDF() {
        switch (requestForm) {
            case AddDropRequestForm addDropRequestForm -> pathFXML = "/ku/cs/views/request-forms/add-drop-form.fxml";
            case AbsenceRequestForm absenceRequestForm -> pathFXML = "/ku/cs/views/request-forms/absence-form.fxml";
            case CoEnrollRequestForm coEnrollRequestForm -> pathFXML = "/ku/cs/views/request-forms/co-enroll-form.fxml";
            case null, default -> AlertService.showWarning("พบข้อผิดพลาดไม่สามารถระบุประเภทใบคำร้องได้");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathFXML));
        fxmlLoader.getController();
        AnchorPane loadedPane;
        try {
            loadedPane = fxmlLoader.load();
            RequestFormController controller = fxmlLoader.getController();
            if (controller != null) {
                controller.prepareDataForPDF(requestForm);
                controller.initializeForm();
            }
        } catch (IOException e) {
            AlertService.showError("พบข้อผิดพลาดในการแสดง PDF");
            return;
        }

        loadedPane.setScaleX(0.48367593712);
        loadedPane.setScaleY(0.48367593712);

        StackPane wrapperPane = new StackPane();
        wrapperPane.setAlignment(Pos.CENTER);
        wrapperPane.getChildren().add(loadedPane);

        AnchorPane.setTopAnchor(wrapperPane, 0.0);
        AnchorPane.setBottomAnchor(wrapperPane, 0.0);
        AnchorPane.setLeftAnchor(wrapperPane, 0.0);
        AnchorPane.setRightAnchor(wrapperPane, 0.0);

        pdfAnchorPane.getChildren().setAll(wrapperPane);
    }
}
