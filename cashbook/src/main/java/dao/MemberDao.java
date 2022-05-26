package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vo.Member;

public class MemberDao {
	// 회원가입
	public void insertMemberByIdPw (String memberId, String memberPw) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String sql = "INSERT INTO member (member_id, member_pw,create_date) values"
				+ " (?, PASSWORD(?) ,NOW())";
		try {
			  Class.forName("org.mariadb.jdbc.Driver");
		      conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
		      stmt = conn.prepareStatement(sql);
		      stmt.setString(1, memberId);
		      stmt.setString(2, memberPw);
		      
		      int row  = stmt.executeUpdate();
		      if (row == 1) {
		    	  System.out.println("회원가입성공");
		      } else {
		    	  System.out.println("회원가입실패");
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
	// 회원수정
	public List<Member> updateMemberPw(String sessionMemberId, String memberPw) {
		List<Member> list = new ArrayList<>();
		 Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
		String sql ="Update member SET member_pw = PASSWORD(?) WHERE member_id = ?";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			 conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1,memberPw);
	         stmt.setString(2,sessionMemberId);  
	         rs = stmt.executeQuery();
	         if(rs.next()) {
	        	 Member m = new Member();
	        	 m.setMemberId(rs.getString("memberId"));
	        	 m.setMemberPw(rs.getString("memberPw"));
	        	 m.setCreateDate(rs.getString("createDate"));
	        	 list.add(m);
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
	// 회원탈퇴
	public void deleteMember(String sessionMemberId) {
		Connection conn = null;
		PreparedStatement cashbookStmt = null;
		PreparedStatement memberStmt = null;
		ResultSet rs = null;
		String cashbookSql = "DELETE FROM cashbook WHERE member_id = ?"; // memberId 가 포함되어있는 cashbook삭제 
		String memberSql = "DELETE FROM member WHERE member_id = ?"; // member 정보 삭제 
		
		try {
			 Class.forName("org.mariadb.jdbc.Driver");
	         conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	         conn.setAutoCommit(false); // 자동커밋 해제
	         
	         //cashbook 데이터 삭제 
	         cashbookStmt = conn.prepareStatement(cashbookSql);
	         cashbookStmt.setString(1, sessionMemberId);
	         cashbookStmt.executeUpdate();
	         
	         //member 데이터 삭제 	
	         memberStmt = conn.prepareStatement(memberSql);
	         memberStmt.setString(1, sessionMemberId);
	         memberStmt.executeUpdate();
	         
	         conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				 conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	
	}
	// 회원정보 조회
	public List<Member> selectMemberOne(String sessionMemberId) {
		List<Member> list = new ArrayList<Member>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id memberId"
				+ "         ,member_pw memberPw"
				+ "         ,create_date createDate "
				+ "     FROM member "
		            + "WHERE member_id = ? ";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			 conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, sessionMemberId);
	         rs = stmt.executeQuery();
	         if (rs.next()) {
	        	Member m = new Member();
	        	 m.setMemberId(rs.getString("memberId"));
	        	 m.setMemberPw(rs.getString("memberPw"));
	        	 m.setCreateDate(rs.getString("createDate"));
	        	 list.add(m);
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
	
	// 로그인
	public String selectMemberByIdPw(Member member) {
	      String memberId = null;
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw=PASSWORD(?)";
	      try {
	         Class.forName("org.mariadb.jdbc.Driver");
	         conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, member.getMemberId()); 
	         stmt.setString(2, member.getMemberPw());
	         rs = stmt.executeQuery();
	         if(rs.next()) {
	            memberId = rs.getString("memberId");
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
	      return memberId;
	}
}