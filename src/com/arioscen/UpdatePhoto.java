package com.arioscen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class UpdatePhoto {

	public static void main(String[] args) {
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(JDBC.MySQL.connUrl, JDBC.MySQL.user, JDBC.MySQL.passwd);
			//check 'photo' column exists
			PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM employee"); 
			ResultSet rs = pstmt1.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			boolean photoExist = false;
			for (int i = 1; i <= count; i++){
				if (rsmd.getColumnName(i) == "photo"){
					photoExist = true;
					break;
				}
			}
			if(photoExist == false){
				PreparedStatement pstmt2 = conn.prepareStatement("ALTER TABLE employee ADD photo longblob");
				pstmt2.executeUpdate();
				System.out.println("add column 'photo'");
			}//check 'photo' column exists end
			
			//update photo to employee table.
			try{			
				//find empno in employee
				String queryStmt3 = "SELECT empno FROM employee";
				PreparedStatement pstmt3 = conn.prepareStatement(queryStmt3);
				ResultSet rs3 = pstmt3.executeQuery();
				while(rs3.next()){
					Integer empno = rs3.getInt("empno");
					String filePath = "res/"+empno.toString()+".jpg";
					//check photo exists.
					File file = new File(filePath);
					if(file.exists()){
						try{
							FileInputStream fis = new FileInputStream(file);
							String updateStmt4 = "UPDATE employee SET photo = ? WHERE empno = ?";
							PreparedStatement pstmt4 = conn.prepareStatement(updateStmt4);
							//setBlob(int parameterIndex, InputStream inputStream, long length)
							pstmt4.setBinaryStream(1, fis, file.length());//use setBinaryStream better than setBlob.
							pstmt4.setInt(2, empno);
							pstmt4.executeUpdate();
							System.out.println(empno.toString()+" photo updated");
							fis.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}				
			}catch(SQLException e){
				e.printStackTrace();
			}//update photo end
			
			//output photo
			try{
				String outputStmt5 = "SELECT empno, photo FROM employee";
				PreparedStatement pstmt5 = conn.prepareStatement(outputStmt5);
				ResultSet rs5 = pstmt5.executeQuery();
				while(rs5.next()){
					Integer empno = rs5.getInt("empno");
					Blob b = rs5.getBlob("photo");
					//employee may not have photo.
					if (b != null){
						byte[] data = b.getBytes(1, (int)b.length());
						File file = new File("res/c"+empno.toString()+".jpg");
						try{
							FileOutputStream fos = new FileOutputStream(file);
							fos.write(data, 0, (int)b.length());
							System.out.println(empno.toString()+" photo output");
							fos.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}				
			}catch(SQLException e){
				e.printStackTrace();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if (conn != null){
				try{
					conn.close();
				}catch(SQLException e2){
					e2.printStackTrace();
				}
			}
		}
		
	}

}
