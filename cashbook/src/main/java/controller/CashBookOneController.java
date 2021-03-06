package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashbookDao;
import vo.Cashbook;


@WebServlet("/CashBookOneController")
public class CashBookOneController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int cashbookNo =Integer.parseInt(request.getParameter("cashbookNo"));
		
		System.out.println(cashbookNo +" <-- cashbookNo CashbookOneController");
		System.out.println("--------------------------------");
		// 로그인 확인 
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) { //로그인 안되어있으면  
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return; 
		}
		
		CashbookDao cashbookDao = new CashbookDao();
		List<Cashbook> list = cashbookDao.selectCashBookOne(cashbookNo);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/view/cashBookOne.jsp").forward(request,response);
	}

}
