package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import service.LoginService;
import service.RegService;

/**
 * Servlet implementation class RegServlet
 */
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RegServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String nextPath = "";
		if ("registration".equalsIgnoreCase(action)) {
			System.out.println("A");
			RegService rs = new RegService();
			boolean isSuccess = rs.insertValues(request);

			if (isSuccess == false){
				request.setAttribute(
						"message",
						" This might happen because of the below errors <br>1.Duplicate Email-Id <br>2.Unable to connect Database<br>3.Unable to connect Internet");
			nextPath = "/Registration.jsp";
			}
			else {
				request.setAttribute("message",
						"Account created successfully<br> Please visit your "
								+ request.getParameter("E-mail Id")
								+ " for the credential");
				nextPath = "/Registration.jsp";
			}
		} else if ("login".equalsIgnoreCase(action)) {
			LoginService ls = new LoginService();
			String username = ls.checkCredintials(request);
			
			System.out.println(username);
			if (username == null)
			{
				nextPath = "/Login.jsp";
				request.setAttribute("username", "invalid");
			}else{
				nextPath = "/GetChart";
			}
			
		}
	
		else if("forgetpassword".equals(action))
		{
			LoginService ls= new LoginService();
			boolean result=ls.forgetPassword(request);
			request.setAttribute("msg", result);
			nextPath="/ForgetPassword.jsp";	
		}
		RequestDispatcher rd = request
				.getRequestDispatcher(nextPath);
		rd.forward(request, response);
	}

}
