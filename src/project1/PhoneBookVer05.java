package project1;

import java.util.Scanner;
import project1.ver05.PhoneInfo;
import project1.ver05.MenuItem;
import project1.ver05.PhoneBookManager;

public class PhoneBookVer05 implements MenuItem {
	public static void main(String[] args) {
		PhoneBookManager start = new PhoneBookManager();

		while (true) {
			start.printMenu();
			Scanner sc = new Scanner(System.in);
			int select = sc.nextInt();
			switch (select) {
			case Input:
				start.dataInput();
				break;
			case Search:
				start.dataSearch();
				break;
			case Delete:
				start.dataDelete();
				break;
			case AllShow:
				start.dataAllShow();
				break;
			case Exit:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			}
		}
	}
}