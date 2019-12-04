package com.kb.team1.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kb.team1.DTO.EMPDTO;
import com.kb.team1.DTO.ExcelDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelDAO {
	
		private static ExcelDAO exceldao = new ExcelDAO();
		public static ExcelDAO getInstance() {
			return exceldao;
		}
	
	// 폴더에서 엑셀파일 받아와 파싱	
	public void parsingFile(HttpServletRequest request) {
		
		String uploadPath = "./upload";
		List<ExcelDTO> excelLlist = new ArrayList<>();
		File folder = new File(uploadPath);

		Workbook wb = null;
		folder.mkdirs();

		try {
			MultipartRequest multi = new MultipartRequest(request, uploadPath, 10 * 1024 * 1024, "utf-8",
					new DefaultFileRenamePolicy());
			File file = multi.getFile("file");
			String fname = file.getName();

			FileInputStream fis = new FileInputStream(uploadPath + "/" + fname);

			int pos = fname.lastIndexOf(".");
			String cutName = fname.substring(pos, fname.length());

			if (cutName.equals(".xls")) {
				wb = new HSSFWorkbook(new POIFSFileSystem(fis)); // *.xls
			} else if (cutName.equals(".xlsx")) {
				wb = new XSSFWorkbook(fis); // *.xlsx
			}

			Sheet sheet = wb.getSheetAt(0); // Get Excel Sheet

			int rows = sheet.getPhysicalNumberOfRows();
			for (int rownum = 1; rownum < rows; rownum++) {
				Row row = sheet.getRow(rownum);
				ExcelDTO edto = new ExcelDTO();
				String[] startSplite = {};
				String[] birthSplite = {};

				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (int col = 0; col < cells; col++) {
						Cell cell = row.getCell(col);
						String vCell = "";
						String birthdate = "";
						String startdate = "";

						if (cell == null) {
							vCell = "";
						} else {
							switch (cell.getCellType()) {
							case FORMULA:
								vCell = cell.getCellFormula();
								break;
							case NUMERIC:
								int vvCell = (int) cell.getNumericCellValue();
								vCell = vvCell + "";
								break;
							case STRING:
								vCell = cell.getStringCellValue() + "";
								break;
							case BLANK:
								vCell = cell.getBooleanCellValue() + "";
								break;
							case ERROR:
								vCell = cell.getErrorCellValue() + "";
								break;
							}
						}
						if (col == 0) {
							edto.setEname(vCell);
						} else if (col == 1) {
							edto.setBirthday(vCell);
							birthdate = vCell;
							birthSplite = birthdate.split("-");
						} else if (col == 2) {
							edto.setMobile(vCell);
						} else if (col == 3) {
							edto.setZipcode(vCell);
						} else if (col == 4) {
							edto.setAddr1(vCell);
						} else if (col == 5) {
							edto.setAddr2(vCell);
						} else if (col == 6) {
							edto.setStartdate(vCell);
							startdate = vCell;
							startSplite = startdate.split("-");
						} else if (col == 7) {
							edto.setUpdeptno(vCell);
						} else if (col == 8) {
							edto.setDeptno(vCell);
						} else if (col == 9) {
							edto.setJobcode(vCell);
						} else if (col == 10) {
							edto.setRemark(vCell);
						}

					}
					// small for

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date today = new Date();

					edto.setModdate(sdf.format(today));

					excelLlist.add(edto);

				} // if Cell != null
				fis.close();
			} // Big for
			request.setAttribute("excelLlist", excelLlist);
			System.out.println("excelLlist =" + excelLlist + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 화면에서 선택한 항목들을 엑셀파일로 출력
	@SuppressWarnings("resource")
	public void outputExcel(HttpServletRequest request) {
		System.out.println("outputExcel");
		List<EMPDTO> stafflist = (List<EMPDTO>) request.getSession().getAttribute("originalList");
		System.out.println("stafflist" + request.getAttribute("stafflist"));
		
		String excelType = request.getParameter("exceltype");
		System.out.println("excelType = " + excelType);

		String[] checkelement = request.getParameterValues("elements[]");
		for (String temp : checkelement) {
			System.out.println("checkelement = " + temp);
		}

		String outputPath = "C:user/Downloads";
		File folder = new File(outputPath);
		Workbook wdx;
		folder.mkdirs();

		if (excelType.equals("xlsx")) {
			wdx = new XSSFWorkbook();
		} else {
			wdx = new HSSFWorkbook();
		}
		CellStyle cellStyle = wdx.createCellStyle();
		cellStyle.setWrapText(true);
		// Cell 색깔, 무늬 채우기
		cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);

		Row row = null;
		Cell cell = null;
		Sheet sheet1 = wdx.createSheet("mysheet");
		System.out.println();
		for (int rows = 0; rows < 1; rows++) {
			System.out.println("outputExcel 2");
			EMPDTO eDto = stafflist.get(rows);
			row = sheet1.createRow(rows);
			if (rows == 0) {
				for (int cols = 0; cols < checkelement.length; cols++) {
					cell = row.createCell(cols);
					cell.setCellValue(checkelement[cols]);
					cell.setCellStyle(cellStyle);
					cell.setCellStyle(cellStyle);
				}
			}
		}
		for (int rows = 1; rows < stafflist.size(); rows++) {
			EMPDTO eDto = stafflist.get(rows);
			row = sheet1.createRow(rows);
			for (int cols = 0; cols < checkelement.length; cols++) {
				cell = row.createCell(cols);

				switch (checkelement[cols]) {
				case "empno":
					cell.setCellValue(eDto.getEmpno());
					break;
				case "ename":
					cell.setCellValue(eDto.getEname());
					break;
				case "birthday":
					cell.setCellValue(eDto.getBirthday());
					break;
				case "mobile":
					cell.setCellValue(eDto.getMobile());
					break;
				case "holdoffice":
					cell.setCellValue(eDto.getHoldoffice());
					break;
				case "addr":
					cell.setCellValue(eDto.getZipcode() + " " + eDto.getAddr1() + " " + eDto.getAddr2());
					break;
				case "startdate":
					cell.setCellValue(eDto.getStartdate());
					break;
				case "hiredate":
					cell.setCellValue(eDto.getHiredate());
					break;
				case "updeptno":
					cell.setCellValue(eDto.getUpdeptno());
					break;
				case "deptno":
					cell.setCellValue(eDto.getDeptno());
					break;
				case "jobcode":
					cell.setCellValue(eDto.getJobcode());
					break;
				case "moddate":
					cell.setCellValue(eDto.getRemark());
					break;
				}
			}
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			Date today = new Date();
			String todayString = sdf.format(today);
			String[] splitToday = todayString.split("-");
			String fileName = splitToday[0].substring(2) + splitToday[1] + splitToday[2] + "_" + splitToday[3]
					+ splitToday[4];

			if (excelType.equals("xlsx")) {
				FileOutputStream fileOutx = new FileOutputStream(outputPath + File.separator + fileName + ".xlsx");
				wdx.write(fileOutx);
			} else {
				FileOutputStream fileOutx = new FileOutputStream(outputPath + File.separator + fileName + ".xls");
				wdx.write(fileOutx);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
