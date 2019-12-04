package com.kb.team1.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.SystemOutLogger;
import org.apache.tomcat.util.buf.StringUtils;

import com.kb.team1.DAO.ExcelDAO;
import com.kb.team1.DAO.StaffDAO;
//import com.kb.team1.DAO.UploadDAO;
import com.kb.team1.DTO.EMPDTO;

/**
 * Servlet implementation class ManagementCtrl
 */
@WebServlet("*.fo")
public class ManagementCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StaffDAO dm = StaffDAO.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			RequestDispatcher rd = null;
			request.setCharacterEncoding("UTF-8");
			String reqURL = request.getRequestURI();
			String contextPath = request.getContextPath();
			String cmd = reqURL.substring(contextPath.length());
			
			StaffDAO stfdao = new StaffDAO();
			ExcelDAO exdao = new ExcelDAO();
//			UploadDAO updao = new UploadDAO();
			//main
			if(cmd.equals("/main.fo")) {
				response.sendRedirect(request.getContextPath()+"/main.jsp");
			}
			//로그인페이지
			else if(cmd.equals("/loginPage.fo")) {
				response.sendRedirect(request.getContextPath()+"/login.jsp");
			}
			//로그인
			else if(cmd.equals("/login.fo")) {
				System.out.println("ctrl - login.fo");
				boolean result=false;
				
				String userid=request.getParameter("userid");
				String passwd=request.getParameter("passwd");
				
				result=dm.loginCheck(userid, passwd);
				
				if(result) {
					System.out.println("result= "+result);
					HttpSession session=request.getSession();
					session.setAttribute("userid", userid);
					session.setAttribute("message", "로그인 성공");
					response.sendRedirect(request.getContextPath()+"/main.jsp");
				}else {
					response.sendRedirect(request.getContextPath()+"/login.jsp?message=error");
				}
				
			}
			//로그아웃
			else if(cmd.equals("/logout.fo")) {
				//세션 초기화(전체삭제)
				//request.getSession().invalidate();
				//섹션 객체 생성
				HttpSession session=request.getSession();
				//섹션값을 모두 추기화 시킴
				session.invalidate();
				String page=request.getContextPath()
						+"/login.jsp?message=logout";
				response.sendRedirect(page);				
			}
