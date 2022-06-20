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

import vo.Cashbook;

public class CashbookDao {
	public void deleteCashBook(int cashbookNo) {
		Connection conn = null;
		PreparedStatement cStmt = null; // delete cashbook 	
		PreparedStatement hStmt = null; // delte hashtag
		String cSql = "DELETE FROM cashbook where cashbook_no=?"; // cashbook 삭제하는 쿼리문
		
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			conn.setAutoCommit(false); // 오토커밋 해제
			
			// cashbook 삭제
			cStmt = conn.prepareStatement(cSql);
			cStmt.setInt(1, cashbookNo);
			
			
			//hashtag 삭제
			String hSql = "DELETE FROM hashtag where cashbook_no=?"; //hashtag 삭제하는 쿼리문
			hStmt = conn.prepareStatement(hSql);
			hStmt.setInt(1, cashbookNo);
			
			
			
			hStmt.executeUpdate();
			cStmt.executeUpdate(); 
			conn.commit(); //커밋하기
			
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public List<Cashbook> selectCashBookOne(int cashbookNo) {
		List<Cashbook> list = new ArrayList<Cashbook>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql ="SELECT cashbook_no cashbookNo, cash_date cashDate, kind, cash, memo, update_date updateDate, create_date createDate FROM cashbook WHERE cashbook_no =?";
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCashDate(rs.getString("cashDate"));
				c.setKind(rs.getString("kind"));
				c.setCash(rs.getInt("cash"));
				c.setMemo(rs.getString("memo"));
				c.setUpdateDate(rs.getString("updateDate"));
				c.setCreateDate(rs.getString("createDate"));
				list.add(c);
				
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
		public void insertCashbook(Cashbook cashbook, List<String> hashtag) {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
				conn.setAutoCommit(false); // 자동커밋을 해제
				
				String sql = "INSERT INTO cashbook(cash_date,kind,cash,memo,update_date,create_date)"
						+ " VALUES(?,?,?,?,NOW(),NOW())";
				
				// insert + select 방금생성된 행의 키값 ex) select 방금입력한 cashbook_no from cashbook;
				stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); 
				stmt.setString(1, cashbook.getCashDate());
				stmt.setString(2, cashbook.getKind());
				stmt.setInt(3, cashbook.getCash());
				stmt.setString(4, cashbook.getMemo());
				stmt.executeUpdate(); // insert
				rs = stmt.getGeneratedKeys(); // select 방금입력한 cashbook_no from cashbook
				int cashbookNo = 0;
				if(rs.next()) {
					cashbookNo = rs.getInt(1);
				}
				
				// hashtag를 저장하는 코드
				PreparedStatement stmt2 = null;
				for(String h : hashtag) {
					String sql2 = "INSERT INTO hashtag(cashbook_no, tag, create_date) VALUES(?, ?, NOW())";
					stmt2 = conn.prepareStatement(sql2);
					stmt2.setInt(1, cashbookNo);
					stmt2.setString(2, h);
					stmt2.executeUpdate();
				}
				
				conn.commit();
			} catch(Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try {
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