package ku.cs.models.collections;

import ku.cs.models.Department;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.User;

import java.util.ArrayList;

public class AdvisorList implements Searchable<Advisor> {
    private ArrayList<Advisor> advisors;

    public AdvisorList(ArrayList<Advisor> advisors) {
        this.advisors = advisors;
    }

    public AdvisorList() {
        this.advisors = new ArrayList<>();
    }

    public ArrayList<Advisor> getAdvisors() {
        return advisors;
    }

    public void addAdvisor(Advisor advisor) {
        advisors.add(advisor);
    }

    public Advisor findAdvisorByName(String name) {
        for (Advisor advisor : advisors) {
            if (advisor.getName().equals(name)) {
                return advisor;
            }
        }
        return null;
    }

    public Advisor findAdvisorById(String id) {
        for (Advisor advisor : advisors) {
            if(advisor.checkAdvisorById(id)){
                return advisor;
            }
        }
        return null;
    }

    public AdvisorList findAdvisorByDepartment(Department department) {
        ArrayList<Advisor> advisorList = new ArrayList<>();
        for (Advisor advisor : advisors) {
            if (advisor.getDepartment().isId(department.getId())) {
                advisorList.add(advisor);
            }
        }
        return new AdvisorList(advisorList);
    }

    @Override
    public ArrayList<Advisor> search(String term) {
        ArrayList<Advisor> targetAdvisors = new ArrayList<>();
        for(Advisor advisor : advisors){
            String name = advisor.getNameTitle() + advisor.getName() + " " + advisor.getSurname();
            String id = advisor.getAdvisorId();
            if(name.contains(term)){
                targetAdvisors.add(advisor);
            } else if (id.contains(term)){
                targetAdvisors.add(advisor);
            }
        }
        return targetAdvisors;
    }
}
