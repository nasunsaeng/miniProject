package com.kb.team1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kb.team1.GetConnection;
import com.kb.team1.DTO.EMPDTO;

public class StaffDAO {
	
	private static StaffDAO md = new StaffDAO();
	public static StaffDAO getInstance() {
		return md;
	}
	
	//검색결과 list
	public List<EMPDTO> search(String searchOption, String searchWord, int start, int end){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EMPDTO> list = new ArrayList<>();		
		String sql=null;
		try {
			System.out.println("searchOption="+searchOption);
			System.out.println("searchWord="+searchWord);
			searchWord="%"+searchWord+"%";
			conn = GetConnection.getConnection();	//context.xml 에 DB연결
			if(searchOption.equals("ename")) {
				sql="select *  "+
				   "from (select p.*, rownum as rnum "+
				   "	  from (select * from emp where ename like ? "+
				   "	        order by ename) p) "+
				   "where rnum between ? and ? ";
			}else if(searchOption.equals("deptno")) {
				sql="select *  "+
				   "from (select p.*, rownum as rnum "+
				   "	  from (select * from emp where deptno like ? "+
				   "	        order by ename) p) "+
				   "where rnum between ? and ? ";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				EMPDTO dto=new EMPDTO();
				dto.setEmpno(rs.getString("empno"));
				dto.setEname(rs.getString("ename"));	
				dto.setBirthday(rs.getString("birthday"));
				dto.setMobile(rs.getString("mobile"));
				dto.setZipcode(rs.getString("zipcode"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
				dto.setStartdate(rs.getString("startdate"));
				dto.setBirthday(rs.getString("hiredate"));
				dto.setUpdeptno(rs.getString("updeptno"));
				dto.setDeptno(rs.getString("deptno"));
				dto.setJobcode(rs.getString("jobcode"));
				dto.setRemark(rs.getString("remark"));
				dto.setModdate(rs.getString("moddate"));
				list.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();					
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	//검색결과 총 count
	public int searchTotalCnt(String searchOption, String searchWord) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		String sql=null;
		try {
			System.out.println("searchOption="+searchOption);
			System.out.println("searchWord="+searchWord);
			searchWord="%"+searchWord+"%";
			conn = GetConnection.getConnection();	//context.xml 에 DB연결
			if(searchOption.equals("ename")) {
				sql="select count(*) from emp where ename like ?";
			}else if(searchOption.equals("deptno")) {
				sql="select count(*) from emp where deptno like ?";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) cnt = rs.getInt(1);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();					
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt;		
	}
	//login 체크
	public boolean loginCheck(String userid, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean adminChk=false;
		System.out.println("dao-loginCheck");
		try {
			conn = GetConnection.getConnection();
			String sql="select * from manager where userid=? and passwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, passwd);
			
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				adminChk=true;
			}else {
				adminChk=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();				
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
		}
		return adminChk;		
	}
	//중복체크
	public boolean dupCheck(String empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean empcheck=false;
		System.out.println("dao-dupCheck");
		try {
			conn = GetConnection.getConnection();
			String sql="select * from emp where empno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(empno));
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				empcheck=true;
			}else {
				empcheck=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();				
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
		}
		return empcheck;
	}
	//사원정보 상세조회
	public EMPDTO memberDetail(String empno) {
		EMPDTO dto = new EMPDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = GetConnection.getConnection();
			String sql="select * from emp where empno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(empno));
			rs = pstmt.executeQuery();
			if( rs.next() )
			{
				dto.setEmpno(rs.getString("empno"));
				dto.setEname(rs.getString("ename"));
				dto.setBirthday(rs.getString("birthday"));
				dto.setMobile(rs.getString("mobile"));
				dto.setHoldoffice(rs.getString("holdoffice"));
				dto.setZipcode(rs.getString("zipcode"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
				dto.setStartdate(rs.getString("startdate"));
				dto.setHiredate(rs.getString("hiredate"));
				dto.setUpdeptno(rs.getString("updeptno"));
				dto.setDeptno(rs.getString("deptno"));
				dto.setJobcode(rs.getString("jobcode"));
				dto.setRemark(rs.getString("remark"));
				dto.setModdate(rs.getString("moddate"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}

	// 사원리스트
	public List<EMPDTO> memberList(int start, int end){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EMPDTO> list = new ArrayList<>();			
		try {
			conn = GetConnection.getConnection();	//context.xml 에 DB연결
			String sql="select *  "+
					   "from (select p.*, rownum as rnum "+
					   "	  from (select * from emp "+
					   "	        order by empno) p) "+
					   "where rnum between ? and ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				list.add(
					new EMPDTO(
						rs.getString("empno"),
						rs.getString("ename"),
						rs.getString("birthday"),
						rs.getString("mobile"),
						rs.getString("holdoffice"),
						rs.getString("zipcode"),
						rs.getString("addr1"),
						rs.getString("addr2"),
						rs.getString("startdate"),
						rs.getString("hiredate"),
						rs.getString("updeptno"),
						rs.getString("deptno"),
						rs.getString("jobcode"),
						rs.getString("remark"),
						rs.getString("moddate"),
						rs.getInt("rnum")
					)
				);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();					
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	// 전체 사원수
	public int cntmember(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		try {
			conn = GetConnection.getConnection();
			String sql="select count(*) from emp";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) cnt = rs.getInt(1);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();					
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt;
	}
	public void delete(String empno) {
		Connection conn=null;
		PreparedStatement pstmt = null;
		try {
			conn = GetConnection.getConnection();
			pstmt = conn.prepareStatement("delete from emp " + 
															"where empno in ( "+empno+" )");
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	//사원정보수정
	public void memberUpdate(EMPDTO dto) {
		System.out.println("수정 dao");
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn = GetConnection.getConnection();
			String sql="update emp set ename=?, birthday=?, mobile=?, zipcode=?, "
					+ "addr1=?, addr2=?, startdate=?, deptno=?, updeptno=?,"
					+ "jobcode=?, MODDATE=To_char(SYSDATE,'YYYY/MM/DD'),remark=? "
					+ "where empno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getEname());
			pstmt.setString(2, dto.getBirthday());
			pstmt.setString(3, dto.getMobile());
			pstmt.setString(4, dto.getZipcode());
			pstmt.setString(5, dto.getAddr1());
			pstmt.setString(6, dto.getAddr2());
			pstmt.setString(7, dto.getStartdate());
			pstmt.setString(8, dto.getDeptno());
			pstmt.setString(9, dto.getUpdeptno());
			pstmt.setString(10, dto.getJobcode());
			pstmt.setString(11, dto.getRemark());
			pstmt.setString(12, dto.getEmpno());
			
			pstmt.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
	// 사원정보등록
	public void memberWrite(EMPDTO dto) {
		System.out.println("dao - memberWrite");
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn = GetConnection.getConnection();
			String sql="INSERT INTO EMP(EMPNO,ENAME,BIRTHDAY,MOBILE,ZIPCODE,ADDR1,ADDR2, " 
			          +"                 STARTDATE,HIREDATE,DEPTNO,UPDEPTNO,JOBCODE,REMARK,MODDATE) " 
					  +"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,To_char(SYSDATE,'YYYY/MM/DD'))";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getEmpno());
			pstmt.setString(2, dto.getEname());
			pstmt.setString(3, dto.getBirthday());
			pstmt.setString(4, dto.getMobile());
			pstmt.setString(5, dto.getZipcode());
			pstmt.setString(6, dto.getAddr1());
			pstmt.setString(7, dto.getAddr2());
			pstmt.setString(8, dto.getStartdate());
			pstmt.setString(9, dto.getHiredate());
			pstmt.setString(10, dto.getDeptno());
			pstmt.setString(11, dto.getUpdeptno());
			pstmt.setString(12, dto.getJobcode());
			pstmt.setString(13, dto.getRemark());
			
			pstmt.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

}
