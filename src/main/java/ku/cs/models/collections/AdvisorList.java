package ku.cs.models.collections;

import ku.cs.models.users.Advisor;

import java.util.ArrayList;

public class AdvisorList {
    private ArrayList<Advisor> advisors;

    public AdvisorList(ArrayList<Advisor> advisors) {
        this.advisors = advisors;
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
}
