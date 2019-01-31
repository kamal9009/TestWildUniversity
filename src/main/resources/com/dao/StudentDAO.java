package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.bean.Student;
import com.exceptions.DuplicateIdException;

public interface StudentDAO {

	void registerStudent(Student stud) throws DuplicateIdException;
	List<Student> getRegisteredStudents() throws SQLException;
}
