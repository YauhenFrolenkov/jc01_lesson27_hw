package com.edu.educational_system.repository.impl;

import java.util.List;
import java.util.Optional;
import com.edu.educational_system.model.Course;
import com.edu.educational_system.repository.CourseRepository;
import com.edu.educational_system.repository.CourseRepositoryException;

public class FileCourseRepository implements CourseRepository {

	@Override
	public List<Course> getAllCourses() throws CourseRepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void obfuscateStudentInCourse(String courseName, String studentEmail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Course> findCourseByName(String courseName) throws CourseRepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewCourse(Course course) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCourse(Course course) {
		// TODO Auto-generated method stub
		
	}
	

}
