package com.arioscen;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertEmployee {
	public static String[] input(File file){
		FileReader fr = null;
		String str = "";
		String[] strs = null;
		try {
			fr = new FileReader(file);
			int c = 0;
			while ((c = fr.read()) != -1){
				str += (char)c;
			}
			strs = str.split("\r\n");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(fr != null){
					fr.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}			
		}		
		return strs;
	}
	
	public static void main(String[] args) {
		Connection conn = null;
		int batchCount = 0;
		try {
			conn = DriverManager.getConnection(JDBC.MySQL.connUrl, JDBC.MySQL.user, JDBC.MySQL.passwd);
			
			String insertStmt = "INSERT INTO employee VALUES(?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(insertStmt);
			
			String filePath = "res/emp.txt";
			File file = new File(filePath);
			String[] emps = input(file);
			for (String emp : emps){
				String[] datas = emp.split(","); 
				for (int i = 0; i < datas.length; i++){
					pstmt.setString(i+1, datas[i]);
				}
				pstmt.addBatch();
				batchCount += 1;
				if (batchCount >= 3){
					pstmt.executeBatch();
				}				
			}
			pstmt.executeBatch();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
