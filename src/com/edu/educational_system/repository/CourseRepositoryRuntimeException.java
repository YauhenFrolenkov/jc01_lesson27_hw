package com.edu.educational_system.repository;

public class CourseRepositoryRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 3298437906469246074L;
	
	public CourseRepositoryRuntimeException(){
		super();
	}
	
	public CourseRepositoryRuntimeException(String message){
		super(message);
	}
	
	public CourseRepositoryRuntimeException(Exception e){
		super(e);
	}
	
	public CourseRepositoryRuntimeException(String message, Exception e){
		super(message, e);
	}


}
