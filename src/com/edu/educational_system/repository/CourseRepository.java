package com.edu.educational_system.repository;

import java.util.List;
import java.util.Optional;

import com.edu.educational_system.model.Course;

public interface CourseRepository {
	void addNewCourse(Course course) throws CourseRepositoryException;
	void updateCourse(Course course) throws CourseRepositoryException;
	List<Course> getAllCourses() throws CourseRepositoryException;
	void obfuscateStudentInCourse(String courseName, String studentEmail) throws CourseRepositoryException;
	Optional<Course> findCourseByName(String courseName) throws CourseRepositoryException;
	
}
