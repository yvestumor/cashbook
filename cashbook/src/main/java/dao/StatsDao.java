package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import vo.Stats;

public class StatsDao {
	public void insertStats () { // <-- Listener
		 //INSERT INTO stats(day, cnt) VALUES(CURDATE(), 1);
		Connection conn = null;
		PreparedStatement stmt = null;

		 String sql = "INSERT INTO stats(day,cnt) VALUES(CURDATE(),1)";
		 try {
			 conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");	
			stmt = conn.prepareStatement(sql);
		   int row = stmt.executeUpdate();
		   if (row ==1) {
			   System.out.println("오늘날짜로 생성 <-- StatsDao.insertStats");
		   } else {
			   System.out.println("오늘날짜로 생성 실패 <--- Stats.isnertStats");
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
	
	public Stats selectStatsOneByNow() { // <-- Listener, Controller
		// SELECT day,cnt,FROM stats WHERE DAY = CURDATE()
		Stats stats = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT day"
				+ "         ,cnt "
				+ "     FROM stats "
				+ "    WHERE day = CURDATE()";
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
			stats = new Stats();
				stats.setDay(rs.getString("day"));
				stats.setCnt(rs.getInt("cnt"));
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
		
		return stats;
	}
	
	public void updateStatsByNow() { // <--Listener
		// UPDATE stats SET cnt = cnt +1 WHERE DAY = CURDATE()
		Connection conn = null;
		PreparedStatement stmt = null; 
		String sql = "UPDATE stats SET cnt = cnt +1 WHERE DAY = CURDATE()"; // 클릭할 때마다 1씩 추가
		try {
			
	        conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	        stmt = conn.prepareStatement(sql);
	        int row = stmt.executeUpdate();
	       if (row == 1) {	
	    	   System.out.println("Update성공 <-- StatsDao.updateStatsByNow");
	       } else {
	    	   System.out.println("Update실패 <-- StatsDao.updateStatsByNow");
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
	
	public int selectStatsTotalCount() { // <-- Controller
		int totalCount = 0;
		// SELECT SUM(cnt) from stats	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT SUM(cnt) cnt FROM stats";
		try {
		
	        conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	        stmt = conn.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        if (rs.next()) { 
	        	totalCount = rs.getInt("cnt");
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
		return totalCount;
	}
} 