package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HashtagDao;


@WebServlet("/TagSearchController")
public class TagSearchController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request,response);
			request.setCharacterEncoding("utf-8");
			
			String kind = request.getParameter("kind");
			String aDate = request.getParameter("aDate");
			String bDate = request.getParameter("bDate");
			
			System.out.println(kind + "<-- kind TagSearchcontroller");
			System.out.println(aDate + "<-- aDate TagSearchcontroller");
			System.out.println(bDate + "<-- bDate TagSearchcontroller");
			
			HashtagDao hashtagDao = new HashtagDao();
			List<Map<String, Object>> list = hashtagDao.selectTagRankSearchList(kind, aDate, bDate);
			request.setAttribute("list", list);
			request.setAttribute("kind", kind);
			request.setAttribute("aDate",aDate);
			request.setAttribute("bDate", bDate);
			request.getRequestDispatcher("WEB-INF/view/tagSearhRankList.jsp").forward(request, response);
			
		}

}
