package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;

@WebServlet("/updateCashBookController")
public class UpdateCashBookController extends HttpServlet {
	private CashbookDao cashbookDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.cashbookDao = new CashbookDao(); // cashbookDao 호출 
	int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo")); // cashbookOne에서 보낸 cashbookNo 정보 받아오기 
	System.out.println("UpdateCashbookDao cashbookNo : " + cashbookNo); //디버깅 
	
	List<Cashbook> list = cashbookDao.selectCashBookOne(cashbookNo);
	
	
	request.setAttribute("list",list);
	request.setAttribute("cashbookNo", cashbookNo);
	
	request.getRequestDispatcher("/WEB-INF/view/updateCashbook.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		System.out.println("updateCashobookController.doPost() cashbookNo :" + cashbookNo);
		String kind = request.getParameter("kind");
		System.out.println("updateCashbookController.doPost() kind : " + kind);
		int cash = Integer.parseInt(request.getParameter("cash"));
		System.out.println("updateCashbookController cash : " + cash);
		String memo = request.getParameter("memo");
		System.out.println("updateCashbookController memo : " + memo);
		
		
		response.sendRedirect(request.getContextPath() +"/cashbookOneController?cashbookNo=" + cashbookNo);
	}
}
