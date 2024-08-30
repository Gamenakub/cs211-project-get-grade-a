package ku.cs.models.collections;

import ku.cs.models.users.officers.Officer;

import java.util.ArrayList;

public class OfficerList {
    protected ArrayList<Officer> officers;
    public OfficerList() {
        officers = new ArrayList<Officer>();
    }
    public void addOfficer(Officer officer) {
        officers.add(officer);
    }
    public void setOfficers(ArrayList<Officer> officers) { this.officers = officers; }
    public ArrayList<Officer> getOfficers() { return officers; }
    public Officer findOfficerByEmail(String officerEmail) {
        for(Officer officer : officers){
            if(officer.checkOfficerByEmail(officerEmail)) {
                return officer;
            }
        }
        return null;
    }
}
