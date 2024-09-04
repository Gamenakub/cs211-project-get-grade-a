package ku.cs.models.collections;

import ku.cs.models.users.Student;

import java.util.ArrayList;

public class StudentList {
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
}