package com.kb.team1;

import javax.naming.*;
import java.sql.*;
import javax.sql.DataSource;

public class GetConnection {
	public static Connection getConnection() {
		//context.xml에 설정된 dbcp에서 커넥션 가져옴
		DataSource ds=null;  //javax.sql
		Connection conn=null;
		try {
			//context.xml을 분석하는 객체
			Context ctx=new InitialContext(); //javax.naming
			//context.xml의 Resource 태그 검색
			//java:comp/env 고정 , jdbc/OracleDB context.xml 의  name
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
			conn=ds.getConnection(); //커넥션을 할당받음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn; //커넥션 리턴		
	}
}


