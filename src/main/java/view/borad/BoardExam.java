package view.borad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import model.board.*;

public class BoardExam {

	private Scanner scan = new Scanner(System.in);
	private Connection conn;
	
	public BoardExam()
	{
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			
			conn = DriverManager.getConnection(url,"hr","12345");
		} catch (Exception e) {
			e.printStackTrace();
//			sysoexit(1);
		}
		
	}
	
	public void list()
	{
		System.out.println();
		System.out.println("[게시판 목록]");
		System.out.println("----------------------------------------");
		System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer","date", "title");
		System.out.println("----------------------------------------");
		
		try {
			String sql = "SELECT bno, btitle, , phone  FROM customer";
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
