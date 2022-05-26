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


@WebServlet("/TagSameController")
public class TagSameController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tag = request.getParameter("tag");
			
		System.out.println(tag +" <--tag TagSameController.doGet()");
		
		HashtagDao hashtagDao = new HashtagDao();
		List<Map<String, Object>> list = hashtagDao.selectSameTagRankList(tag);
		
		request.setAttribute("list", list);
		request.setAttribute("tag", tag);
		request.getRequestDispatcher("WEB-INF/view/tagSameList.jsp").forward(request, response);
	}

}
