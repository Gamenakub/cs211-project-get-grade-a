package ku.cs.controllers.components.tables;

public record TableColumnAnnotationAttribute(
        int order, String name, int size, HeaderMode headerMode
) {
}
