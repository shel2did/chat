package project1.ver08;

import java.io.Serializable;

public class PhoneInfo implements SubMenuItem, Serializable{
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


