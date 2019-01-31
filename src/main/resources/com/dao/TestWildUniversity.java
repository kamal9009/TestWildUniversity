package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.bean.Student;
import com.dao.StudentDAOImpl;
import com.exceptions.DuplicateIdException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestWildUniversity extends TestCase {
	
	
	public void testStudentRegistration() {
		
		StudentDAO dao = new StudentDAOImpl();
		
		try {
			dao.registerStudent(new Student("John","Male", 75));
		} catch (DuplicateIdException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		DBConnection db = new DBConnection();
		Connection conn = db.getConnection();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from Student where Uno = 75";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println(rs.getInt("Uno") + " " + rs.getString("Name") + " " + rs.getString("Gender"));
				Assert.assertEquals(75, rs.getInt("Uno"));
				Assert.assertEquals("John", rs.getString("Name"));
				Assert.assertEquals("Male", rs.getString("Gender"));
			}
			
			
			
			conn.close();
		}catch (SQLException e) {
			
		}

		
	}
	
}
