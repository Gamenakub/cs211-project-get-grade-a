package ku.cs.models.collections;

import ku.cs.models.users.Student;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class StudentList implements Searchable<Student> {
    private ArrayList<Student> students;

    public StudentList() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.checkStudentById(studentId)) {
                return student;
            }
        }
        throw new NoSuchElementException("Student with ID " + studentId + " not found.");
    }

    @Override
    public ArrayList<Student> filter(String keyword) {
        ArrayList<Student> targetForms = new ArrayList<>();
        for (Student requestForm : this.students) {
            if ((requestForm.getStatus() + "").equals(keyword)) {
                targetForms.add(requestForm);
            }
        }
        return targetForms;
    }

    @Override
    public ArrayList<Student> search(String keyword) {
        ArrayList<Student> targetStudent = new ArrayList<>();
        for (Student student : this.students) {
            if (student.getName().contains(keyword)) {
                targetStudent.add(student);
            } else if (student.getStudentId().contains(keyword)) {
                targetStudent.add(student);
            } else if ((student.getSurname()).contains(keyword)) {
                targetStudent.add(student);
            }
        }
        return targetStudent;
    }
}