package ku.cs.services.datasource;

import ku.cs.models.Course;
import ku.cs.models.collections.CourseList;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;


public class CourseDataSource implements Writable<CourseList, Course>, Readable<CourseList, Course> {

    @Override
    public String getFileName() {
        return "course.csv";
    }

    @Override
    public String getDirectory() {
        return "data/";
    }

    @Override
    public HashMap<String, String> modelToHashMap(Course course) {
        HashMap<String, String> map = new HashMap<>();
        map.put("courseName", course.getCourseName());
        map.put("courseId", course.getCourseId());
        map.put("courseYear", course.getCourseYear());
        map.put("courseType", course.getCourseType());
        map.put("lectureSection", course.getLectureSection());
        map.put("practiceSection", course.getPracticeSection());
        map.put("lectureCredit", course.getLectureCredit());
        map.put("practiceCredit", course.getPracticeCredit());
        map.put("professorName", course.getProfessorName());
        map.put("relatedRequestFormId", course.getRelatedRequestFormId());
        return map;
    }

    @Override
    public Course hashMapToModel(HashMap<String, String> row) {

        return new Course(
                row.get("courseName"),
                row.get("courseId"),
                row.get("courseYear"),
                row.get("courseType"),
                row.get("lectureSection"),
                row.get("practiceSection"),
                row.get("lectureCredit"),
                row.get("practiceCredit"),
                row.get("professorName"),
                row.get("relatedRequestFormId")
        );
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("courseName");
        header.add("courseId");
        header.add("courseYear");
        header.add("courseType");
        header.add("lectureSection");
        header.add("practiceSection");
        header.add("lectureCredit");
        header.add("practiceCredit");
        header.add("professorName");
        header.add("relatedRequestFormId");
        return header;
    }

    @Override
    public ArrayList<Course> getCollectionArrayList(CourseList courseList) {
        return new ArrayList<>(courseList.getCourses());
    }

    @Override
    public CourseList collectionInitializer() {
        return new CourseList();
    }

    @Override
    public void addModelToList(CourseList courseList, Course course) {
        courseList.addCourse(course);
    }
}
