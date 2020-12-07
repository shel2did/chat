package project1.ver09;


public class PhoneInfo {
	String name;
	String phoneNumber;
	String birthday;

	public PhoneInfo(String n, String ph) {
		name = n;
		phoneNumber = ph;
		showPhoneInfo();
	}

	public PhoneInfo(String n, String ph, String bir) {
		name = n;
		phoneNumber = ph;
		birthday = bir;
		showPhoneInfo();
	}

	public void showPhoneInfo() {
		if (birthday == null) {
			birthday = "입력되지않음";
		}
		System.out.println("이름:" + name);
		System.out.println("전화번호:" + phoneNumber);
		System.out.println("생년월일:" + birthday);
	}

}


