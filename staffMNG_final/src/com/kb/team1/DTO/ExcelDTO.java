package com.kb.team1.DTO;

public class ExcelDTO {

	private String ename;
	private String birthday;
	private String mobile;
	private String zipcode;
	private String addr1;
	private String addr2;
	private String startdate;
	private String updeptno;
	private String deptno;
	private String jobcode;
	private String remark;
	private String moddate;

	public ExcelDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExcelDTO(String ename, String birthday, String mobile, String zipcode, String addr1, String addr2,
			String startdate, String updeptno, String deptno, String jobcode, String remark, String moddate) {
		super();
		this.ename = ename;
		this.birthday = birthday;
		this.mobile = mobile;
		this.zipcode = zipcode;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.startdate = startdate;
		this.updeptno = updeptno;
		this.deptno = deptno;
		this.jobcode = jobcode;
		this.remark = remark;
		this.moddate = moddate;
	}

	@Override
	public String toString() {
		return "ExcelDTO [" + (ename != null ? "ename=" + ename + ", " : "")
				+ (birthday != null ? "birthday=" + birthday + ", " : "")
				+ (mobile != null ? "mobile=" + mobile + ", " : "")
				+ (zipcode != null ? "zipcode=" + zipcode + ", " : "") + (addr1 != null ? "addr1=" + addr1 + ", " : "")
				+ (addr2 != null ? "addr2=" + addr2 + ", " : "")
				+ (startdate != null ? "startdate=" + startdate + ", " : "")
				+ (updeptno != null ? "updeptno=" + updeptno + ", " : "")
				+ (deptno != null ? "deptno=" + deptno + ", " : "")
				+ (jobcode != null ? "jobcode=" + jobcode + ", " : "")
				+ (remark != null ? "remark=" + remark + ", " : "") + (moddate != null ? "moddate=" + moddate : "")
				+ "] \n\n";
	}
	public String getEname() {return ename;}
	public String getBirthday() {return birthday;}
	public String getMobile() {return mobile;}
	public String getZipcode() {return zipcode;}
	public String getAddr1() {return addr1;}
	public String getAddr2() {return addr2;}
	public String getStartdate() {return startdate;}
	public String getUpdeptno() {return updeptno;}
	public String getDeptno() {return deptno;}
	public String getJobcode() {return jobcode;}
	public String getRemark() {return remark;}
	public String getModdate() {return moddate;}

	public void setEname(String ename) {this.ename = ename;}
	public void setBirthday(String birthday) {this.birthday = birthday;}
	public void setMobile(String mobile) {this.mobile = mobile;}
	public void setZipcode(String zipcode) {this.zipcode = zipcode;}
	public void setAddr1(String addr1) {this.addr1 = addr1;}
	public void setAddr2(String addr2) {this.addr2 = addr2;}
	public void setStartdate(String startdate) {this.startdate = startdate;}
	public void setUpdeptno(String updeptno) {this.updeptno = updeptno;}
	public void setDeptno(String deptno) {this.deptno = deptno;}
	public void setJobcode(String jobcode) {this.jobcode = jobcode;}
	public void setRemark(String remark) {this.remark = remark;}	
	public void setModdate(String moddate) {this.moddate = moddate;}

}
