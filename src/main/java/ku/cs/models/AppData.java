package ku.cs.models;

public class AppData {
    private String key;
    private String value;
    public AppData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isKey(String key) {
        return this.key.equals(key);
    }

    public String getKey() {
        return key;
    }
}
