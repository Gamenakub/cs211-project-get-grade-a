package ku.cs.models.collections;

import ku.cs.models.Course;

import java.util.ArrayList;

public class CourseList {
    private final ArrayList<Course> courses;

    public CourseList() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        if (!courses.contains(course) && course != null) {
            courses.add(course);
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public CourseList getCourseByRelatedRequestFormId(String relatedRequestFormId) {
        CourseList relatedCourses = new CourseList();
        for (Course course : this.courses) {
            if (course.getRelatedRequestFormId().equals(relatedRequestFormId)) {
                relatedCourses.addCourse(course);
            }
        }
        return relatedCourses;
    }

    public int getCourseListSize() {
        return courses.size();
    }

    public void setAllCourseRequestFormId(String requestId) {
        for (Course course : courses) {
            course.setRelatedRequestFormId(requestId);
        }
    }
}
