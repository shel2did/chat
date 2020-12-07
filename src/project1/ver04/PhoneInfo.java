package project1.ver04;


public class PhoneInfo {
	String name;
	String phoneNumber;

	public PhoneInfo(String n, String ph) {
		name = n;
		phoneNumber = ph;
	}

	public void showPhoneInfo() {
		System.out.println("이름:" + name);
		System.out.println("전화번호:" + phoneNumber);
	}

}


