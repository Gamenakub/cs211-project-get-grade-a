package ku.cs.models.collections;

import ku.cs.models.users.officers.Officer;

import java.util.ArrayList;

public class OfficerList {
    protected final ArrayList<Officer> officers;

    public OfficerList() {
        officers = new ArrayList<>();
    }

    public void addOfficer(Officer officer) {
        officers.add(officer);
    }

    public ArrayList<Officer> getOfficers() {
        return officers;
    }
}
