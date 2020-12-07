package project1.ver08;

public class PhoneCompanyInfo extends PhoneInfo {
	String compName;
	public PhoneCompanyInfo(String n, String ph, String com) {
		super(n, ph);
		compName= com;
	}
	public void showPhoneInfo() {
		System.out.println("이름:" + name);
		System.out.println("전화번호:" + phoneNumber);
		System.out.println("회사:"+ compName);
	}

}
