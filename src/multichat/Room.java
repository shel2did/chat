package multichat;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.function.ToIntBiFunction;

public class Room extends Thread {
	static Scanner in = new Scanner(System.in);
	final static String l = "lobby";
	static HashMap<String, Integer> rList = new HashMap<String, Integer>();
	static HashMap<String, String> rUser = new HashMap<String, String>();
	static HashMap<String, Integer> size = new HashMap<String, Integer>();
	static Iterator<String> listIt;
	static Iterator<String> userIt;
	static Iterator<String> sizeIt;
	
	public Room() {
		listIt = rList.keySet().iterator();
		
		sizeIt = size.keySet().iterator();
		rList.put("lobby", null);
	}

	public static void roomList(PrintWriter out) {
		try {
			out.println("========방목록========");
			while (listIt.hasNext()) {
				String rooml = listIt.next();
				if (rList.get(rooml) != null) {
					out.println("[비공개] " + rooml);
				} else {
					out.println(rooml);
				}
			}
			out.println("====================");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("룸리스트 예외");

		}
	}

	public static void userList(String name, PrintWriter out) {
		try {
			out.println("======참가자 목록======");
			userIt = rUser.keySet().iterator();
			while (userIt.hasNext()) {
				String user = userIt.next();
				System.out.println(user);
				if (rUser.get(user).equals(rUser.get(name))&&(user!=name)) {
					out.println(user);
				}
			}
			out.println("====================");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("유저리스트 예외");

		}

	}

	public static void enter(String n, String r, BufferedReader in, PrintWriter out) {
		if (rUser.get(n).equals(l) == false) {
			out.println("로비에서 참가할수있어요");
		} else {
			if (rList.containsKey(r)) {
				try {
					if (rList.get(r) == null) { // 비밀번호 없는경우
						if (size.get(r) == rUser.size()) {
							out.println("정원초과");
						} else {
							rUser.remove(n);
							rUser.put(n, r);
							userList(n, out);
						}
					} else {
						out.println("비밀번호를 입력하세요");
						int passin = in.read();
						if (rList.get(r).equals(passin)) {
							rUser.remove(n);
							rUser.put(n, r);
						} else {
							out.println("비밀번호가 틀립니다");
						}

					}
				} catch (Exception e) {
					System.out.println("enter예외:");
					e.printStackTrace();
				}
			} else {
				out.println("그런방은 없어요");
			}
		}
	}

	public static void out(String n, PrintWriter out) {

		if (rUser.get(n) == l) {
			out.println("이미 로비에 있어요");
		} else {
			out.println("방을나갑니다.");
			rUser.remove(n);
			rUser.put(n, l);
			roomList(out);
		}
	}

	public static void makeRoom(String rname, String num, PrintWriter out) {
		try {
			if (rList.containsKey(rname)) {
				out.println("이미 그 제목은 쓰고 있어요");
			} else {
				int rsize = Integer.parseInt(num);
				rList.put(rname, null);
				size.put(rname, rsize);
				out.println(rname + "방이 생성되었습니다.");
			}

		} catch (Exception e) {
			System.out.println("makeRoom예외:");
			e.printStackTrace();
		}
	}

	public static void makeRoom(String rname, String num, String pass, PrintWriter out) {
		if (rList.containsKey(rname)) {
			out.println("이미 그 제목은 쓰고 있어요");
		} else {
			int rsize = Integer.parseInt(num);
			int password = Integer.parseInt(pass);
			rList.put(rname, password);
			size.put(rname, rsize);
			out.println(rname + "방이 생성되었습니다.");
		}
	}

}

//if(Room.rList.get(name).equals("lobby")){if(Room.rList.get(strArr[1])!=null){out.println("비밀번호를 입력하세요.");
//
//}else{
//
//}}else{out.println("퇴장후 방에 참가합니다.");Room.enter();}