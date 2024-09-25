package ku.cs.services;

import java.util.ArrayList;

public class PermissionControlledCollection<MODEL> {
    private ArrayList<MODEL> nonPermittedModels;
    private ArrayList<MODEL> myModels;
    public PermissionControlledCollection(ArrayList<MODEL> myModels, ArrayList<MODEL> nonPermittedModels) {
        this.myModels = myModels;
        this.nonPermittedModels = nonPermittedModels;
    }

    public ArrayList<MODEL> getMyModels() {
        return myModels;
    }

    public void add(MODEL model){
        nonPermittedModels.add(model);
        myModels.add(model);
    }
}
