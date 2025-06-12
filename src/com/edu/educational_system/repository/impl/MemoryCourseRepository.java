package com.edu.educational_system.repository.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edu.educational_system.model.*;
import com.edu.educational_system.repository.CourseRepository;
import com.edu.educational_system.repository.CourseRepositoryException;
import com.edu.educational_system.repository.CourseRepositoryRuntimeException;

public class MemoryCourseRepository implements CourseRepository {

	private final File FILE_PATH = new File("course.txt");

	public MemoryCourseRepository() {
		try {
			if (!FILE_PATH.exists()) {
				FILE_PATH.createNewFile();
			}
		} catch (IOException e) {
			throw new CourseRepositoryRuntimeException("Failed to create course.txt file", e);
		}
	}
	

	public void addNewCourse(Course course) throws CourseRepositoryException {
	    List<Course> existingCourses = loadCoursesFromFile();

	    for (Course exCourse : existingCourses) {
	        if (exCourse.getName().equalsIgnoreCase(course.getName())) {
	            throw new CourseRepositoryException("Course with name '" + course.getName() + "' already exists");
	        }
	    }

	    existingCourses.add(course);
	    writeCoursesToFile(existingCourses);
	}

	public void updateCourse(Course course) throws CourseRepositoryException {
	    List<Course> existingCourses = loadCoursesFromFile();
	    boolean found = false;

	    for (int i = 0; i < existingCourses.size(); i++) {
	        if (existingCourses.get(i).getName().equalsIgnoreCase(course.getName())) {
	            existingCourses.set(i, course);
	            found = true;
	            break;
	        }
	    }

	    if (!found) {
	        throw new CourseRepositoryException("Course not found for update: " + course.getName());
	    }

	    writeCoursesToFile(existingCourses);
	}
	
	
	private void writeCoursesToFile(List<Course> courses) throws CourseRepositoryException {
	    try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH)))) {
	        for (Course course : courses) {
	            pw.println("Course;" + course.getName());

	            for (Person p : course.getParticipants()) {
	                pw.println(serializePerson(p));
	            }

	            for (Person p : course.getStaff()) {
	                pw.println(serializePerson(p));
	            }

	            pw.println("---");
	        }
	    } catch (IOException e) {
	        throw new CourseRepositoryException(e);
	    }
	}
	
	private String serializePerson(Person p) {
		if (p instanceof Student s) {
			return new StringBuilder("Student;").append(s.getName()).append(";").append(s.getEmail()).append(";")
					.append(s.getGroup()).append(";").append(s.getAverageGrade()).toString();
		} else if (p instanceof Administrator a) {
			return new StringBuilder("Administrator;").append(a.getName()).append(";").append(a.getEmail()).append(";")
					.append(a.getDepartment()).toString();
		} else if (p instanceof Teacher t) {
			return new StringBuilder("Teacher;").append(t.getName()).append(";").append(t.getEmail()).append(";")
					.append(t.getSubject()).toString();
		}
		return "";
	}

	
	public List<Course> loadCoursesFromFile() throws CourseRepositoryException {
		List<Course> result = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			Course course = null;
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.equals("---") || line.isBlank())
					continue;

				String[] parts = line.split(";");
				switch (parts[0]) {
				case "Course" -> {
					if (course != null)
						result.add(course);
					course = new Course(parts[1]);
				}
				case "Teacher", "Administrator" -> {
					if (course != null) {
						Person person = deserializePerson(parts);
						course.getStaff().add(person); 
					}
				}
				case "Student" -> {
					if (course != null) {
						Person person = deserializePerson(parts);
						course.getParticipants().add(person); 
					}
				}
				}
			}
			if (course != null)
				result.add(course);

		} catch (IOException e) {
			throw new CourseRepositoryException(e);
		}

		return result;
	}
	
	private Person deserializePerson(String[] parts) {
		return switch (parts[0]) {
		case "Student" -> new Student(parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
		case "Teacher" -> new Teacher(parts[1], parts[2], parts[3]);
		case "Administrator" -> new Administrator(parts[1], parts[2], parts[3]);
		default -> null;
		};
	}
	
	
	public List<Course> getAllCourses() throws CourseRepositoryException {
		return loadCoursesFromFile();
	}
	

	public void obfuscateStudentInCourse(String courseName, String studentEmail) throws CourseRepositoryException {
		List<String> originalLines = readAllLines();
		List<String> updatedLines = new ArrayList<>();
		boolean insideCourse = false;

		for (String line : originalLines) {
			if (line.startsWith("Course;")) {
				insideCourse = line.equalsIgnoreCase("Course;" + courseName);
			} else if (insideCourse && line.startsWith("Student;")) {
				String[] parts = line.split(";");
				if (parts.length >= 5 && parts[2].equalsIgnoreCase(studentEmail)) {
					line = "Student;***;***@***;" + parts[3] + ";" + parts[4];
				}
			}
			updatedLines.add(line);
		}

		writeAllLines(updatedLines);
	}

	private List<String> readAllLines() throws CourseRepositoryException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			throw new CourseRepositoryException(e);
		}
		return lines;
	}

	private void writeAllLines(List<String> lines) throws CourseRepositoryException {
		try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
			for (String line : lines) {
				writer.println(line);
			}
		} catch (IOException e) {
			throw new CourseRepositoryException(e);
		}
	}

	
	public Optional<Course> findCourseByName(String courseName) throws CourseRepositoryException {
		List<Course> allCourses = loadCoursesFromFile();
		for (Course c : allCourses) {
			if (c.getName().equalsIgnoreCase(courseName)) {
				return Optional.of(c);
			}
		}
		return Optional.empty();
	}

}
