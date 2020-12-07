package project1.ver08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class PhoneBookManager implements SubMenuItem {
	HashSet<PhoneInfo> list = new HashSet<PhoneInfo>();
	public int listCount;
	Scanner in = new Scanner(System.in);

	public PhoneBookManager() {
		listCount = 0;
	}

	public HashSet<PhoneInfo> get() {
		return list;
	}

	public void set(HashSet<PhoneInfo> load) {
		list = load;
	}

	public void printMenu() {
		System.out.println("=================메뉴를 선택하세요=================");
		System.out.println("1.주소록입력 2.검색 3.삭제 4.출력 5.저장옵션 6.3by3 7.종료");
		System.out.println("==============================================");
		System.out.println("메뉴선택:");
	}

	public void dataInput() {

		while (true) {
			System.out.println("1.일반, 2.동창, 3.직장동료");
			System.out.print(">>선택");
			int a = in.nextInt();
			if (a != 1 && a != 2 && a != 3) {
				System.out.println("1~3중에 고르세요");
				continue;
			} else {
				switch (a) {
				case commonAdd:
					System.out.println("이름:");
					String n = in.next();
					System.out.println("전화번호:");
					String p = in.next();
					PhoneInfo common = new PhoneInfo(n, p);
					overwrite(common);
					list.add(common);
					System.out.println("===입력이완료되었습니다===");
					common.showPhoneInfo();
					break;
				case SchoolAdd:
					System.out.println("이름:");
					String n1 = in.next();
					System.out.println("전화번호:");
					String p1 = in.next();
					System.out.println("전공:");
					String mj = in.next();
					System.out.println("학년:");
					int g;
					while(true) {
						try{
							g = in.nextInt();
							break;
						}
						catch(InputMismatchException e) {
							System.out.println("숫자를 입력해주세요");
							String enter = in.nextLine();
							continue;
						}
					}
					PhoneSchoolInfo school = new PhoneSchoolInfo(n1, p1, mj, g);
					overwrite(school);
					list.add(school);
					System.out.println("===입력이완료되었습니다===");
					school.showPhoneInfo();
					break;
				case CompanyAdd:
					System.out.println("이름:");
					String n2 = in.next();
					System.out.println("전화번호:");
					String p2 = in.next();
					System.out.println("회사:");
					String c = in.next();
					PhoneInfo com = new PhoneCompanyInfo(n2, p2, c);
					overwrite(com);
					list.add(com);
					System.out.println("===입력이완료되었습니다===");
					com.showPhoneInfo();
					break;
				}
				break;
			}
		}
	}

	public void overwrite(PhoneInfo a) {
		boolean foundIt = list.add(a);
		if (foundIt == false) {
			System.out.println("이미 저장된 데이터입니다. 덮어쓸까요? Y(y) / N(n)");
			String sel = in.next();
			if (sel.equals("y") || sel.equals("Y")) {
				list.remove(a);
				System.out.println("입력한 정보가 저장되었습니다.");
			} else if (sel.equals("n") || sel.equals("N")) {
				System.out.println("저장하지 않았습니다.");
			}
		}
	}

	public void dataSearch() {
		System.out.println("데이터 검색을 시작합니다..");
		System.out.println("이름:");
		Scanner search = new Scanner(System.in);
		String find = search.next();
		boolean foundIt = false;
		Iterator<PhoneInfo> it = list.iterator();
		while (it.hasNext()) {
			PhoneInfo phone = it.next();
			if (phone.name.equals(find) == true) {
				foundIt = true;
				System.out.println("데이터 검색이 완료되었습니다.");
				phone.showPhoneInfo();
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
		Iterator<PhoneInfo> it = list.iterator();
		while (it.hasNext()) {
			PhoneInfo p = it.next();
			if (p.name.equals(find)) {
				it.remove();
				foundIt = true;
			}
		}
		if (foundIt == false) {
			System.out.println("일치하는 데이터가 없어 삭제하지 못했습니다.");
		} else {
			System.out.println("데이터 삭제가 완료되었습니다.");
		}
	}

	public void dataAllShow() {

		System.out.println("=====주소록을 출력함=====");
		for (PhoneInfo a : list) {
			a.showPhoneInfo();
			System.out.println();
		}
		if (list.isEmpty()) {
			System.out.println("저장된 내용이 없어요");
		} else {
			System.out.println("==주소록 출력이 완료되었습니다==");
		}
	}
}
