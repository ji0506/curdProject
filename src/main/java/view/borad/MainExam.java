package view.borad;

import java.util.List;
import java.util.Scanner;

import org.eclipse.persistence.jpa.jpql.parser.NewValueBNF;

import dao.UserDao;
import model.User;

public class MainExam {

	private static Scanner scan = new Scanner(System.in);

	public void exit() {
		System.out.println("** 프로그램 종료 **");
		System.exit(0);

	}

	public static void main(String[] args) {		
		UserExam uexm = new UserExam();
		String userId;
		while(true)
		{
			System.out.println("---------------------------------------------------------------");
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("다른 숫자 입력시 종료");

			System.out.print("메뉴 선택: ");
			String menuNo = scan.nextLine();
			
			if ("1".equals(menuNo)) {
				userId = uexm.login();
				
				if(userId != "")
				{
					System.out.println("로그인에 성공하셨습니다.");
					break;
				}
				else {
					System.out.println("로그인에 실패하셨습니다.");
				}
			} 
			else if ("2".equals(menuNo)) {
				boolean btrue = uexm.create();
				if(btrue)
					System.out.println("회원가입되셨습니다.");
				else 
					System.out.println("회원가입에 실패하셨습니다.");
			} else 
				return;
		}

		BoardExam bexm = new BoardExam(userId);

		while(true)
		{
			bexm.list();
		}
		
		
	}

}
