package service;

import Model.Course;
import Model.Users;

public interface ICourseService extends service<Course> {

    void registCourse(String courseID, Users users);

}
