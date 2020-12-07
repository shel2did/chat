package project1.ver03;

import java.util.Scanner;

public class PhoneBookManager {
	PhoneInfo[] list = new PhoneInfo[100];
	public int listCount;

	public PhoneBookManager() {
		listCount = 0;
	}

	public void printMenu() {
		System.out.println("============");
		System.out.println("선택하세요");
		System.out.println("1.데이터입력");
		System.out.println("2.데이터검색");
		System.out.println("3.데이터삭제");
		System.out.println("4.주소록 출력");
		System.out.println("5.프로그램 종료");
		System.out.println("============");
		System.out.println("선택:");
	}

	public void dataInput() {
		Scanner in = new Scanner(System.in);
		System.out.println("이름:");
		String n = in.next();
		System.out.println("전화번호:");
		String p = in.next();
		System.out.println("생일이 있나요? 1:네 / 다른키:아니오");
		String a = in.next();
		System.out.println(a);
		PhoneInfo test;
		if (a.compareTo("1") == 0) {
			System.out.println("생년월일:");
			String b = in.next();
			test = new PhoneInfo(n, p, b);
		} else {
			test = new PhoneInfo(n, p);
		}
		list[listCount] = test;
		listCount++;

	}

	public void dataSearch() {
		System.out.println("데이터 검색을 시작합니다..");
		System.out.println("이름:");
		Scanner search = new Scanner(System.in);
		String find = search.next();
		boolean foundIt = false;
		for (int i = 0; i < listCount; i++) {

			if (find.compareTo(list[i].name) == 0) {
				System.out.println("이름:" + list[i].name);
				System.out.println("전화번호:" + list[i].phoneNumber);
				System.out.println("생년월일:" + list[i].birthday);
				System.out.println("데이터 검색이 완료되었습니다.");
				foundIt = true;
			}
		}
		if (foundIt == false) {
			System.out.println("찾는사람이 없어요!");
		}
	}

	public void dataDelete() {
		System.out.println("데이터 삭제를 시작합니다..");
		System.out.println("이름:");
		Scanner search = new Scanner(System.in);
		String find = search.next();
		boolean foundIt = false;
		for (int i = 0; i < listCount; i++) {
			if (find.compareTo(list[i].name) == 0) {
				for (int j = i; j < listCount; j++) {
					list[j] = null;
					list[j] = list[j + 1];
				}
				listCount--;
				foundIt = true;
				System.out.println("데이터 삭제가 완료되었습니다.");
			} 
		}
		if(foundIt==false) {
			System.out.println("일치하는 데이터가 없어 삭제하지 못했습니다.");
		}
	}

	public void dataAllShow() {
		if (list[0] == null) {
			System.out.println("저장된 정보가 없습니다.");
		} else {
			for (int a = 0; a < listCount; a++) {
				list[a].showPhoneInfo();
			}
		}
	}
}
