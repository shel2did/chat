package project1;

import java.util.Scanner;

import project1.ver02.PhoneInfo;

public class PhoneBookVer02 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("이름:");
		String n = in.next();
		System.out.println("전화번호:");
		String p = in.next();
		System.out.println("생일이 있나요? 1:네 / 다른키:아니오");
		int a = in.nextInt();
		System.out.println(a);
		if (a ==1) {
			System.out.println("생년월일:");
			String b = in.next();
			PhoneInfo test = new PhoneInfo(n, p, b);
		} else {
			PhoneInfo test = new PhoneInfo(n, p);
		}
	}
}