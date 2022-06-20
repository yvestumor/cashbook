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

public class HashtagDao {
	public List<Map<String,Object>> selectTagRankSearchList(String kind, String aDate, String bDate) {
		List<Map<String, Object>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");	
			String sql ="";
			if (kind.equals("") && aDate.equals("") & bDate.equals("")) { //3개다 입력하지 않았을 때
				sql = "SELECT kind, t.tag tag, t.cnt cnt, RANK() over(ORDER BY t.cnt DESC) ranking"
						+ " FROM "
						+ " (SELECT c.kind kind,c.cash_date, tag, COUNT(*) cnt"
						+ " FROM hashtag t INNER JOIN cashbook c"
						+ " ON t.cashbook_no = c.cashbook_no"
						+ " GROUP BY t.tag) t";
				stmt= conn.prepareStatement(sql);
			} else if (!kind.equals("") && aDate.equals("") & bDate.equals("")) {
				sql =  "SELECT kind, t.tag tag, t.cnt cnt, RANK() over(ORDER BY t.cnt DESC) ranking"
						+ " FROM "
						+ " (SELECT c.kind kind,c.cash_date, tag, COUNT(*) cnt"
						+ " FROM hashtag t INNER JOIN cashbook c"
						+ " ON t.cashbook_no = c.cashbook_no"
						+ " WHERE c.kind = ?"
						+ " GROUP BY t.tag) t";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1,kind);
			} else if (!kind.equals("") && aDate.equals("") & !bDate.equals("")){
				sql =  "SELECT kind, t.tag tag, t.cnt cnt, RANK() over(ORDER BY t.cnt DESC) ranking"
						+ " FROM "
						+ " (SELECT c.kind kind,c.cash_date, tag, COUNT(*) cnt"
						+ " FROM hashtag t INNER JOIN cashbook c"
						+ " ON t.cashbook_no = c.cashbook_no"
						+ " WHERE c.kind = ? AND c.cash_date BETWEEN '0000-01-01' AND ?"
						+ " GROUP BY t.tag) t";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1,kind);
				stmt.setString(2, bDate);
			} else if (!kind.equals("") && !aDate.equals("") && bDate.equals("")) {
				sql = "SELECT kind, t.tag tag, t.cnt cnt, RANK() over(ORDER BY t.cnt DESC) ranking"
						+ " FROM "
						+ " (SELECT c.kind kind,c.cash_date, tag, COUNT(*) cnt"
						+ " FROM hashtag t INNER JOIN cashbook c"
						+ " ON t.cashbook_no = c.cashbook_no"
						+ " WHERE c.kind = ? AND c.cash_date BETWEEN ? AND NOW()"
						+ " GROUP BY t.tag) t";				
				stmt = conn.prepareStatement(sql);
				stmt.setString(1,kind);
				stmt.setString(2, aDate);
			} else if (!kind.equals("") && !aDate.equals("") && !bDate.equals("")) {
				sql = "SELECT kind, t.tag tag, t.cnt cnt, RANK() over(ORDER BY t.cnt DESC) ranking"
						+ " FROM "
						+ " (SELECT c.kind kind,c.cash_date, tag, COUNT(*) cnt"
						+ " FROM hashtag t INNER JOIN cashbook c"
						+ " ON t.cashbook_no = c.cashbook_no"
						+ " WHERE c.kind = ? AND c.cash_date BETWEEN ? AND ?"
						+ " GROUP BY t.tag) t";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1,kind);
				stmt.setString(2, aDate);
				stmt.setString(3, bDate);
			} else if (kind.equals("") && aDate.equals("") && !bDate.equals("")) {
				sql =  "SELECT kind, t.tag tag, t.cnt cnt, RANK() over(ORDER BY t.cnt DESC) ranking"
						+ " FROM "
						+ " (SELECT c.kind kind,c.cash_date, tag, COUNT(*) cnt"
						+ " FROM hashtag t INNER JOIN cashbook c"
						+ " ON t.cashbook_no = c.cashbook_no"
						+ " WHERE c.cash_date BETWEEN '0000-01-01' AND ?"
						+ " GROUP BY t.tag) t";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1,bDate);
			} else if (kind.equals("") && !aDate.equals("") && bDate.equals("")) {
				sql = "SELECT kind, t.tag tag, t.cnt cnt, RANK() over(ORDER BY t.cnt DESC) ranking"
						+ " FROM "
						+ " (SELECT c.kind kind,c.cash_date, tag, COUNT(*) cnt"
						+ " FROM hashtag t INNER JOIN cashbook c"
						+ " ON t.cashbook_no = c.cashbook_no"
						+ " WHERE c.cash_date BETWEEN ? AND NOW()"
						+ " GROUP BY t.tag) t";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, aDate);
			} else if (kind.equals("") && !aDate.equals("") && !bDate.equals("")) {
				sql = "SELECT kind, t.tag tag, t.cnt cnt, RANK() over(ORDER BY t.cnt DESC) ranking"
						+ " FROM "
						+ " (SELECT c.kind kind,c.cash_date, tag, COUNT(*) cnt"
						+ " FROM hashtag t INNER JOIN cashbook c"
						+ " ON t.cashbook_no = c.cashbook_no"
						+ " WHERE c.cash_date BETWEEN ? AND ?"
						+ " GROUP BY t.tag) t";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1,aDate);
				stmt.setString(2,bDate);
			}
			System.out.println(sql + " <-- sql HashtagDao");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("kind", rs.getString("kind"));
				map.put("tag", rs.getString("tag"));
				map.put("cnt", rs.getString("cnt"));
				map.put("ranking", rs.getString("ranking"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	public List<Map<String, Object>> selectSameTagRankList(String tag) {
		List<Map<String, Object>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql =" SELECT t.tag tag"
				+ " ,kind"
				+ " ,c.cash cash"
				+ " ,c.cash_date cashDate"
				+ " ,memo"
				+ " ,c.update_date updateDate"
				+ ",c.create_date createDate"
				+ " FROM hashtag t"
				+ " INNER JOIN cashbook c"
				+ " ON t.cashbook_no = c.cashbook_no"
				+ " WHERE t.tag = ?"
				+ " GROUP BY c.cash_date";
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");	
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, tag);
			rs = stmt.executeQuery();
			System.out.println(sql +" <-- sql HashtagDao");
			System.out.println(tag +" <-- tag HashtagDao");		
			while (rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("tag", rs.getString("tag"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("cashDate", rs.getString("cashDate"));
				map.put("memo", rs.getString("memo"));
				map.put("updateDate", rs.getString("updateDate"));
				map.put("createDate", rs.getString("createDate"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public List<Map<String, Object>> selectTagRankList() {
		List<Map<String, Object>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		 /*
        SELECT t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) ranking
       FROM 
       (SELECT tag, COUNT(*) cnt
       FROM hashtag
       GROUP BY tag) t
     */

	    String sql = "SELECT t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) rank"
	               + "            FROM"
	               + "            (SELECT tag, COUNT(*) cnt"
	               + "            FROM hashtag"
	               + "            GROUP BY tag) t";

		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			  while(rs.next()) {
		            Map<String, Object> map = new HashMap<>();
		            map.put("tag", rs.getString("tag"));
		            map.put("cnt", rs.getInt("t.cnt"));
		            map.put("rank", rs.getInt("rank"));
		            list.add(map);
		         }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
