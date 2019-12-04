package com.kb.team1.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kb.team1.DAO.UploadDAO;
import com.kb.team1.DTO.EMPDTO;
import com.kb.team1.DAO.ExcelDAO;

@WebServlet("*.do")
public class UploadCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UploadCtrl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		request.setCharacterEncoding("UTF-8");
		String reqURL = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmd = reqURL.substring(contextPath.length());
				
		UploadDAO updao = UploadDAO.getInstance();
		ExcelDAO exdao = ExcelDAO.getInstance();
		
		// 일괄 업로드 페이지로 가기
		if(cmd.equals("/totalupload/upload.do")) {
			System.out.println("/totalupload/upload.do");
			rd = request.getRequestDispatcher("/totalupload/upload.jsp");
			
		// 엑셀 파일을 받아와 값을 화면에 뿌리기 (무결성 조건 만족하는지 확인)
		}else if(cmd.equals("/totalupload/uploadView.do")){
			exdao.parsingFile(request);		
			System.out.println("/totalupload/uploadView.do");
			updao.tempCheck(request);
			updao.tempUpload(request);
			updao.tempSelect(request);
			rd = request.getRequestDispatcher("/totalupload/upload.do");
		
		// 화면에 무결성 어긋나는 값을 고쳐서 임시 DB에 수정 
		}else if(cmd.equals("/totalupload/tempupdate.do")) {
			System.out.println("tempupdate");
			updao.tempUpdate(request);
			updao.tempSelect(request);
			rd = request.getRequestDispatcher("/totalupload/upload.do");
			
		// 화면에 뿌려진 값을 main DB에 넣기
		}else if(cmd.equals("/totalupload/uploadSave.do")){
			System.out.println("uploadSave.do");
			updao.mergeTable();
			System.out.println("uploadSave_merge success");
			updao.tempDelete();
			rd=request.getRequestDispatcher("/totalupload/upload.do");
			
		// 체크박스 된 list 항목들을 엑셀로 출력하기 전에 조회
		} else if(cmd.equals("/maindbselect.do")) {
			String flag = request.getParameter("flag");
			updao.mainDBSelect(request);
			List<EMPDTO> stafflist = (List<EMPDTO>) request.getAttribute("originalList");
			
			request.setAttribute("stafflist = ", stafflist);
			if(flag.equals("list")) {
				rd = request.getRequestDispatcher("/list.fo");
			}else if(flag.equals("searchList")) {
				rd = request.getRequestDispatcher("/searchList.fo");
			}
			
		// 조회해온 값들을 엑셀파일로 만들어 저장하기
		}else if(cmd.equals("/outputexcel.do")) {
			System.out.println("outputexcel.do?");
			String flag = request.getParameter("flag");
			exdao.outputExcel(request);
			if(flag.equals("list")) {
				rd = request.getRequestDispatcher("/list.fo");
			}else if(flag.equals("searchList")) {
				rd = request.getRequestDispatcher("/searchList.fo");
			}
		} 
		rd.forward(request, response);
	}
}
