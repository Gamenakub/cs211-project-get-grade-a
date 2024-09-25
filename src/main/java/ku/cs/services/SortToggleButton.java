package ku.cs.services;

import javafx.scene.control.Button;

import javafx.scene.layout.HBox;
import ku.cs.controllers.components.tables.EventCallback;

public class SortToggleButton extends HBox {

    private boolean ascending = true; // Track sort direction
    private final Button sortButton;
    private EventCallback onToggle = (eventData) -> {};
    private String text;

    public void setToggle(boolean toggle) {
        ascending = toggle;
        updateButtonLabel();
    }

    public void setToggle(SortDirection direction) {
        setToggle(direction == SortDirection.ASCENDING);
    }

    public void onToggle(EventCallback callback) {
        this.onToggle = callback;
    }


    public SortToggleButton(String text) {
        this.text = text;
        sortButton = new Button();
        updateButtonLabel();

        // Style the button to look like a label
        sortButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent");
        // set css class
        //sortButton.getStyleClass().add("normal-text");

        // set vertical align center in box
        this.setAlignment(javafx.geometry.Pos.CENTER);


        // set background color to red
        //sortButton.setStyle("-fx-background-color: red;");


        // Add sorting functionality on click
        sortButton.setOnAction(e -> {
            ascending = !ascending; // Toggle direction
            updateButtonLabel(); // Update arrow
            updateToggleListenner(ascending ? SortDirection.ASCENDING : SortDirection.DESCENDING);
        });

        // Add the button to this custom widget
        this.getChildren().add(sortButton);
    }

    private void updateToggleListenner(SortDirection direction) {
        ascending = direction == SortDirection.ASCENDING;
        onToggle.onEvent(direction);
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
        updateButtonLabel();
    }

    // Update the button text based on sorting direction
    private void updateButtonLabel() {
        if (ascending) {
            sortButton.setText(this.text+" ▲"); // Ascending arrow
        } else {
            sortButton.setText(this.text+" ▼"); // Descending arrow
        }
    }

    // Get current sorting direction
    public boolean isAscending() {
        return ascending;
    }
}
