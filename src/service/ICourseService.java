package service;

import Model.Course;
import Model.Users;

public interface ICourseService extends Service<Course> {

    void registerCourse(Users user, Course course);
}
