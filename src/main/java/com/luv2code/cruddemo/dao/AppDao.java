package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import com.luv2code.cruddemo.entity.Student;

import java.util.List;

public interface AppDao {
    void save(Instructor theInstructor);

    Instructor findInstructorById(int theId);

    void deleteInstructorById(int theId);

    InstructorDetail findInstructorDetailById(int theId);

    void deleteInstructorDetailById(int theId);

    List<Course> findCoursesByInstructorId(int theId);

    Instructor findInstructorByIdJoinFetch(int theId);

    void updateInstructor(Instructor tempInstructor);

    void updateCourse(Course tempCourse);

    Course findCoursesById(int TheId);

    void deleteCourseById(int TheId);

    void save(Course theCourse);

   Course findCourseAndReviewsByCourseId(int theId);
   Course findCourseAndStudentByCourseId(int theId);
   Student findStudentAndCoursesByStudentId(int theId);
   void update(Student student);
    void deleteStudent(int TheId);
}
