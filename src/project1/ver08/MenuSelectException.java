package project1.ver08;

public class MenuSelectException extends Exception {
	public MenuSelectException() {
		System.out.println("1~7사이 숫자로만 입력해주세요");
	}
}