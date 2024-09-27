package ku.cs.services.popup;

import java.util.Stack;

public class PopupHistoryList {
    // stack
    private Stack<PopupHistory> popupHistoryStack = new Stack<>();

    public void addHistory(PopupHistory history) {
        popupHistoryStack.push(history);
    }

    public PopupHistory pop() {
        return popupHistoryStack.pop();
    }
}
