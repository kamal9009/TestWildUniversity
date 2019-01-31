package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bean.Student;
import com.exceptions.DuplicateIdException;
//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class StudentDAOImpl implements StudentDAO {

	DBConnection db = new DBConnection();
	Connection conn = null;

	@Override
	public void registerStudent(Student stud) throws DuplicateIdException {

		conn = db.getConnection();
		System.out.println(conn);

		// STEP 4: Execute a query
		System.out.println("Inserting records into the table...");
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "INSERT INTO Student VALUES(" + stud.getUniversity_number() + ",'" + stud.getName() + "','"
					+ stud.getGender() + "')";
			stmt.execute(sql);
			conn.close();
		} catch (SQLException e) {
			//if(e instanceof MySQLIntegrityConstraintViolationException) {
				//log this root cause
				System.out.println(e.getMessage());
				throw new DuplicateIdException(e.getMessage());
			//}
		}
	}

	@Override
	public List<Student> getRegisteredStudents() throws SQLException {

		List<Student> students = new ArrayList<>();

		conn = db.getConnection();
		System.out.println(conn);

		// STEP 4: Execute a query
		System.out.println("Creating statement...");

		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM Student";
		ResultSet rs = stmt.executeQuery(sql);

		// STEP 5: Extract data from result set
		while (rs.next()) {
			// Retrieve by column name
			int uno = rs.getInt("student_id");
			String name = rs.getString("student_name");
			String gender = (String) rs.getObject("gender");

			Student s = new Student(name, gender, uno);

			students.add(s);

			// Display values
			System.out.print("Uno: " + uno);
			System.out.print(", Name: " + name);
			System.out.print(", Gender: " + gender);

		}

		conn.close();

		return students;
	}

}
