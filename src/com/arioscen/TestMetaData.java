package com.arioscen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMetaData {

	public static void main(String[] args) {
		Connection conn = null;
//		try{
//			conn = DriverManager.getConnection(connUrl, "root", "");
//			PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM employee"); 
//			ResultSet rs = pstmt1.executeQuery();
//			ResultSetMetaData rsmd = rs.getMetaData();
//			System.out.println(rsmd.getTableName(2));
//			
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
		
		try{
			conn = DriverManager.getConnection(JDBC.MySQL.connUrl, JDBC.MySQL.user, JDBC.MySQL.passwd);
			DatabaseMetaData dbmd = conn.getMetaData();
			System.out.println(dbmd.getDatabaseProductName());
			System.out.println(dbmd.getDriverName());
			ResultSet rs = dbmd.getTableTypes();
//			while(rs.next()){
//				System.out.println(rs.getString("TABLE_TYPE")+", ");
//			}
			
//			rs = dbmd.getPrimaryKeys(null, null, "employee");
//			while(rs.next()){
//				System.out.println(rs.getString("table_name"));
//				System.out.println(rs.getString("column_name"));
//				System.out.println(rs.getString("key_seq"));
//			}
			
			rs = dbmd.getColumns(null, "jdbc", "employee", null);
			while(rs.next()){
				System.out.println(rs.getString("column_name"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
