package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;


@WebServlet("/CashBookListByMonthController")
public class CashBookListByMonthController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 월별 가계부 리스트 요청 분석
		Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		int m = now.get(Calendar.MONTH) + 1; // 0월 ~11월 로 되어있기 떄문에  1을 더해야함	
			
		if(request.getParameter("y") != null) {
			y = Integer.parseInt(request.getParameter("y"));
		}
		if(request.getParameter("m") != null) {
			m = Integer.parseInt(request.getParameter("m"));
		}
		if(m==0) { // month가 0이되면 
			m = 12; // 12월로바꾸고
			y = y-1; //전년도로 바꿈
		}
		if(m==13) { // month값이 13이되면
			m = 1; // 1월로 바꾸고
			y = y+1; // 년도에 1을더함
		}
		
		System.out.println(y+ " <-- y CashBookListByMonthController.java");
		System.out.println(m + " <-- m CashBookListByMonthController.java");
		System.out.println("----------------------------------------");
		// 2) 모델값(월별 가계부 리스트)을 반환하는 비지니스로직(모델) 호출
		

		// 시작시 필요한 공백<TD>
		 // firstDay는 오늘날짜를 먼저구하고 ,오늘 날짜를 1일로 변경
		Calendar firstDay = Calendar.getInstance(); // ex) 2022.04.19
		firstDay.set(Calendar.YEAR, y);
		firstDay.set(Calendar.MONTH, m-1);
		firstDay.set(Calendar.DATE, 1); // ex) 2022.04.01
		int dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);
		// dayOfWeek -> 일 1,월 2 ,화 3... 토 7
		// startBlank -> 일 0, 월 1,...토 6
		int startBlank = dayOfWeek - 1;
		// 마지막 날짜는 자바 달력 api 이용해서 구하기
		// 2)
		int endDay = firstDay.getActualMaximum(Calendar.DATE); // firstDay달의 제일 큰 숫자 -> 마지막 날짜
		// startBlank와 endDay를 합의 결과에 endBlank를 더해서 7의 배수가 되도록함
		int endBlank = 0; //마지막빈칸
		if ((startBlank+endDay)%7 != 0) { //시작일+끝날 나머지가 0이아니면 
			endBlank = 7-((startBlank+endDay)%7); 
		}
		// 4)
		int totalTd = startBlank + endDay + endBlank; //전체 칸의 개수
		
		CashbookDao cashbookDao = new CashbookDao();
		List<Map<String, Object>> list = cashbookDao.selectCashbookListByMonth(y, m);
		/*
		 달력 풀력에 필요한 모델값(1 ,2, 3, 4) + 데이터베이스에서 반환된 모델값(list,y 출력년도,m출력월) + 오늘날짜(today)
		 */
		request.setAttribute("startBlank", startBlank);
		request.setAttribute("endDay", endDay);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("totalTd", totalTd);
		
		request.setAttribute("list", list);
		request.setAttribute("y", y);
		request.setAttribute("m", m);
		
	
		// 3) 뷰 포워딩
		request.getRequestDispatcher("WEB-INF/view/CashBookListByMonth.jsp").forward(request,response);	
		}
}
