package project1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.plaf.SliderUI;
import project1.ver08.AutoSaverT;
import project1.ver08.Game3by3;
import project1.ver08.MenuItem;
import project1.ver08.MenuSelectException;
import project1.ver08.PhoneBookManager;
import project1.ver08.PhoneInfo;

public class PhoneBookVer08 implements Serializable, MenuItem {
	public static void main(String[] args) throws Exception {
		PhoneBookManager start = new PhoneBookManager();
		boolean autosaving;
		Game3by3 game = new Game3by3();
		AutoSaverT auto = new AutoSaverT(start.get());
		auto.load();
		start.set(auto.start);
		auto.setDaemon(true);
		autosaving = false;
		while (true) {
			try {
				start.printMenu();
				Scanner sc = new Scanner(System.in);
				int select = sc.nextInt();
				if (select > 7 || select < 0) {
					MenuSelectException e = new MenuSelectException();
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
				case AutoSave:
					int op = auto.switching(autosaving);
					switch (op) {
					case 1:
						auto = new AutoSaverT(start.get());
						auto.start();
						autosaving = true;
						break;
					case 2:
						auto.interrupt();
						autosaving = false;
						break;
					case 0:
						System.out.println("변경없이 초기화면으로 돌아갑니다");
					}
					break;
				case Game:
					game.game();
					break;
				case Exit:
					System.out.println("프로그램을 종료합니다");
					// 파일저장
					auto.save();
					System.exit(0);
				}
			} catch (InputMismatchException e) {
				System.out.println("1~7사이 숫자로 입력해주세요");
			} catch (MenuSelectException e) {
			}
		}
	}
}