/*			else if(cmd.equals("/management/staffInsert.fo")) {
				System.out.println("staffInsert.do come");
				stfdao.insertSingle(request);
				rd = request.getRequestDispatcher("insert.jsp");
				
			}*/
			//사원정보 입력화면
			else if(cmd.equals("/memberInsert.fo")) {
				rd = request.getRequestDispatcher("memberInsert.jsp");
				rd.forward(request, response);
			}	
			//사원추가 등록
			else if(cmd.equals("/memberWrite.fo")) {
				System.out.println("memberWrite.fo....");
				EMPDTO dto=new EMPDTO();
				String empno=request.getParameter("empno");
				response.setCharacterEncoding("UTF-8"); 
				response.setContentType("text/html; charset=UTF-8");

				boolean check = dm.dupCheck(empno);
				
				if(!check) {
					dto.setEmpno(empno);
					dto.setEname(request.getParameter("ename"));;
					dto.setBirthday(request.getParameter("birthday"));
					dto.setMobile(request.getParameter("mobile"));
					dto.setZipcode(request.getParameter("zipcode"));
					dto.setAddr1(request.getParameter("addr1"));
					dto.setAddr2(request.getParameter("addr2"));
					dto.setStartdate(request.getParameter("startdate"));
					dto.setHiredate(request.getParameter("hiredate"));
					dto.setDeptno(request.getParameter("deptno"));
					dto.setUpdeptno(request.getParameter("updeptno"));
					dto.setJobcode(request.getParameter("jobcode"));
					dto.setRemark(request.getParameter("remark"));			
					
					dm.memberWrite(dto);	
					response.getWriter().print("등록되었습니다.");
				}else {
					System.out.println("ctrl -  이미등록");
					response.getWriter().print("이미 등록된 사원입니다.");
				}
			}
			//좌측 리스트 보기에서 체크항목 삭제
			else if(cmd.equals("/memberDelete.fo")) {
				System.out.println("delete 이쪽으로 오나");
				String empno = StringUtils.join(request.getParameterValues("empno[]"));
				String flag = request.getParameter("flag");

				dm.delete(empno);
				
				if(flag.equals("list")) {
					rd = request.getRequestDispatcher("/list.fo");
				}else if(flag.equals("searchList")) {
					rd = request.getRequestDispatcher("/search.fo");
				}
				
				rd.forward(request, response);
			}
			//우측 조회하면에서 수정
			else if(cmd.equals("/memberUpdate.fo")) {
				System.out.println("수정 ctrl");
				EMPDTO dto=new EMPDTO();

				dto.setEmpno(request.getParameter("empno"));
				dto.setEname(request.getParameter("ename"));;
				dto.setBirthday(request.getParameter("birthday"));
				dto.setMobile(request.getParameter("mobile"));
				dto.setZipcode(request.getParameter("zipcode"));
				dto.setAddr1(request.getParameter("addr1"));
				dto.setAddr2(request.getParameter("addr2"));
				dto.setStartdate(request.getParameter("startdate"));
				dto.setDeptno(request.getParameter("deptno"));
				dto.setUpdeptno(request.getParameter("updeptno"));
				dto.setJobcode(request.getParameter("jobcode"));
				dto.setRemark(request.getParameter("remark"));
				
				dm.memberUpdate(dto);
				request.setAttribute("dto", dto);
				
				rd = request.getRequestDispatcher("memberDetail.jsp");
				rd.forward(request, response);
			}
			//main 좌측 상세조회화면
			else if(cmd.equals("/memberDetail.fo")) {
				String empno=request.getParameter("empno");
				String flag=request.getParameter("flag");
				EMPDTO dto=dm.memberDetail(empno);
				request.setAttribute("dto", dto);
				
				rd = request.getRequestDispatcher("memberDetail.jsp?flag="+flag);
				rd.forward(request, response);
			}
			//main 좌측 리스트 및 페이징
			else if(cmd.equals("/list.fo")) {
				int curPage = Integer.parseInt(request.getParameter("curPage"));
				//시작번호=(현제페이지 - 1) * 페이지당 게시물수 + 1
				//끝번호=시작번호  + (페이지당게시물수 - 1)				
				int start = 15*(curPage-1)+1;
				int end = start+(15-1);
				
				List<EMPDTO> dtos=dm.memberList(start,end);
				request.setAttribute("myList", dtos);
				
				//전체페이지수
				int rows=dm.cntmember();
				int total_page=(int) Math.ceil(rows/15.0);
				//블록당 표시할 페이지 수
				int page_list_size=10;
				//페이지 블록의 갯수
				int total_block=(int) Math.ceil(total_page*1.0/page_list_size);
				//현재 페이지가 몇번째 블록에 속하는지 계산
				int current_block=(curPage-1)/page_list_size + 1;
				//n번째 블록의 시작페이지 , 끝페이지 번호 계산
				int block_start=(current_block-1)*page_list_size+1;
				int block_end=block_start + page_list_size -1;
				//이전페이지, 다음페이지 번호
				int prev_page=current_block==1?1:(current_block-1)*page_list_size;
				int next_page=current_block > total_block
						?(current_block*page_list_size):(current_block*page_list_size)+1;
				//마지막 블록의 끝번호가 범위를 초과하지 않도록 처리
				if(block_end > total_page) {
					block_end=total_page;
				}
				//페이지 네비게이션에 필요한 변수들을 request 영역에 저장
				request.setAttribute("total_block", total_block);//블록갯수
				request.setAttribute("current_block", current_block);//현재블록
				request.setAttribute("block_start", block_start);//시작페이지번호
				request.setAttribute("block_end", block_end);//끝페이지번호
				request.setAttribute("prev_page", prev_page);//이전페이지번호
				request.setAttribute("next_page", next_page);//다음페이지번호
				request.setAttribute("current_page", curPage);//현재페이지번호		
				request.setAttribute("total_page", total_page); //전체페이지수				
				
				rd = request.getRequestDispatcher("memberList.jsp");
				rd.forward(request, response);
			}
			//검색
			else if(cmd.equals("/search.fo")) {
				int rows=0;
				int curPage = Integer.parseInt(request.getParameter("curPage"));
				String searchOption=request.getParameter("searchOption");
				String searchWord=request.getParameter("searchWord");
				
				int start = 15*(curPage-1)+1;
				int end = start+(15-1);
				
				List<EMPDTO> dtos=dm.search(searchOption,searchWord,start,end);
				request.setAttribute("myList", dtos);
				
				//전체페이지수
				rows=dm.searchTotalCnt(searchOption, searchWord);
				System.out.println("ctrl curPage="+curPage);
				System.out.println("ctrl search rows="+rows);
				
				int total_page=(int) Math.ceil(rows/15.0);
				//블록당 표시할 페이지 수
				int page_list_size=10;
				//페이지 블록의 갯수
				int total_block=(int) Math.ceil(total_page*1.0/page_list_size);
				//현재 페이지가 몇번째 블록에 속하는지 계산
				int current_block=(curPage-1)/page_list_size + 1;
				//n번째 블록의 시작페이지 , 끝페이지 번호 계산
				int block_start=(current_block-1)*page_list_size+1;
				int block_end=block_start + page_list_size -1;
				//이전페이지, 다음페이지 번호
				int prev_page=current_block==1?1:(current_block-1)*page_list_size;
				int next_page=current_block > total_block
						?(current_block*page_list_size):(current_block*page_list_size)+1;
				//마지막 블록의 끝번호가 범위를 초과하지 않도록 처리
				if(block_end > total_page) {
					block_end=total_page;
				}
				//페이지 네비게이션에 필요한 변수들을 request 영역에 저장
				request.setAttribute("total_block", total_block);//블록갯수
				request.setAttribute("current_block", current_block);//현재블록
				request.setAttribute("block_start", block_start);//시작페이지번호
				request.setAttribute("block_end", block_end);//끝페이지번호
				request.setAttribute("prev_page", prev_page);//이전페이지번호
				request.setAttribute("next_page", next_page);//다음페이지번호
				request.setAttribute("current_page", curPage);//현재페이지번호		
				request.setAttribute("total_page", total_page); //전체페이지수				
				
				rd = request.getRequestDispatcher("searchList.jsp");
				rd.forward(request, response);				
			}
		
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
