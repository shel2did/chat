package multichat;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ServerCom extends Thread{
	HashSet<String> blackList = new HashSet<String>();
	HashSet<String> pWords = new HashSet<String>();
	public void run() {
		while (true) {
			try {
				Scanner select = new Scanner(System.in);
				list();
				int a = select.nextInt();
				
				switch(a) {
				case 1:
					System.out.println("추가할 이름");
					String black = select.next();
					blackList.add(black);
					break;
				case 2:
					System.out.println("추가할 문구");
					String pword = select.next();
					pWords.add(pword);
					break;
				case 3:
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("숫자로 입력하세요");
			}
		}

	}

	public void list() {
		System.out.println("1. 블랙리스트");
		System.out.println("2. 금칙어 설정");
		System.out.println("입력:");
	}
}
