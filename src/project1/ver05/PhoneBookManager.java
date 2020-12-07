package project1.ver05;

import java.util.Scanner;

public class PhoneBookManager implements SubMenuItem {
	PhoneInfo [] list = new PhoneInfo[100];
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
		System.out.println("1.일반, 2.대학, 3.회사");
		int a = in.nextInt();

		switch (a) {
		case commonAdd:
			System.out.println("이름:");
			String n = in.next();
			System.out.println("전화번호:");
			String p = in.next();
			PhoneInfo add = new PhoneInfo(n, p);
			list[listCount] = add;
			listCount++;
			break;
		case SchoolAdd:
			System.out.println("이름:");
			String n1 = in.next();
			System.out.println("전화번호:");
			String p1 = in.next();
			System.out.println("전공:");
			String mj = in.next();
			System.out.println("학년:");
			int g = in.nextInt();
			PhoneSchoolInfo school = new PhoneSchoolInfo(n1, p1, mj, g);
			list[listCount] = school;
			listCount++;
			break;
		case CompanyAdd:
			System.out.println("이름:");
			String n2 = in.next();
			System.out.println("전화번호:");
			String p2 = in.next();
			System.out.println("회사:");
			String c = in.next();
			PhoneInfo com = new PhoneCompanyInfo(n2, p2, c);
			list[listCount] = com;
			listCount++;
			break;
		}
		dataAllShow();
	}

	public void dataSearch() {
		System.out.println("데이터 검색을 시작합니다..");
		System.out.println("이름:");
		Scanner search = new Scanner(System.in);
		String find = search.next();
		boolean foundIt = false;
		for (int i = 0; i < listCount; i++) {

			if (find.compareTo(list[i].name) == 0) {
				list[i].showPhoneInfo();
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
		if (foundIt == false) {
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
