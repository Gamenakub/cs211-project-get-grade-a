package ku.cs.services.popup;

public record PopupHistory(String fxmlPath, String title) {
    public String getFxmlPath() {
        return fxmlPath;
    }

    public String getTitle() {
        return title;
    }
}
