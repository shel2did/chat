package project1.ver08;

import java.util.Random;
import java.util.Scanner;

public class Game3by3 {
	int x = 2, y = 2;
	int calX = x, calY = y;
	int mixCcount = 0;
	boolean result = true;
	char[][] deftable = new char[][] { { '1', '2', '3' }, { '4', '5', '6' }, { '7', '8', 'x' } };
	char[][] table = new char[][] { { '1', '2', '3' }, { '4', '5', '6' }, { '7', '8', 'x' } };
	boolean gameout = false;

	public void game() {
		while (true) {
			start();
			System.out.println("재시작 하시겠습니까?(y 누르면 재시작, 나머지는 종료)");
			Scanner restart = new Scanner(System.in);
			String res = restart.next();
			if (res.equals("y")) {
				System.out.println("게임재시작");
				gameout = false;
			} else {
				gameout = true;
				break;
			}
		}
		System.out.println("게임종료");
	}

	public void start() {
		Scanner in = new Scanner(System.in);
		int end = 0;
		mixTable();
		System.out.println("=w-위쪽 a-왼쪽 s-아래쪽 d-오른쪽 x-게임종료=");
		showTable(table);
		while (end != 9) {
			end = 0;
			String select = in.next();
			char s = select.charAt(0);
			if ((s == 'a') || (s == 's') || (s == 'd') || (s == 'w') || (s == 'x')) {
				System.out.println("선택한 키:" + s);
				if (s == 'x') {
					gameout = true;
					break;
				} else {
					move(s);
					showTable(table);
					if (result == false) {
						calX = x;
						calY = y;
						System.out.println("====그쪽으로는 못가요====");
					}
				}
				if (gameout == true) {
					break;
				}
			} else {
				System.out.println(select + "선택 w,a,s,d,x중 고르세요");
			}
			for (int a = 0; a <= 2; a++) {
				for (int b = 0; b <= 2; b++) {
					if (table[a][b] == deftable[a][b]) {
						end++;
					}
				}
			}
			if (gameout == true) {
				break;
			}
		}
		if (end == 9) {
			System.out.println("퍼즐완성!");
		}
	}

	public void move(char select) {
		// w-위쪽 a-왼쪽 s-아래쪽 d-오른쪽 x-게임종료
		calX = x;
		calY = y;
		result = true;

		switch (select) {
		case 'w':
			calX--;
			if (calX < 0 || calX > 2) {
				result = false;
			} else {
				table[x][y] = table[calX][y];
				x = calX;
				table[calX][y] = 'x';
				mixCcount++;
			}
			break;
		case 'a':
			calY--;
			if (calY < 0 || calY > 2) {
				result = false;
			} else {
				table[x][y] = table[x][calY];
				y = calY;
				table[x][calY] = 'x';
				mixCcount++;
			}
			break;
		case 's':
			calX++;
			if (calX < 0 || calX > 2) {
				result = false;
			} else {
				table[x][y] = table[calX][y];
				x = calX;
				table[calX][y] = 'x';
				mixCcount++;
			}
			break;
		case 'd':
			calY++;
			if (calY < 0 || calY > 2) {
				result = false;
			} else {
				table[x][y] = table[x][calY];
				y = calY;
				table[x][calY] = 'x';
				mixCcount++;
			}
		}
	}

	public void mixTable() {
		mixCcount = 0;
		char sel[] = new char[] { 'w', 'a', 's', 'd' };
		Random ran = new Random();
		while (mixCcount <= 6) {
			char o = sel[ran.nextInt(4)];
			move(o);
			if (result = false) {
				calX = x;
				calY = y;
			}
		}
	}

	public void showTable(char[][] table) {
		System.out.printf("%c  %c  %c\n\n", table[0][0], table[0][1], table[0][2]);
		System.out.printf("%c  %c  %c\n\n", table[1][0], table[1][1], table[1][2]);
		System.out.printf("%c  %c  %c\n\n", table[2][0], table[2][1], table[2][2]);
	}

}
