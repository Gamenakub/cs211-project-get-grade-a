package ku.cs.controllers.components.navigationbars;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;

import java.io.IOException;

public class AdvisorNavigationBarController {
    @FXML
    AnchorPane navBarAnchorPane;

    @FXML
    MenuButton fontSizeMenuButton;
    @FXML
    MenuItem largeItem;
    @FXML
    MenuItem mediumItem;
    @FXML
    MenuItem smallItem;

    @FXML
    MenuButton fontStyleMenuButton;
    @FXML
    MenuItem sarabunItem;
    @FXML
    MenuItem angsanaItem;
    @FXML
    MenuItem maligradeItem;

    @FXML
    ImageView imgButton;

    @FXML
    Circle profilePictureCircle;
    public void initialize() {
        ProfilePictureController.setImageToCircle(profilePictureCircle, Session.getSession().getLoggedInUser().getProfilePictureFileName());
        fontSizeMenuButton.setText(Session.getSession().getTheme().getFontSize());


        // สร้าง ContextMenu (เมนูที่คล้ายกับ MenuButton)
//        ContextMenu contextMenu = new ContextMenu();
//        MenuItem item1 = new MenuItem("Option 1");
//        MenuItem item2 = new MenuItem("Option 2");
//        contextMenu.getItems().addAll(item1, item2);
//
//        // กำหนด Event ให้คลิกขวาที่ ImageView เพื่อแสดง ContextMenu
//        imgButton.setOnMouseClicked(event -> {
//            if (event.getButton() == MouseButton.PRIMARY) {
//                contextMenu.show(imgButton, event.getScreenX(), event.getScreenY());
//            }
//        });
//        fontSizeMenuButton.setGraphic(imageView);
    }

    @FXML
    public void onPersonalInformationManagementButton(){
        try {
            FXRouter.goTo("user-personal-information-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onNisitListPageButtonClick() {
        try {
            FXRouter.goTo("advisor-student-information");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onFormManagePageButtonClick() {
        try {
            FXRouter.goTo("advisor-request-form");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onLogoutButton() {
        Session.getSession().clearSession();
        try {
            FXRouter.goTo("user-login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    public void onThemeButtonClick(){
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeTheme(root);
    }

    @FXML
    public void onLargeItemClick(){
        fontSizeMenuButton.setText(largeItem.getText());
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeFontSize(root,fontSizeMenuButton.getText());
    }

    @FXML
    public void onSmallItemClick() {
        fontSizeMenuButton.setText(smallItem.getText());
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeFontSize(root,fontSizeMenuButton.getText());
    }

    @FXML
    public void onMediumItemClick() {
        fontSizeMenuButton.setText(mediumItem.getText());
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeFontSize(root,fontSizeMenuButton.getText());
    }

    @FXML
    public void onSarabunItemClick(){
        fontStyleMenuButton.setText(sarabunItem.getText());
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeFontStyle(root,fontStyleMenuButton.getText());
    }

    @FXML
    public void onMaliGradeItemClick() {
        fontStyleMenuButton.setText(maligradeItem.getText());
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeFontStyle(root,fontStyleMenuButton.getText());
    }

    @FXML
    public void onAngsanaNewItemClick() {
        fontStyleMenuButton.setText(angsanaItem.getText());
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeFontStyle(root,fontStyleMenuButton.getText());
        System.out.println("root");
    }
}
