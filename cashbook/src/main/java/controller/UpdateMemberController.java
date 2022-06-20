package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;


@WebServlet("/UpdateMemberController")
public class UpdateMemberController extends HttpServlet {
	private MemberDao memberDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.memberDao = new MemberDao();
		// 로그인
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		
		if(sessionMemberId == null) { // sessionMemberId 값이 null 이면 
			response.sendRedirect(request.getContextPath()+"/LoginController"); // loginController 로 돌아감 
		
			return;
		}
		List<Member> list = memberDao.selectMemberOne(sessionMemberId);		
			
		request.setAttribute("list", list);
	request.getRequestDispatcher("/WEB-INF/view/updateCashBookMember.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String sessionMemberId = request.getParameter("sessionMemberId");
		 String memberPw = request.getParameter("memberPw");
		 
		 System.out.println(sessionMemberId + " <--UpdateMemberController.doPost()");
		 System.out.println(memberPw + " <-- UpdateMemberController.doPost()");
		 
		 
		MemberDao memberDao = new MemberDao();
		
	}

}
