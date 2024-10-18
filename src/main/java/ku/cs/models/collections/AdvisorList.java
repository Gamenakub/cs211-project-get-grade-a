package ku.cs.models.collections;

import ku.cs.models.Department;
import ku.cs.models.users.Advisor;

import java.util.ArrayList;

public class AdvisorList implements Searchable<Advisor> {
    private final ArrayList<Advisor> advisors;

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

    public Advisor findAdvisorById(String id) {
        for (Advisor advisor : advisors) {
            if (advisor.checkAdvisorById(id)) {
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
    public ArrayList<Advisor> search(String keyword) {
        ArrayList<Advisor> targetAdvisors = new ArrayList<>();
        for (Advisor advisor : advisors) {
            if (advisor.getFullName().contains(keyword)) {
                targetAdvisors.add(advisor);
            } else if (advisor.getUsername().contains(keyword)) {
                targetAdvisors.add(advisor);
            } else if (advisor.getAdvisorId().contains(keyword)) {
                targetAdvisors.add(advisor);
            }
        }
        return targetAdvisors;
    }
}
