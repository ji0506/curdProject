package view.borad;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import dao.BoardDao;
import model.Board;

public class BoardExam {

	private Scanner scan = new Scanner(System.in);
	private BoardDao bdao = new BoardDao();
	private String userId = "";

	public BoardExam()
	{

	}

	
	public BoardExam(String userId)
	{
		this.userId = userId;
	}
	
	public void list() {
		System.out.println();
		System.out.println("[게시판 목록]");
		System.out.println("---------------------------------------------------------------");
		System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer", "title", "date");
		System.out.println("---------------------------------------------------------------");

		try {

			List<Board> list = bdao.selectAll();

			for (Board b : list) {
				System.out.printf("%-6s%-12s%-16s%-40s\n", b.getBno(), b.getBwriter(), b.getBtitle(),b.getBdate());
			}

		} catch (Exception e) {
			e.printStackTrace();
			exit();
		}

		mainMenu();
	}

	public void mainMenu() {
		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Exit");
		System.out.print("메뉴 선택: ");
		String menuNo = scan.nextLine();

		switch (menuNo) {
		case "1":
			create();
			break;
		case "2":
			read();
			break;
		case "3":
			exit();
			break;
		}
	}

	public void create() {
		Board brd = new Board();
		System.out.println("[새 게시물 입력]");
		System.out.print("작성자:");
		System.out.println(userId);
		brd.setBwriter(userId);
		System.out.print("제목:");
		brd.setBtitle(scan.nextLine());
		System.out.print("내용:");
		brd.setBcontent(scan.nextLine());

		brd.setBdate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
		System.out.println("----------------------------------------");
		System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
		System.out.print("메뉴 선택: ");
		String menuNo = scan.nextLine();

		if ("1".equals(menuNo)) {
			try {
				bdao.create(brd);
			} catch (Exception e) {
				e.printStackTrace();
				exit();
			}
		}

	}

	public void read() {
		System.out.println("[게시물 읽기]");
		System.out.print("bno:");
		String bno = scan.nextLine();

		try {
			Board brd = bdao.selectById(Integer.parseInt(bno));

			System.out.println("###########");
			System.out.println("번호 : " + brd.getBno());
			System.out.println("제목 : " + brd.getBtitle());
			System.out.println("내용 : " + brd.getBcontent());
			System.out.println("작성자 : " + brd.getBwriter());
			System.out.println("날짜 : " + brd.getBdate());

			System.out.println("----------------------------------------");
			System.out.println("보조 메뉴: 1.Update | 2.Delete ");
			System.out.print("메뉴 선택(1,2 이외에 값을 입력하면 다시 List로): ");
			String menuNo = scan.nextLine();

			if ("1".equals(menuNo)) {
				if(brd.getBwriter().equals(userId))
					update(brd);
				else
					System.out.println("본인글만 수정할 수 있습니다.");
			} else if ("2".equals(menuNo)) {
				if(brd.getBwriter().equals(userId))
					delete(brd);
				else
					System.out.println("본인글만 삭제할 수 있습니다.");
				
			} else 
				return;

		} catch (Exception e) {
			e.printStackTrace();
			exit();
		}

	}

	private void update(Board brd) {
		try {
			System.out.println("[새 게시물 입력]");
			System.out.print("제목:");
			brd.setBtitle(scan.nextLine());
			System.out.print("내용:");
			brd.setBcontent(scan.nextLine());

			System.out.println("----------------------------------------");
			System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
			System.out.print("메뉴 선택: ");
			String menuNo = scan.nextLine();

			if ("1".equals(menuNo)) {
				bdao.update(brd);
			}
		} catch (Exception e) {
			e.printStackTrace();
			exit();
		}
	}

	private void delete(Board brd) {
		try {
			bdao.delete(brd);
		} catch (Exception e) {
			e.printStackTrace();
			exit();
		}
	}

	public void clear() {
		try {
			System.out.println("[게시물 전체 삭제]");
			System.out.println("----------------------------------------");
			System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
			System.out.print("메뉴 선택: ");
			String menuNo = scan.nextLine();
			
			if("1".equals(menuNo)){
				bdao.deleteAll();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			exit();
		}
		
		list();
	}

	public void exit() {
		System.out.println("** 게시판 종료 **");
		System.exit(0);

	}

	public static void main(String[] args) {
		try {
			BoardExam exam = new BoardExam();
			exam.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
