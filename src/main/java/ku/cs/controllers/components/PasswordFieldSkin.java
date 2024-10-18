package ku.cs.controllers.components;

import javafx.scene.control.PasswordField;
import javafx.scene.control.skin.TextFieldSkin;

public class PasswordFieldSkin extends TextFieldSkin {
    public PasswordFieldSkin(PasswordField control) {
        super(control);
    }

    @Override
    protected String maskText(String text) {
        return "•".repeat(text.length());
    }
}