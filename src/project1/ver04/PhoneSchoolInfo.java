package project1.ver04;

public class PhoneSchoolInfo extends PhoneInfo {
	String major;
	int grade;

	public PhoneSchoolInfo(String n, String ph, String mj, int g) {
		super(n, ph);
		major = mj;
		grade = g;
	}
	public void showPhoneInfo() {
		System.out.println("이름:" + name);
		System.out.println("전화번호:" + phoneNumber);
		System.out.println("전공:" + major);
		System.out.println("학년:" + grade);
	}

}
