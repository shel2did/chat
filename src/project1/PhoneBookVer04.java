package project1;

import java.util.Scanner;
import project1.ver04.PhoneInfo;
import project1.ver04.PhoneBookManager;

public class PhoneBookVer04 {
	public static void main(String[] args) {
		PhoneBookManager start = new PhoneBookManager();

		while (true) {
			start.printMenu();
			Scanner sc = new Scanner(System.in);
			int select = sc.nextInt();
			switch (select) {
			case 1:
				start.dataInput();
				break;
			case 2:
				start.dataSearch();
				break;
			case 3:
				start.dataDelete();
				break;
			case 4:
				start.dataAllShow();
				break;
			case 5:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			}
		}
	}
}