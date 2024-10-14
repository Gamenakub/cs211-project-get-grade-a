package ku.cs.controllers.components.tables;

public interface EventCompatible {
    void issueEvent(String eventName, Object eventData);

    void issueEvent(String eventName);

    void addEventListener(String eventName, EventCallback callback);
}
