package ku.cs.controllers.officer.department;

import java.util.ArrayList;

public class PendingAction{
    private ArrayList<AwaitedAction> pendingCallback = new ArrayList<>();
    private ArrayList<AwaitedAction> cancleCallback = new ArrayList<>();
    private AwaitedAction onSuccessfulAction = () -> {};
    public void addPendingAction(AwaitedAction callback){
        pendingCallback.add(callback);
    }
    public void executePendingAction(){
        for (AwaitedAction callback : pendingCallback) {
            callback.onAction();
        }
        onSuccessfulAction.onAction();
        cancelPendingAction();
    }
    public void addCancleAction(AwaitedAction callback){
        cancleCallback.add(callback);
    }
    public void executeCancleAction(){
        for (AwaitedAction callback : cancleCallback) {
            callback.onAction();
        }
        cancelPendingAction();
    }

    public void setOnSuccessfulAction(AwaitedAction callback){
        onSuccessfulAction = callback;
    }

    public void cancelPendingAction(){
        pendingCallback.clear();
        cancleCallback.clear();
    }
}