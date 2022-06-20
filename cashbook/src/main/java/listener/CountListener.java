package listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import dao.StatsDao;
import vo.Stats;

@WebListener
public class CountListener implements HttpSessionListener {
	private StatsDao statsDao;


    public void sessionCreated(HttpSessionEvent se)  { 
    	//현재 접속자 수 --> 톰켓 안에 세션의 수
    	int currentCount = (int)se.getSession().getServletContext().getAttribute("currentCount");
    	se.getSession().getServletContext().setAttribute("currentCount", currentCount+1);
    	System.out.println("CountListener currentCount : " + currentCount);
    	//날짜별 or 전체 접속자 수 --> DB이용
    	
    	this.statsDao = new StatsDao(); //statsDao호출 
    	Stats stats = statsDao.selectStatsOneByNow();
    	System.out.print(stats.toString() + "stats CountListner");
    	if(stats.getDay() == null) { //오늘 날짜 카운트가 없다
    		statsDao.insertStats();//오늘날짜로 카운트 1 추가
    	}else {//오늘 날짜 카운트가 있다면 
    		statsDao.updateStatsByNow();//오늘 날짜 카운트 1증가
    		
    	}
    	
    }
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	int currentCount = (int)se.getSession().getServletContext().getAttribute("currentCount");
    	se.getSession().getServletContext().setAttribute("currentCount", currentCount-1);
    }
	
}