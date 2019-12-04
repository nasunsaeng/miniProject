package com.kb.team1.DTO;

public class EMPDTO {
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
	private int rnum;
	
	
	public EMPDTO(String empno, String ename, String birthday, String mobile, String holdoffice, String zipcode,
			String addr1, String addr2, String startdate, String hiredate, String updeptno, String deptno,
			String jobcode, String remark, String moddate, int rnum) {
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
		this.rnum = rnum;
	}


	@Override
	public String toString() {
		return "EMPDTO [empno=" + empno + ", ename=" + ename + ", birthday=" + birthday + ", mobile=" + mobile
				+ ", holdoffice=" + holdoffice + ", zipcode=" + zipcode + ", addr1=" + addr1 + ", addr2=" + addr2
				+ ", startdate=" + startdate + ", hiredate=" + hiredate + ", updeptno=" + updeptno + ", deptno="
				+ deptno + ", jobcode=" + jobcode + ", remark=" + remark + ", moddate=" + moddate + ", rnum=" + rnum
				+ "]";
	}


	public String getEmpno() {
		return empno;
	}


	public void setEmpno(String empno) {
		this.empno = empno;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getHoldoffice() {
		return holdoffice;
	}


	public void setHoldoffice(String holdoffice) {
		this.holdoffice = holdoffice;
	}


	public String getZipcode() {
		return zipcode;
	}


	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public String getAddr1() {
		return addr1;
	}


	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}


	public String getAddr2() {
		return addr2;
	}


	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}


	public String getStartdate() {
		return startdate;
	}


	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}


	public String getHiredate() {
		return hiredate;
	}


	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}


	public String getUpdeptno() {
		return updeptno;
	}


	public void setUpdeptno(String updeptno) {
		this.updeptno = updeptno;
	}


	public String getDeptno() {
		return deptno;
	}


	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}


	public String getJobcode() {
		return jobcode;
	}


	public void setJobcode(String jobcode) {
		this.jobcode = jobcode;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getModdate() {
		return moddate;
	}


	public void setModdate(String moddate) {
		this.moddate = moddate;
	}


	public int getRnum() {
		return rnum;
	}


	public void setRnum(int rnum) {
		this.rnum = rnum;
	}


	public EMPDTO() {	super();	}
	
}


