package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class BootListener implements ServletContextListener {

    
    public BootListener() {
    }

    public void contextDestroyed(ServletContextEvent sce)  { //톰켓이꺼지고 난후
    }
    @Override
    public void contextInitialized(ServletContextEvent sce)  { 
    	//현재 사용자수
    	sce.getServletContext().setAttribute("currentCount", 0);
    	try {
			Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("db드라이버로딩");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
	
}
