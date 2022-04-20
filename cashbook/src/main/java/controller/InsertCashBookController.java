package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;


@WebServlet("/InsertCashBookController")
public class InsertCashBookController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String y = request.getParameter("y");
		String m = request.getParameter("m");
		String d = request.getParameter("d");
		String cashDate = y+"-"+m+"-"+d;
		request.setAttribute("cashDate", cashDate);
		request.getRequestDispatcher("/WEB-INF/view/insertCashBookForm.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		int cash = Integer.parseInt(request.getParameter("cash"));
		String memo = request.getParameter("memo");
		
		System.out.println(cashDate + " <--cashDate InsertCashBookController.doPost()");
		System.out.println(kind + " <--kind InsertCashBookController.doPost()");
		System.out.println(cash + " <--cash InsertCashBookController.doPost()");
		System.out.println(memo + " <--memo InsertCashBookController.doPost()");
		
		Cashbook cashbook = new Cashbook();
		cashbook.setCashDate(cashDate);
		cashbook.setKind(kind);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		
		
		List<String> hashtag = new ArrayList<>();
		String memo2 = memo.replace("#", " #");
		String[] arr = memo2.split(" ");
		for(String s : arr) {
			if(s.startsWith("#")) {
				String temp = s.replace("#", "");
				if(temp.length()>0) {
					hashtag.add(temp);
				}
			}
		}
		System.out.println(hashtag.size() + " <--hashtag.size InsertCashBookController.doPost()");
		for(String h : hashtag) {
			System.out.println(h + " <-- hashtag InsertCashBookController.doPost()");
		}
		
		CashbookDao cashbookDao = new CashbookDao();
		cashbookDao.insertCashbook(cashbook, hashtag);
		
		response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController");
	}
}
