package ku.cs.models.collections;

import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import java.util.ArrayList;

public class RequestFormList implements Searchable<RequestForm> {
    private ArrayList<RequestForm> requestForms;

    public RequestFormList() {
        requestForms = new ArrayList<>();
    }

    public void addRequestForm(RequestForm requestForm) {
        requestForms.add(requestForm);
    }

    public ArrayList<RequestForm> getRequestForms() {
        return requestForms;
    }

    public void setRequestForms(ArrayList<RequestForm> requestForms) {
        this.requestForms = requestForms;
    }

    public void applyRequestFormApprovingHistoryList(RequestFormActionHistoryList requestFormActionHistoryList) {
        for (RequestForm requestForm : requestForms) {
            RequestFormActionHistoryList targetFormHistoryList = requestFormActionHistoryList.getRelatedToRequestFormApprovingHistoryList(requestForm.getRequestFormId());
            requestForm.setRequestFormApprovingHistoryList(targetFormHistoryList);
        }
    }

    public RequestFormList findRequestFormsByDepartment(Department department) {
        ArrayList<RequestForm> targetRequestForms = new ArrayList<>();
        for (RequestForm requestForm : requestForms) {
            if (requestForm.getStudent().getDepartment().getId().equals(department.getId())) {
                targetRequestForms.add(requestForm);
            }
        }
        RequestFormList requestFormList = new RequestFormList();
        requestFormList.setRequestForms(targetRequestForms);
        return requestFormList;
    }

    public void removeForm(RequestForm targetRequestForm) {
        requestForms.remove(targetRequestForm);
    }

    public RequestFormList findRequestFormsByStatus(RequestForm.Status status) {
        ArrayList<RequestForm> targetRequestForms = new ArrayList<>();
        for (RequestForm requestForm : requestForms) {
            if (requestForm.getStatus() == status) {
                targetRequestForms.add(requestForm);
            }
        }
        RequestFormList requestFormList = new RequestFormList();
        requestFormList.setRequestForms(targetRequestForms);
        return requestFormList;
    }

    public RequestFormList findRequestFormsByFaculty(Faculty faculty) {
        ArrayList<RequestForm> targetRequestForms = new ArrayList<>();
        for (RequestForm requestForm : requestForms) {
            if (requestForm.getStudent().getFaculty().equals(faculty)) {
                targetRequestForms.add(requestForm);
            }
        }
        RequestFormList requestFormList = new RequestFormList();
        requestFormList.setRequestForms(targetRequestForms);
        return requestFormList;
    }

    public RequestFormList findRequestFormsByAdvisor(Advisor advisor) {
        ArrayList<RequestForm> targetRequestForms = new ArrayList<>();
        for (RequestForm requestForm : requestForms) {
            if (requestForm.getAdvisor().equals(advisor)) {
                targetRequestForms.add(requestForm);
            }
        }
        RequestFormList requestFormList = new RequestFormList();
        requestFormList.setRequestForms(targetRequestForms);
        return requestFormList;
    }

    public RequestFormList findRequestFormsByStudent(Student student) {
        ArrayList<RequestForm> targetRequestForms = new ArrayList<>();
        for (RequestForm requestForm : requestForms) {
            if (requestForm.getStudent().equals(student)) {
                targetRequestForms.add(requestForm);
            }
        }
        RequestFormList requestFormList = new RequestFormList();
        requestFormList.setRequestForms(targetRequestForms);
        return requestFormList;
    }

    public void addRequestFormList(RequestFormList requestFormList) {
        requestForms.addAll(requestFormList.getRequestForms());
    }

    @Override
    public ArrayList<RequestForm> search(String keyword) {
        ArrayList<RequestForm> targetForms = new ArrayList<>();
        for (RequestForm requestForm : this.requestForms) {
            if (requestForm.getRequestFormId().contains(keyword)) {
                targetForms.add(requestForm);
            } else if (requestForm.getRequestFormTitle().contains(keyword)) {
                targetForms.add(requestForm);
            } else if ((requestForm.getStatus() + "").contains(keyword)) {
                targetForms.add(requestForm);
            }
        }
        return targetForms;
    }

    @Override
    public ArrayList<RequestForm> filter(String keyword) {
        ArrayList<RequestForm> targetForms = new ArrayList<>();
        for (RequestForm requestForm : this.requestForms) {
            if ((requestForm.getStatus() + "").contains(keyword)) {
                targetForms.add(requestForm);
            }
        }
        return targetForms;
    }


}
