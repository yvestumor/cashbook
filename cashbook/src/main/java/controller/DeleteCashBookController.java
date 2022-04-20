package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;


@WebServlet("/DeleteCashBookController")
public class DeleteCashBookController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cashbookNo =Integer.parseInt(request.getParameter("cashbookNo")); //삭제할 번호 받기
		
		System.out.println(cashbookNo +" <-- cashbookNo DeleteCashBookController"); //디버깅
		
		
		CashbookDao cashbookDao = new CashbookDao();
		cashbookDao.deleteCashBook(cashbookNo);
		
		response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController"); 
		
	
	}

}
