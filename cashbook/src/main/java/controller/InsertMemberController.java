package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.MemberDao;


@WebServlet("/InsertMemberController")
public class InsertMemberController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/view/insertCashBookMember.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		System.out.println(memberId + " <-- memberId InsertMemberController doPost()");
		System.out.println(memberPw + " <-- memberPW InsertMemberController doPost()");
		
		MemberDao memberDao = new MemberDao(); //member dao호출
		memberDao.insertMemberByIdPw(memberId, memberPw);
		response.sendRedirect(request.getContextPath() + "/LoginController");
		
	}

}
