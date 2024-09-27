package ku.cs.models.collections;

import ku.cs.models.users.Student;

import java.util.ArrayList;
import java.util.Comparator;

public class StudentList implements Searchable<Student>{
    private ArrayList<Student> students;
    public StudentList() {
        students = new ArrayList<>();
    }
    public void addStudent(Student student) { students.add(student); }
    public void setStudents(ArrayList<Student> students) { this.students = students; }
    public ArrayList<Student> getStudents() { return students; }
    public Student findStudentById(String studentId) {
        for(Student student : students){
            if(student.checkStudentById(studentId)) {
                return student;
            }
        }
        return null;
    }
    public Student findStudentByName(String studentName) {
        for(Student student : students){
            if(student.checkStudentByName(studentName)){
                return student;
            }
        }
        return null;
    }
    // sort
    public void sortStudents(Comparator<Student> comparator) {
        students.sort(comparator);
    }



    @Override
    public ArrayList<Student> filter(String term) {
        ArrayList<Student> targetForms = new ArrayList<>();
        for (Student requestForm : this.students) {
            if((requestForm.getStatus()+"").equals(term)){
                targetForms.add(requestForm);
            }
        }
        return targetForms;
    }

    @Override
    public ArrayList<Student> search(String keyword){
        ArrayList<Student> targetStudent = new ArrayList<>();
        for (Student student : this.students) {
            if(student.getName().contains(keyword)){
                targetStudent.add(student);
            }
            else if(student.getStudentId().contains(keyword)){
                targetStudent.add(student);
            }
            else if((student.getSurname()).contains(keyword)){
                targetStudent.add(student);
            }
        }
        return  targetStudent;
    }
}