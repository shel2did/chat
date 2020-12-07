package project1.ver08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Scanner;

public class AutoSaverT extends Thread {
	Scanner in = new Scanner(System.in);
	public HashSet<PhoneInfo> start;
	Connection conn;
	Statement stat;
	ResultSet res;
	PreparedStatement prep;
	public AutoSaverT(HashSet<PhoneInfo> hashSet) throws Exception {
		this.start = hashSet;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin://@localhost:1521:orcl", "KOSMO", "1234");
			stat = conn.createStatement();
			System.out.println("오라클접속");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				sleep(5000);
				save();
				System.out.println("주소록이 텍스트로 자동저장되었습니다.");
			}
		} catch (InterruptedException e) {
		} catch (Exception e) {
		}
	}
	public void save() {
		try {
			FileOutputStream filSt = new FileOutputStream("src/project1/ver08/AutoSaveBook.txt");
			ObjectOutputStream objOut = new ObjectOutputStream(filSt);
			objOut.writeObject(start);
			objOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void load() throws Exception, IOException {
		try {
			FileInputStream load = new FileInputStream("src/project1/ver08/AutoSaveBook.txt");
			ObjectInputStream objIn = new ObjectInputStream(load);
			start = (HashSet<PhoneInfo>) objIn.readObject();
			System.out.println("*이전에 저장되었던 파일을 불러왔어요");
		} catch (FileNotFoundException e) {
			System.out.println("*저장된 파일이 아직 없어요");
		} catch (Exception e) {
			System.out.println("예외");
			e.getStackTrace();
		}
	}

	public int switching(boolean s) {
		System.out.println("1. 끄기/2. 켜기/그외  초기화면");
		Scanner in = new Scanner(System.in);
		int conf = in.nextInt();
		if (conf == 1) {

			if (s == true) {
				s = false;
				System.out.println("자동저장 꺼짐");
				return 2;
			} else {
				System.out.println("이미 꺼져있어요");
				return 0;
			}
		} else if (conf == 2) {
			if (s == true) {
				System.out.println("이미 켜져있어요");
				return 0;
			} else {
				System.out.println("자동저장 켜짐");
				return 1;
			}
		} else {
			System.out.println("처음으로 돌아갑니다");
			return 0;
		}
	}
}
