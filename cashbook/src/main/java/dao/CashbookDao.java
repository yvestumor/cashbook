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
   public List<Map<String, Object>> selectCashbookListByMonth(int y, int m) {
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      /*
       SELECT 
          cashbook_no cashbookNo
          ,DAY(cash_date) day
          ,kind
          ,cash
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
            + "       FROM cashbook"
            + "       WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?"
            + "       ORDER BY DAY(cash_date) ASC";
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