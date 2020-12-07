package project1.ver07;


public class PhoneInfo implements SubMenuItem{
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
	@Override
	public int hashCode() {
		int hc1 = name.hashCode();
		return hc1;
	}
	@Override
	public boolean equals(Object obj) {
		PhoneInfo p = (PhoneInfo)obj;
		if(p.name.equals(this.name)) {
			return true;
		}
		else {
			return false;
		}
	}

}


