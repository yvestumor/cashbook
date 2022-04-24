package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import vo.Member;

public class MemberDao {
	// 회원가입
	public void insertMemberByIdPw(String memberId, String memberPw) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			String sql = "INSERT INTO member(member_id memberId,member_pw memberPw,create_date createDate) values {"
					+ "(?,PASSWORD(?),NOW()) ";
			  Class.forName("org.mariadb.jdbc.Driver");
		      conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
		      stmt = conn.prepareStatement(sql);
		      stmt.setString(1,memberId);
		      stmt.setString(2,memberPw);
		      
		      int row  = stmt.executeUpdate();
		      if (row ==1) {
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
	// 회원탈퇴
	// 회원정보
	
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