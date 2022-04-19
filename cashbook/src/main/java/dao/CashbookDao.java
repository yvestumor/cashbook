package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashbookDao {
	public void insertCashbookList(int y,int m, int d,int cash,String kind, String memo) {
		  Connection conn = null;
	      PreparedStatement stmt = null;
	      
	      String sql ="INSERT INTO cashbook (cash_date cashDate, kind ,cash , memo,update_date updateDate, create_date createDate) VALUES ('?-?-?','?',?,'?',NOW(),NOW())";
	      try {
	          Class.forName("org.mariadb.jdbc.Driver");
	          conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	          stmt = conn.prepareStatement(sql);
	          stmt.setInt(1, y);
	          stmt.setInt(2, m);
	          stmt.setInt(3, d);
	          stmt.setString(4, kind);
	          stmt.setInt(5, cash);
	          stmt.setString(6, memo);
	          
	          int row = stmt.executeUpdate();
	  		if(row == 1) {
	  			System.out.println("입력성공");
	  		} else {
	  			System.out.println("입력실패");
	  		}
	      } catch (Exception e) {
	          e.printStackTrace();
	      } finally {
	         try {
	            stmt.close();
	            conn.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         } 		
	      }
	}
   public List<Map<String, Object>> selectCashbookListByMonth(int y, int m) {
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      /*
       SELECT 
          cashbook_no cashbookNo
          ,DAY(cash_date) day
          ,kind
          ,cash
          ,CONCAT(LEFT(memo,5),'...') memo
       FROM cashbook
       WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?
       ORDER BY DAY(cash_date) ASC
       */
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      String sql = "SELECT"
            + "          cashbook_no cashbookNo"
            + "          ,DAY(cash_date) day"
            + "          ,kind"
            + "          ,cash"
            + "			,(LEFT(memo,5)) memo"
            + "       FROM cashbook"
            + "       WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?"
            + "       ORDER BY DAY(cash_date) ASC,kind ASC";
      try {
         Class.forName("org.mariadb.jdbc.Driver");
         conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
         stmt = conn.prepareStatement(sql);
         stmt.setInt(1, y);
         stmt.setInt(2, m);
         rs = stmt.executeQuery();
         while(rs.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("cashbookNo", rs.getInt("cashbookNo"));
            map.put("day", rs.getInt("day"));
            map.put("kind", rs.getString("kind"));
            map.put("cash", rs.getInt("cash"));
            map.put("memo", rs.getString("memo"));
            list.add(map);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            rs.close();
            stmt.close();
            conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return list;
   }
}