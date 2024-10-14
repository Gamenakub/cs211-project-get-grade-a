package ku.cs.controllers.components.navigationbars;

import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.services.Session;

public class IconController {
    private final ImageView logoutIcon;
    private final ImageView themeIcon;
    private final MenuButton fontSizeMenuButton;
    private final MenuButton fontStyleMenuButton;
    private final ImageView fontSizeImgView;
    private final ImageView fontStyleImgView;

    public IconController(ImageView logoutIcon, ImageView themeIcon, MenuButton fontSizeMenuButton, MenuButton fontStyleMenuButton) {
        this.logoutIcon = logoutIcon;
        this.themeIcon = themeIcon;
        this.fontSizeMenuButton = fontSizeMenuButton;
        this.fontStyleMenuButton = fontStyleMenuButton;
        fontStyleImgView = new ImageView();
        fontStyleImgView.setFitHeight(50);
        fontStyleImgView.setFitWidth(50);
        fontSizeImgView = new ImageView();
        fontSizeImgView.setFitHeight(50);
        fontSizeImgView.setFitWidth(50);
    }


    public void updateNavBar() {
        Image logoutIconImg;
        Image themeIconImg;
        Image fontSizeImg;
        Image fontStyleImg;
        if (!Session.getSession().getThemeProvider().getMode()) {
            logoutIconImg = new Image("/images/navbar-icons/logout-icon-light.png");
            themeIconImg = new Image("/images/navbar-icons/theme-icon-light.png");
            fontSizeImg = new Image("/images/navbar-icons/font-size-icon-light.png");
            fontStyleImg = new Image("/images/navbar-icons/font-style-icon-light.png");

        } else {
            logoutIconImg = new Image("/images/navbar-icons/logout-icon-dark.png");
            themeIconImg = new Image("/images/navbar-icons/theme-icon-dark.png");
            fontSizeImg = new Image("/images/navbar-icons/font-size-icon-dark.png");
            fontStyleImg = new Image("/images/navbar-icons/font-style-icon-dark.png");
        }
        fontStyleImgView.setImage(fontStyleImg);
        fontSizeImgView.setImage(fontSizeImg);

        logoutIcon.setImage(logoutIconImg);
        themeIcon.setImage(themeIconImg);
        fontSizeMenuButton.setGraphic(fontSizeImgView);
        fontStyleMenuButton.setGraphic(fontStyleImgView);

    }
}
