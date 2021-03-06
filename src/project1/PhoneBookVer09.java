package project1;

import java.util.InputMismatchException;
import java.util.Scanner;
import project1.ver09.PhoneInfo;
import project1.ver09.PhoneBookManager;

public class PhoneBookVer09 {
	public static void main(String[] args) {
		PhoneBookManager start = new PhoneBookManager();
		while (true) {
			try {
				start.printMenu();
				Scanner sc = new Scanner(System.in);
				int select = sc.nextInt();
				if (select <= 5 && select > 0) {
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
				} else {
					System.out.println("1~5까지만 입력해주세요");
				}
			} catch (InputMismatchException e) {
				System.out.println("1~5까지 숫자만 입력해주세요");
			}
		}
	}
}