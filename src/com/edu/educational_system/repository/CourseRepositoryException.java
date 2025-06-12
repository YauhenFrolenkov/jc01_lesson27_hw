package com.edu.educational_system.repository;

public class CourseRepositoryException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public CourseRepositoryException(){
		super();
	}
	
	public CourseRepositoryException(String message){
		super(message);
	}
	
	public CourseRepositoryException(Exception e){
		super(e);
	}
	
	public CourseRepositoryException(String message, Exception e){
		super(message, e);
	}

}
