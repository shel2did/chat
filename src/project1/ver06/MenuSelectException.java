package project1.ver06;

public class MenuSelectException extends Exception {
	public MenuSelectException() {
		System.out.println("1~5사이 정수만 입력해주세요");
	}
}