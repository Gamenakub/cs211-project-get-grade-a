package ku.cs.models;

public class ApplicationRecord {
    private final String key;
    private String value;

    public ApplicationRecord(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isKey(String key) {
        return this.key.equals(key);
    }

    public String getKey() {
        return key;
    }

}
