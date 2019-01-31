package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Student;
import com.dao.StudentDAO;
import com.dao.StudentDAOImpl;
import com.exceptions.DuplicateIdException;

/**
 * Servlet implementation class UniversityController
 */
@WebServlet("/controller")
public class UniversityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	
    	RequestDispatcher view= null;
    	StudentDAO dao = new StudentDAOImpl();
    	
    	String action = request.getParameter("action");
    	
    	if(action.equals("register")) {
    		
    		view = request.getRequestDispatcher("registration.jsp");
    		view.forward(request, response);
    		
    	}else if(action.equals("list")) {    		 	

    		///// get the students 
    		
    		List<Student> students = null;
			try {
				students = dao.getRegisteredStudents();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    		ServletContext context = getServletContext(); //get the servlet context
    		context.setAttribute("studentlist",students); //store student list in servlet context
    													  //(this data can be accessed in any servlet/jsp of the that application)
    		
    		view = request.getRequestDispatcher("list.jsp");
    		view.forward(request, response);
    	
    	} else if(action.equals("registernewstudent")) {
    		//code to register new student
    		
    		int uno = Integer.parseInt(request.getParameter("uno"));
    		String gender = request.getParameter("gender");
    		String name = request.getParameter("name");
    		
    		Student stud = new Student(name, gender, uno);
    		
    		try {
				dao.registerStudent(stud);
				response.sendRedirect("homepage.jsp");
			} catch (DuplicateIdException e) {
				
				//ServletContext context = getServletContext();
				//response.sendRedirect("registration.jsp");
				
				request.setAttribute("DuplicateError", "Duplicate entry for University number");
				view = request.getRequestDispatcher("registration.jsp");
	    		view.forward(request, response);
			}
    		
    		
    		    		
    	}
    	
    	
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
