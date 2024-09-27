package ku.cs.services.popup;

public class PopupHistory {
    private String fxmlPath;
    private String title;
    public PopupHistory(String fxmlPath, String title) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
    public String getTitle(){
        return title;
    }
}
