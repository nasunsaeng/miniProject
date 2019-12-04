package com.kb.team1.DTO;

public class TempDTO {
	private String empno; // primary key,
	private String ename;
	private String birthday;
	private String mobile;
	private String holdoffice;
	private String zipcode;
	private String addr1;
	private String addr2;
	private String startdate;
	private String hiredate;
	private String updeptno;
	private String deptno;
	private String jobcode;
	private String remark;
	private String moddate;
	private int errcheck;

	public TempDTO() {	super();	}
	
	public TempDTO(String empno, String ename, String birthday, String mobile, String holdoffice, String zipcode,
			String addr1, String addr2, String startdate, String hiredate, String updeptno, String deptno,
			String jobcode, String remark, String moddate, int errcheck) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.birthday = birthday;
		this.mobile = mobile;
		this.holdoffice = holdoffice;
		this.zipcode = zipcode;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.startdate = startdate;
		this.hiredate = hiredate;
		this.updeptno = updeptno;
		this.deptno = deptno;
		this.jobcode = jobcode;
		this.remark = remark;
		this.moddate = moddate;
		this.errcheck = errcheck;
	}


	@Override
	public String toString() {
		return "EMPDTO [empno=" + empno + ", ename=" + ename + ", birthday=" + birthday + ", mobile=" + mobile
				+ ", holdoffice=" + holdoffice + ", zipcode=" + zipcode + ", addr1=" + addr1 + ", addr2=" + addr2
				+ ", startdate=" + startdate + ", hiredate=" + hiredate + ", updeptno=" + updeptno + ", deptno="
				+ deptno + ", jobcode=" + jobcode + ", remark=" + remark + ", moddate=" + moddate + errcheck + "] \n\n";
	}
	public String getEmpno() {return empno;	}
	public String getEname() {return ename;}
	public String getBirthday() {return birthday;}
	public String getMobile() {return mobile;}
	public String getHoldoffice() {return holdoffice;}
	public String getZipcode() {return zipcode;}
	public String getAddr1() {return addr1;}
	public String getAddr2() {return addr2;}
	public String getStartdate() {return startdate;}
	public String getHiredate() {return hiredate;}
	public String getUpdeptno() {return updeptno;}
	public String getDeptno() {return deptno;}
	public String getJobcode() {return jobcode;}
	public String getRemark() {return remark;}
	public String getModdate() {return moddate;}
	public int getErrcheck() {return errcheck;}

	public void setEmpno(String empno) {this.empno = empno;}
	public void setEname(String ename) {this.ename = ename;}
	public void setBirthday(String birthday) {this.birthday = birthday;}
	public void setMobile(String mobile) {this.mobile = mobile;}
	public void setHoldoffice(String holdoffice) {this.holdoffice = holdoffice;}
	public void setZipcode(String zipcode) {this.zipcode = zipcode;}
	public void setAddr1(String addr1) {this.addr1 = addr1;}
	public void setAddr2(String addr2) {this.addr2 = addr2;}
	public void setStartdate(String startdate) {this.startdate = startdate;}
	public void setHiredate(String hiredate) {this.hiredate = hiredate;}
	public void setUpdeptno(String updeptno) {this.updeptno = updeptno;}
	public void setDeptno(String deptno) {this.deptno = deptno;}
	public void setJobcode(String jobcode) {this.jobcode = jobcode;}
	public void setRemark(String remark) {this.remark = remark;}	
	public void setModdate(String moddate) {this.moddate = moddate;}
	public void setErrcheck(int errcheck) {this.errcheck = errcheck;}

}


