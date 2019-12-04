package com.kb.team1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.buf.StringUtils;

import com.kb.team1.GetConnection;
import com.kb.team1.DTO.EMPDTO;
import com.kb.team1.DTO.ExcelDTO;
import com.kb.team1.DTO.TempDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.http.HttpServletRequest;

public class UploadDAO {
	private static UploadDAO uploaddao = new UploadDAO();
	public static UploadDAO getInstance() {
		return uploaddao;
	}
	
	// 파싱한 엑셀 파일이 DB 무결성 조건 맞는지 체크 
	public void tempCheck(HttpServletRequest request) {
		List<ExcelDTO> excellist = (List<ExcelDTO>) request.getAttribute("excelLlist");
		List<TempDTO> stafflist = new ArrayList<>();
		System.out.println("temperrcheck in");

		Date today = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
		String thisyear = sdf2.format(today).substring(2);

		String[] startSplite = {};
		String[] birthSplite = {};

		for (int lstrow = 0; lstrow < excellist.size(); lstrow++) {
			ExcelDTO exdtp = excellist.get(lstrow);
			TempDTO tdto = new TempDTO();
			int errcheck = 0;
			String ename = exdtp.getEname();
			String birthday = exdtp.getBirthday();

			if (birthday.indexOf("-") == -1) {
				if (birthday.length() == 6) {
					birthSplite = new String[3];
					birthSplite[0] = birthday.substring(0, 2);
					birthSplite[1] = birthday.substring(2, 4);
					birthSplite[2] = birthday.substring(4);
					if (Integer.parseInt(birthSplite[0]) <= Integer.parseInt(thisyear)) {
						birthday = "20" + birthSplite[0] + "-" + birthSplite[1] + "-" + birthSplite[2];
					} else {
						birthday = "19" + birthSplite[0] + "-" + birthSplite[1] + "-" + birthSplite[2];
					}
				} else if (birthday.length() < 6) {
					birthSplite = new String[3];
					birthSplite[0] = "erbd";
					birthSplite[1] = "err";
					birthSplite[2] = "err";
				}
			} else {
				birthSplite = birthday.split("-");
				birthSplite[0] = birthSplite[0].substring(2);
			}

			String mobile = exdtp.getMobile();
			if (mobile == null) {
			}
			String zipcode = exdtp.getZipcode();
			String addr1 = exdtp.getAddr1();
			String addr2 = exdtp.getAddr2();
			String startdate = exdtp.getStartdate();

			if (startdate.indexOf("-") == -1) {
				if (startdate.length() == 6) {
					startSplite = new String[3];
					startSplite[0] = startdate.substring(0, 2);
					startSplite[1] = startdate.substring(2, 4);
					startSplite[2] = startdate.substring(4);
					if (Integer.parseInt(startSplite[0]) <= Integer.parseInt(thisyear)) {
						startdate = "20" + startSplite[0] + "-" + startSplite[1] + "-" + startSplite[2];
					} else {
						startdate = "19" + startSplite[0] + "-" + startSplite[1] + "-" + startSplite[2];
					}
				} else if (startdate.length() < 4) {
					startSplite = new String[3];
					startSplite[0] = "ersd";
					startSplite[1] = "err";
					startSplite[2] = "err";
				}
			} else {
				startSplite = startdate.split("-");
				startSplite[0] = startSplite[0].substring(2);
			}

			String updeptno = exdtp.getUpdeptno();
			String deptno = exdtp.getDeptno();
			String jobcode = exdtp.getJobcode();
			String remark = exdtp.getRemark();
			String moddate = sdf1.format(today);
			
			String holdoffice = "재직중";
			String hiredate = "";

			String empno = startSplite[0] + startSplite[1] + birthSplite[0] + birthSplite[1];

			tdto.setEmpno(empno); // primary key,
			tdto.setEname(ename);
			tdto.setBirthday(birthday);
			tdto.setMobile(mobile);
			tdto.setHoldoffice(holdoffice);
			tdto.setZipcode(zipcode);
			tdto.setAddr1(addr1);
			tdto.setAddr2(addr2);
			tdto.setStartdate(startdate);
			tdto.setHiredate(hiredate);
			tdto.setUpdeptno(updeptno);
			tdto.setDeptno(deptno);
			tdto.setJobcode(jobcode);
			tdto.setRemark(remark);
			tdto.setModdate(moddate);
			tdto.setErrcheck(errcheck);

			stafflist.add(tdto);
		}
		System.out.println("temperrcheck out");

		request.setAttribute("tempList", stafflist);
	}

