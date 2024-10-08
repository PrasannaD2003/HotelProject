package com.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upswd=request.getParameter("pass");
		String umob=request.getParameter("contact");
		RequestDispatcher dispatcher=null;
		Connection con=null;
		
		try {
			String url ="jdbc:mysql://localhost:3306/yogi?useSSL=false";
			String username = "root";
			String password = "root";
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,username,password);
			PreparedStatement pst = con.prepareStatement("insert into user(uname,upswd,uemail,umob) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, upswd);
			pst.setString(3, uemail);
			pst.setString(4, umob);
			
			int rowCount = pst.executeUpdate();
			dispatcher= request.getRequestDispatcher("Registration.jsp");
			
			if(rowCount>0) {
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
