package com.wandou.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DBHandler {
	String url = "jdbc:mysql://localhost/test";
	String user="root";
	String psd="wandou_mysql";
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	public Connection getConn(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, psd);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return conn;
	}
	public String query(String sql,String[] args){
		String result="";
		try{
			conn=getConn();
			System.out.println(sql);
			ps=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				ps.setString(i+1, args[i]);
			}			
			rs=ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();			
			int count=rsmd.getColumnCount();
			System.out.println(count);
			while (rs.next()) {
				for(int i=1;i<=count;i++){
					result+=rs.getString(i)+"*";
				}				
			}
			System.out.println(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		return result;		
	}
	public boolean insert(String sql,String[] args){
		boolean flag=false;
		try{
			conn=getConn();
			System.out.println(sql);
			ps=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				ps.setString(i+1, args[i]);
			}			
			int i=ps.executeUpdate();
			System.out.println(i);
			if(i==1){
				flag=true;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		return flag;
	}
	public boolean checkUser(String sql,String[] args){
		boolean flag=false;
		try{
			conn=getConn();
			ps=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				ps.setString(i+1, args[i]);
			}			
			rs=ps.executeQuery();
			if(rs.next()){
				flag=true;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		return flag;
	}
	
//	public static void main(String[] args){
//		DBHandler dbh=new DBHandler();
//		String result=dbh.query(
//				"select * from book where book_name like ? or book_author like ?",
//				new String[] { "wandou", "123456" });
//		System.out.println(result);
//	}
}
