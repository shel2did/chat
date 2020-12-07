package project1;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.Writer;

import project1.ver08.PhoneInfo;

import java.io.FileOutputStream;
public class IoTest {

	public static void main(String[] args) {
		try {
			ObjectOutputStream out = 
					new ObjectOutputStream(new FileOutputStream("src/ex20io/PhoneBook.obj"));
			PhoneInfo p = new PhoneInfo("듀띠", "010-5634");
			
			
		} catch (Exception e) {

		}

	}

}
