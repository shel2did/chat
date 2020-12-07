package project1;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import project1.ver07.PhoneInfo;
import project1.ver07.MenuItem;
import project1.ver07.MenuSelectException;
import project1.ver07.PhoneBookManager;

public class PhoneBookVer07 implements MenuItem {
	public static void main(String[] args) throws MenuSelectException {
		PhoneBookManager start = new PhoneBookManager();
		while (true) {
			try {
				start.printMenu();
				Scanner sc = new Scanner(System.in);
				int select = sc.nextInt();
				if(select>5||select<0) {
					MenuSelectException e= new MenuSelectException();
					throw e;
				}
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
			} catch (InputMismatchException e) {
				System.out.println("정수만 입력해주세요");
			}catch (MenuSelectException e) {
			}
		}
	}
}