package project1.ver09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class PhoneBookManager {
	Connection conn;
	Statement stat;
	ResultSet res;
	PreparedStatement prep;
	public int listCount;

	public PhoneBookManager() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin://@localhost:1521:orcl", "KOSMO", "1234");
			stat = conn.createStatement();
			System.out.println("오라클접속");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		try {
			
			System.out.println("이름:");
			String n = in.next();
			System.out.println("전화번호:");
			String p = in.next();
			System.out.println("생일이 있나요? 1:네 / 다른키:아니오");
			String a = in.next();
			PhoneInfo test;
			if (a.compareTo("1") == 0) {
				System.out.println("생년월일:");
				String b = in.next();
				String sql = "insert into phonebook_tb values "
				+ "('" + n + "', '" + p + "', '" + b + "',seq_phonebook.nextval)";
				prep=conn.prepareStatement(sql);
				int comp = prep.executeUpdate();
				System.out.println(comp + "개 정보 저장");
			} else {
				String sql1 = "insert into phonebook_tb values " 
			+ "('" + n + "', '" + p + "', '입력되지않음',seq_phonebook.nextval)";
				prep=conn.prepareStatement(sql1);
				int comp1 = prep.executeUpdate(sql1);
				System.out.println(comp1 + "개 정보 저장");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("이미 있는 이름은 저장 안돼요!");
		}catch( Exception e) {
			e.printStackTrace();
		}
	}

	public void dataSearch() {
		System.out.println("데이터 검색을 시작합니다..");
		System.out.println("이름:");
		Scanner search = new Scanner(System.in);
		String n = search.next();
		boolean foundIt = false;
		String sql = "select * from phonebook_tb where name like " + "'" + n + "'";
		try {
			res = stat.executeQuery(sql);
			while (res.next()) {
				String name = res.getString("name");
				String phoneNum = res.getString("phonenum");
				String birthday = res.getString("birthday");
				System.out.println("이름:" + name);
				System.out.println("전화번호:" + phoneNum);
				System.out.println("생일:" + birthday);
			}
		} catch (Exception e) {
		}
	}

	public void dataDelete() {
		System.out.println("데이터 삭제를 시작합니다..");
		System.out.println("이름:");
		Scanner search = new Scanner(System.in);
		String find = search.next();
		boolean foundIt = false;

		try {
			stat = conn.createStatement();
			String sql2 = "delete from phonebook_tb where name like '" + find + "'";
			int result = stat.executeUpdate(sql2);

			if (result == 0) {
				System.out.println("일치하는 데이터가 없어 삭제하지 못했습니다.");
			} else {
				System.out.println(result + "개 정보 삭제");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dataAllShow() {
		String sql = "select * from phonebook_tb ";
		boolean savelist = false;
		try {
			res = stat.executeQuery(sql);
			while (res.next()) {
				String name = res.getString("name");
				String phoneNum = res.getString("phonenum");
				String birthday = res.getString("birthday");
				System.out.println("---------------");
				System.out.println("이름:" + name);
				System.out.println("전화번호:" + phoneNum);
				System.out.println("생일:" + birthday);
				savelist = true;

			}
		} catch (Exception e) {
		}
		if(savelist==false) {
			System.out.println("저장된 내용이 없네요");
		}
	}
}
