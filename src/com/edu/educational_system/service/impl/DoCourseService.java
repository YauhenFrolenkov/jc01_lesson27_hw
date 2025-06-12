package com.edu.educational_system.service.impl;

import java.util.List;
import java.util.Optional;

import com.edu.educational_system.model.Course;
import com.edu.educational_system.model.Person;
import com.edu.educational_system.repository.CourseRepository;
import com.edu.educational_system.repository.CourseRepositoryException;
import com.edu.educational_system.repository.CourseRepositoryProvider;
import com.edu.educational_system.service.CourseService;
import com.edu.educational_system.service.CourseServiceExeption;

public class DoCourseService implements CourseService {
	private final CourseRepository courseRepository;

	{
		CourseRepositoryProvider provider = CourseRepositoryProvider.getInstance();
		courseRepository = provider.getCourseRepository();
	}

	public DoCourseService() {
	}

	public void createCourse(Course course) throws CourseServiceExeption {
		try {
			courseRepository.addNewCourse(course);
		} catch (CourseRepositoryException e) {
			throw new CourseServiceExeption(e);
		}
	}

	public boolean enrollPerson(Course course, Person person) throws CourseServiceExeption {
		boolean success = course.addParticipant(person);
		if (success) {
			try {
				courseRepository.updateCourse(course);
			} catch (CourseRepositoryException e) {
				throw new CourseServiceExeption(e);
			}
		}
		return success;
	}

	public void conductLesson(Course course) {
		course.conductLesson();
	}

	public List<Person> getParticipants(String courseName) throws CourseServiceExeption {
		Optional<Course> course = findCourseByName(courseName);
		if (course.isPresent()) {
	        return course.get().getParticipants();
	    } else {
	        throw new CourseServiceExeption("Course with the specified name '" + courseName + "' not found.");
	    }
	}

	public List<Person> getStaff(String courseName) throws CourseServiceExeption {
	    Optional<Course> course = findCourseByName(courseName);
	    if (course.isPresent()) {
	        return course.get().getStaff();
	    } else {
	        throw new CourseServiceExeption("Course with the specified name '" + courseName + "' not found.");
	    }
	}

	public double getAverageGrade(String courseName) throws CourseServiceExeption  {
		Optional<Course> course = findCourseByName(courseName);
	    if (course.isPresent()) {
	        return course.get().calculateAverageGrade();
	    } else {
	        throw new CourseServiceExeption("Course with the specified name '" + courseName + "' not found.");
	    }
	}

	public List<Course> getAllCourses() throws CourseServiceExeption {
		try {
			return courseRepository.getAllCourses();
		} catch (CourseRepositoryException e) {
			throw new CourseServiceExeption(e);
		}
	}

	public void obfuscateData(String nameOfCourse, String email) throws CourseServiceExeption {
		try {
			courseRepository.obfuscateStudentInCourse(nameOfCourse, email);
		} catch (CourseRepositoryException e) {
			throw new CourseServiceExeption(e);
		}
	}

	public Optional<Course> findCourseByName(String name) throws CourseServiceExeption {
		try {
			return courseRepository.findCourseByName(name);
		} catch (CourseRepositoryException e) {
			throw new CourseServiceExeption(e);
		}
	}
	

}