	// 임시 DB에 insert
	public void tempUpload(HttpServletRequest request) {
		List<TempDTO> stafflist = (List<TempDTO>) request.getAttribute("tempList");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		System.out.println("upload method");

		for (int lstrow = 0; lstrow < stafflist.size(); lstrow++) {
			TempDTO tdto = stafflist.get(lstrow);
			String empno = tdto.getEmpno();
			String ename = tdto.getEname();
			String birthday = tdto.getBirthday();
			String mobile = tdto.getMobile();
			String holdoffice = tdto.getHoldoffice();
			String zipcode = tdto.getZipcode();
			String addr1 = tdto.getAddr1();
			String addr2 = tdto.getAddr2();
			String startdate = tdto.getStartdate();
			String hiredate = tdto.getHiredate();
			String updeptno = tdto.getUpdeptno();
			String deptno = tdto.getDeptno();
			String jobcode = tdto.getJobcode();
			String remark = tdto.getRemark();
			String moddate = tdto.getModdate();
			int errcheck = tdto.getErrcheck();
			try {
				conn = GetConnection.getConnection();
				pstmt = conn.prepareStatement(
						"insert into tempEMP values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, empno);
				pstmt.setString(2, ename);
				pstmt.setString(3, birthday);
				pstmt.setString(4, mobile);
				pstmt.setString(5, holdoffice);
				pstmt.setString(6, zipcode);
				pstmt.setString(7, addr1);
				pstmt.setString(8, addr2);
				pstmt.setString(9, startdate);
				pstmt.setString(10, hiredate);
				pstmt.setString(11, updeptno);
				pstmt.setString(12, deptno);
				pstmt.setString(13, jobcode);
				pstmt.setString(14, remark);
				pstmt.setString(15, moddate);
				pstmt.setInt(16, errcheck);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					if (rs != null) {
						rs.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}System.out.println("tempUpload success");
	}

	// 임시 DB의 값을 select
	public void tempSelect(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TempDTO> stafflist = new ArrayList<>();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date today = new Date();

		System.out.println("tempSelect in");
		try {
			conn = GetConnection.getConnection();
			pstmt = conn.prepareStatement("select * from tempemp");
			rs = pstmt.executeQuery();

			while (rs.next()) {

				int errcheck =rs.getInt("errcheck");
				
				String empno = rs.getString("empno");
				if(empno == null) {errcheck += 1;}
				String ename = rs.getString("ename");
				if(ename == null) {errcheck += 1;}
				String birthday = rs.getString("birthday");
				if(birthday == null) {errcheck += 1;}
				String mobile = rs.getString("mobile");
				if(mobile == null) {errcheck += 1;}
				String holdoffice = rs.getString("holdoffice");
				String zipcode = rs.getString("zipcode");
				if(zipcode == null) {errcheck += 1;}
				String addr1 = rs.getString("addr1");
				if(addr1 == null) {errcheck += 1;}
				String addr2 = rs.getString("addr2");
				if(addr2 == null) {errcheck += 1;}
				String startdate = rs.getString("startdate");
				if(startdate == null) {errcheck += 1;}
				String hiredate = rs.getString("hiredate");
				String updeptno = rs.getString("updeptno");
				if(updeptno == null) {errcheck += 1;}
				String deptno = rs.getString("deptno");
				if(deptno == null) {errcheck += 1;}
				String jobcode = rs.getString("jobcode");
				if(jobcode == null) {errcheck += 1;}
				String remark = rs.getString("remark");
				String moddate = sdf1.format(today);
				
				stafflist.add(new TempDTO(empno,ename,birthday,mobile,holdoffice,zipcode,addr1,addr2,
						startdate,hiredate,updeptno,deptno,jobcode,remark,moddate,errcheck));
			}
			request.setAttribute("templist", stafflist);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("tempSelect success");
		}
	}

	// 임시 DB 값 update
	public void tempUpdate(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
		Date today = new Date();

		String thisyear = sdf2.format(today).substring(2);

		System.out.println("tempUpdate in");

		String empno = request.getParameter("empno");
		String ename = request.getParameter("ename");
		String birthday = request.getParameter("birthday");
		String mobile = request.getParameter("mobile");
		String holdoffice = "재직중";
		String zipcode = request.getParameter("zipcode");
		String addr1 = request.getParameter("addr1");
		String addr2 = request.getParameter("addr2");
		String startdate = request.getParameter("startdate");
		String hiredate = request.getParameter("hiredate");
		String updeptno = request.getParameter("updeptno");
		String deptno = request.getParameter("deptno");
		String jobcode = request.getParameter("jobcode");
		String remark = request.getParameter("remark");
		String moddate = sdf1.format(today);
		int errcheck = 0;

		String[] startSplite = {};
		String[] birthSplite = {};

		if (birthday.indexOf("-") == -1) {
			if (birthday.length() == 6) {
				birthSplite = new String[3];
				birthSplite[0] = birthday.substring(0, 2);
				birthSplite[1] = birthday.substring(2, 4);
				birthSplite[2] = birthday.substring(4);
				if (Integer.parseInt(birthSplite[0]) <= Integer.parseInt(thisyear)) {
					birthday = "20" + birthSplite[0] + "-" + birthSplite[1] + "-" + birthSplite[2];
				} else {
					birthday = "19" + birthSplite[0] + "-" + birthSplite[1] + "-" + birthSplite[2];
				}
			}
		} else {
			birthSplite = birthday.split("-");
			birthSplite[0] = birthSplite[0].substring(2);
		}

		if (startdate.indexOf("-") == -1) {
			if (startdate.length() == 6) {
				startSplite = new String[3];
				startSplite[0] = startdate.substring(0, 2);
				startSplite[1] = startdate.substring(2, 4);
				startSplite[2] = startdate.substring(4);
				if (Integer.parseInt(startSplite[0]) <= Integer.parseInt(thisyear)) {
					startdate = "20" + startSplite[0] + "-" + startSplite[1] + "-" + startSplite[2];
				} else {
					startdate = "19" + startSplite[0] + "-" + startSplite[1] + "-" + startSplite[2];
				}
			}
		} else {
			startSplite = startdate.split("-");
			startSplite[0] = startSplite[0].substring(2);
		}

		String newEmpno = startSplite[0] + startSplite[1] + birthSplite[0] + birthSplite[1];

		try {
			System.out.println("이게 문제? 1");
			conn = GetConnection.getConnection();
			pstmt = conn.prepareStatement(
					"update tempemp set EMPNO = ?,ENAME = ?,BIRTHDAY = ?,MOBILE = ?,HOLDOFFICE = ?,ZIPCODE = ?,"
							+ "ADDR1 = ?,ADDR2 = ?,STARTDATE = ?,hiredate = ?,UPDEPTNO = ?,"
							+ "DEPTNO = ?,JOBCODE = ?,REMARK = ?,moddate = ?,errcheck=? where EMPNO = ?");
			System.out.println("이게 문제? 2");
			pstmt.setString(1, newEmpno);
			pstmt.setString(2, ename);
			pstmt.setString(3, birthday);
			pstmt.setString(4, mobile);
			pstmt.setString(5, holdoffice);
			pstmt.setString(6, zipcode);
			pstmt.setString(7, addr1);
			pstmt.setString(8, addr2);
			pstmt.setString(9, startdate);
			pstmt.setString(10, hiredate);
			pstmt.setString(11, updeptno);
			pstmt.setString(12, deptno);
			pstmt.setString(13, jobcode);
			pstmt.setString(14, remark);
			pstmt.setString(15, moddate);
			System.out.println("이게 문제? 3");
			pstmt.setInt(16, errcheck);
			System.out.println("이게 문제? 4");
			pstmt.setString(17, empno);

			pstmt.executeUpdate();
			
			System.out.println("success tempUpdate");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// 임시 DB와 main DB값 비교해서 합치기	
	public void mergeTable() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = GetConnection.getConnection();
			pstmt = conn.prepareStatement(
					"merge into Emp using tempEMP on (Emp.EMPNO = tempEMP.EMPNO) when matched then update set "+
					"Emp.Ename = tempEMP.Ename,Emp.BIRTHDAY = tempEMP.BIRTHDAY,Emp.MOBILE = tempEMP.MOBILE,"+
					"Emp.HOLDOFFICE = tempEMP.HOLDOFFICE,Emp.ZIPCODE = tempEMP.ZIPCODE,"+
					"Emp.ADDR1 = tempEMP.ADDR1,Emp.ADDR2 = tempEMP.ADDR2,Emp.STARTDATE = tempEMP.STARTDATE,"+
					"Emp.hiredate = tempEMP.hiredate,Emp.UPDEPTNO = tempEMP.UPDEPTNO,Emp.DEPTNO = tempEMP.DEPTNO,"+
					"Emp.JOBCODE = tempEMP.JOBCODE,Emp.REMARK = tempEMP.REMARK,"+
					"Emp.moddate = tempEMP.moddate when not matched then insert values "+
					"(tempEMP.EMPNO,tempEMP.ENAME,tempEMP.BIRTHDAY,tempEMP.MOBILE,tempEMP.HOLDOFFICE,"+
					"tempEMP.ZIPCODE,tempEMP.ADDR1,tempEMP.ADDR2,tempEMP.STARTDATE,tempEMP.hiredate,tempEMP.UPDEPTNO,"+
					"tempEMP.DEPTNO,tempEMP.JOBCODE,tempEMP.REMARK,tempEMP.moddate)");

			System.out.println("mergeTable1");
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("mergeTable2");
		}
	}

	// 임시 DB 비우기
	public void tempDelete() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = GetConnection.getConnection();
			pstmt = conn.prepareStatement("truncate table tempEMP ");
			pstmt.executeUpdate();
			System.out.println("tempDelete success ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// main DB의 값 받아오기
	public void mainDBSelect(HttpServletRequest request) {
		
		System.out.println("mainDBSelect");
//		String empnolist = StringUtils.join(request.getParameterValues("element[]"));
		String empnoarray[] = request.getParameterValues("empno[]");
		
		String empnolist = "";
		for (int i=0;i<empnoarray.length;i++) {
			if(i==empnoarray.length-1) {
				empnolist += "'"+empnoarray[i]+"'";
			}else {
				empnolist += "'"+empnoarray[i]+"',";
			}
		}
		
		System.out.println("empnolist = "+empnolist);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EMPDTO> stafflist = new ArrayList<>();
		
		try {
			conn = GetConnection.getConnection();
			System.out.println("메인디비 1");
			pstmt = conn.prepareStatement("select EMP.*, rownum from emp where empno in("+empnolist+")");
			System.out.println("메인디비 2");
//			pstmt.setString(1, empnolist);
			rs = pstmt.executeQuery();
			System.out.println("rs = "+rs);

			while (rs.next()) {
				System.out.println("메인디비 3");
				stafflist.add(
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
								rs.getInt("rownum")));
			}
			System.out.println(stafflist);
			request.setAttribute("originalList", stafflist);
			request.getSession().setAttribute("originalList", stafflist);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("mainDBSelect success");
		}
	}


	}
