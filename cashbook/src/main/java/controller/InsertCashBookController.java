package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/InsertCashBookController")
public class InsertCashBookController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/InsertCashBookController.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		// year
		int y = 0;
		
		if(request.getParameter("y") != null) {
			y = Integer.parseInt(request.getParameter("y"));
		}
		// month
		int m = 0;
		
		if(request.getParameter("m") != null) {
			m = Integer.parseInt(request.getParameter("m"));
		}
		// day
		int d = 0;
		
		if(request.getParameter("d") != null) {
			d = Integer.parseInt(request.getParameter("d"));
		}
		// cash
		int cash = 0;
		
		if(request.getParameter("cash") != null) {
			cash = Integer.parseInt(request.getParameter("cash"));
		}
		// memo
		String memo = null;
		
		if(request.getParameter("memo") != null) {
			memo = request.getParameter("memo");
		}
	}

}
