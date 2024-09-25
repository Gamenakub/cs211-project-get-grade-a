package ku.cs.models.collections;

import ku.cs.models.Course;

import java.util.ArrayList;

public class CourseList {
    private final ArrayList<Course> courses;
    public CourseList() {
        courses = new ArrayList<>();
    }
    public void addCourse(Course course) {
        if(!courses.contains(course)&& course != null) {
            courses.add(course);
        }
    }
    public void removeCourse(Course course) { courses.remove(course); }
    public ArrayList<Course> getCourses() { return courses; }
    public int getIndexOfCourse(Course course) { return courses.indexOf(course); }
    public Course findCourseByID(String subjectId) {
        for(Course course : courses) {
            if(course.checkSubjectById(subjectId)){
                return course;
            }
        }
        return null;
    }
    public CourseList getCourseByRelatedRequestFormId(String relatedRequestFormId) {
        CourseList relatedCourses = new CourseList();
        for(Course course : this.courses) {
            if(course.getRelatedRequestFormId().equals(relatedRequestFormId)) {
                relatedCourses.addCourse(course);
            }
        }
        return relatedCourses;
    }

    public int getCourseListSize() {
        return courses.size();
    }
}
