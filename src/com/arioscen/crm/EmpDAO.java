package com.arioscen.crm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpDAO implements IEmpDAO {
	public static final String INSERT_STMT = "INSERT INTO employee VALUES(?,?,?,?,?,?)";
	public static final String UPDATE_STMT = "UPDATE employee SET ename = ?, hiredate = ?, salary = ?, deptno = ?, title = ? WHERE empno = ?";
	public static final String DELETE_STMT = "DELETE FROM employee WHERE empno = ?";
	public static final String GET_ONE_STMT = "SELECT empno, ename, hiredate, salary, deptno, title FROM employee WHERE empno = ?";
	public static final String GET_ALL_STMT = "SELECT empno, ename, hiredate, salary, deptno, title FROM employee ORDER BY empno";
	Connection conn = null;

	@Override
	public void getConnection() throws SQLException {
		String connUrl = "jdbc:mysql://localhost:3306/jdbc?useSSL=false";
		conn = DriverManager.getConnection(connUrl, "root", "root");

	}

	@Override
	public int insert(EmpVO emp) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT);
		pstmt.setInt(1, emp.getEmpno());
		pstmt.setString(2, emp.getEname());
		pstmt.setDate(3, emp.getHiredate());
		pstmt.setDouble(4, emp.getSalary());
		pstmt.setInt(5, emp.getDeptno());
		pstmt.setString(6, emp.getTitle());
		updateCount = pstmt.executeUpdate();
		return updateCount;

	}

	@Override
	public int update(EmpVO emp) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_STMT);
		pstmt.setString(1, emp.getEname());
		pstmt.setDate(2, emp.getHiredate());
		pstmt.setDouble(3, emp.getSalary());
		pstmt.setInt(4, emp.getDeptno());
		pstmt.setString(5, emp.getTitle());
		pstmt.setInt(6, emp.getEmpno());
		updateCount = pstmt.executeUpdate();
		return updateCount;
	}

	@Override
	public int delete(int empno) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(DELETE_STMT);		
		pstmt.setInt(1, empno);
		updateCount = pstmt.executeUpdate();
		return updateCount;
	}

	@Override
	public EmpVO findByPrimaryKey(int empno) throws SQLException {
		EmpVO emp = null;		
		PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT);
		pstmt.setInt(1, empno);
		ResultSet rs = pstmt.executeQuery();
		//可能查詢沒有結果，而且ResultSet是從結果的前一段開始，必須使用next()
		if(rs.next()){
			emp = new EmpVO();//使用非static宣告的方法必須要有該類別的實體
			emp.setEmpno(rs.getInt("empno"));
			emp.setEname(rs.getString("ename"));
			emp.setHiredate(rs.getDate("hiredate"));
			emp.setSalary(rs.getDouble("salary"));
			emp.setDeptno(rs.getInt("deptno"));
			emp.setTitle(rs.getString("title"));
		}
		return emp;
	}

	@Override
	public List<EmpVO> getAll() throws SQLException {
		EmpVO emp = null;
		List<EmpVO> emps = new ArrayList<EmpVO>();		
		PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			emp = new EmpVO();
			emp.setEmpno(rs.getInt("empno"));
			emp.setEname(rs.getString("ename"));
			emp.setHiredate(rs.getDate("hiredate"));
			emp.setSalary(rs.getDouble("salary"));
			emp.setDeptno(rs.getInt("deptno"));
			emp.setTitle(rs.getString("title"));
			emps.add(emp);
		}
		return emps;
	}

	@Override
	public void closeConn() throws SQLException {
		if(conn != null){
			conn.close();
		}

	}

}
