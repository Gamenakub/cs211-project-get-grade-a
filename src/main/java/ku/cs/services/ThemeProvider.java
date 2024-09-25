package ku.cs.services;

import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.util.Duration;

public class ThemeProvider {

    boolean isDarkMode = false;
    private String fontSize = "medium";
    private String fontStyle = "Sarabun";

    public void setTheme(Parent anchorPane) {
        if (isDarkMode) {
            anchorPane.getStyleClass().remove("default-theme");
            anchorPane.getStyleClass().add("dark-theme");
        } else {
            anchorPane.getStyleClass().remove("dark-theme");
            anchorPane.getStyleClass().add("default-theme");
        }
        changeFontSize(anchorPane,fontSize);
        changeFontStyle(anchorPane,fontStyle);
    }
    public void changeTheme(Parent anchorPane){
        isDarkMode = !isDarkMode;

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), anchorPane);
        fadeOut.setFromValue(5.0);
        fadeOut.setToValue(0.2);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), anchorPane);
        fadeIn.setFromValue(0.2);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(event -> {
            if (isDarkMode) {
                anchorPane.getStyleClass().remove("default-theme");
                anchorPane.getStyleClass().add("dark-theme");
            } else {
                anchorPane.getStyleClass().remove("dark-theme");
                anchorPane.getStyleClass().add("default-theme");
            }

            fadeIn.play(); // Start fading in the new theme
        });

        fadeOut.play();
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public void changeFontSize(Parent anchorPane,String size) {
        setFontSize(size);
        if (fontSize.equals("Large")) {
            anchorPane.getStyleClass().remove("font-size-small");
            anchorPane.getStyleClass().add("font-size-large");
            fontSize = "Large";
        }
        else if(fontSize.equals("Medium")) {
            anchorPane.getStyleClass().remove("font-size-small");
            anchorPane.getStyleClass().remove("font-size-large");
            fontSize = "Medium";
        }
        else if(fontSize.equals("Small")) {
            anchorPane.getStyleClass().remove("font-size-large");
            anchorPane.getStyleClass().add("font-size-small");
            fontSize = "Small";
        }
    }
    public String getFontSize() {
        return fontSize;
    }

    public void changeFontStyle(Parent anchorPane,String style) {
        setFontStyle(style);
        if (fontStyle.equals("Maligrade")) {
            anchorPane.getStyleClass().remove("angsana-new-font");
            anchorPane.getStyleClass().add("mali-grade-font");
            fontStyle = "Maligrade";
        }
        else if(fontStyle.equals("Sarabun")) {
            anchorPane.getStyleClass().remove("angsana-new-font");
            anchorPane.getStyleClass().remove("mali-grade-font");
            fontStyle = "Sarabun";
        }
        else if(fontStyle.equals("Angsananew")) {
            anchorPane.getStyleClass().remove("mali-grade-font");
            anchorPane.getStyleClass().add("angsana-new-font");
            fontStyle = "Angsananew";
        }
        anchorPane.getStyleClass();
    }


    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }
}
