package com.arioscen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class TestProcedure {

	public static void main(String[] args) {
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(JDBC.MySQL.connUrl, JDBC.MySQL.user, JDBC.MySQL.passwd);
			CallableStatement cstmt = conn.prepareCall("{call upd_emp_salary(?,?)}");
			cstmt.setDouble(1, 44000);
			cstmt.setInt(2, 1002);
			cstmt.execute();
			
			cstmt = conn.prepareCall("{call qry_emp(?,?,?)}");
			cstmt.setInt(1, 1002);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.DOUBLE);
			cstmt.execute();
			String name = cstmt.getString(2);
			double salary = cstmt.getDouble(3);
			System.out.print(name+"\t"+salary);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
