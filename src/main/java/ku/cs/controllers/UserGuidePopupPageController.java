package ku.cs.controllers;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.services.AlertService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.util.Duration;

public class UserGuidePopupPageController extends BasePopup<Object> {
    @FXML private Pane pdfDisplayPane;
    private ImageView pdfImageView;
    private int maxIndexPage;
    private int indexPage = 0;
    private PDDocument pdfDocument;
    private Timeline timeline;

    @Override
    public void onPopupOpen() {
        indexPage = 0;
        displayUserGuide();
        displayPdf();
        addEventListener("close", event -> {
            if(timeline != null) {
                timeline.stop();
            }
            if (pdfDocument != null) {
                try {
                    pdfDocument.close();
                } catch (IOException e) {
                    AlertService.showError("พบข้อผิดพลาดในการปิดไฟล์ Pdf: " + e.getMessage());
                }
            }
        });
        startAutoNextPage();
    }

    private void startAutoNextPage() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> onNextPageButton()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void onPreviousPageButton() {
        indexPage = (indexPage - 1 + maxIndexPage + 1) % (maxIndexPage + 1);
        displayPdf();
    }

    public void onNextPageButton() {
        indexPage = (indexPage + 1) % (maxIndexPage + 1);
        displayPdf();
    }

    private void displayUserGuide() {
        try (InputStream pdfStream = getClass().getClassLoader().getResourceAsStream("user-guide.pdf")) {
            if (pdfStream == null) {
                AlertService.showError("ไม่พบไฟล์ Pdf: " + "user-guide.pdf");
                return;
            }
            pdfDocument = PDDocument.load(pdfStream);
            maxIndexPage = pdfDocument.getNumberOfPages() - 1;
            pdfImageView = new ImageView();
            pdfImageView.setFitHeight(480);
            pdfImageView.setFitWidth(854);
            pdfDisplayPane.getChildren().add(pdfImageView);
        } catch (IOException e) {
            AlertService.showError("พบข้อผิดพลาดในการแสดงผลไฟล์ Pdf: " + e.getMessage());
        }
    }

    public void displayPdf() {
        if (pdfDocument == null) return;
        try {
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(indexPage, 300);
            pdfImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        } catch (IOException e) {
            AlertService.showError("พบข้อผิดพลาดในการแสดงผลไฟล์ Pdf: " + e.getMessage());
        }
    }

    @FXML
    public void onManualButton() {
        openManualPdf();
    }

    private void openManualPdf() {
        try (InputStream pdfStream = getClass().getClassLoader().getResourceAsStream("user-manual.pdf")) {
            if (pdfStream == null) {
                AlertService.showError("ไม่พบไฟล์ Pdf: " + "user-manual.pdf");
                return;
            }
            Path tempFile = Files.createTempFile("tempPdf", ".pdf");
            Files.copy(pdfStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            File pdfFile = tempFile.toFile();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(pdfFile.toURI());
            } else {
                AlertService.showError("ระบบนี้ไม่รองรับการแสดงผลไฟล์ Pdf บนบราวเซอร์");
            }
        } catch (IOException e) {
            AlertService.showError("พบข้อผิดพลาดในการแสดงผลไฟล์ Pdf:" + e.getMessage());
        }
    }
}
