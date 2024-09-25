package ku.cs.controllers.components.tables;

public interface ModelFilter<MODEL> {
    boolean isInclude(MODEL model);
}
