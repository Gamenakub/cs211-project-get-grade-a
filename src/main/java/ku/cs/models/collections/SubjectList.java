package ku.cs.models.collections;

import ku.cs.models.Subject;

import java.util.ArrayList;

public class SubjectList {
    private final ArrayList<Subject> subjects;
    public SubjectList() {
        subjects = new ArrayList<>();
    }
    public void addSubject(Subject subject) {
        if(!subjects.contains(subject)&& subject != null) {
            subjects.add(subject);
        }
    }
    public void removeSubject(Subject subject) { subjects.remove(subject); }
    public ArrayList<Subject> getSubjects() { return subjects; }
    public int getIndexOfSubject(Subject subject) { return subjects.indexOf(subject); }
    public Subject findSubjectByID(String subjectId) {
        for(Subject subject : subjects) {
            if(subject.checkSubjectById(subjectId)){
                return subject;
            }
        }
        return null;
    }
}
