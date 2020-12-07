package multichat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import oracle.sql.LobPlsqlUtil;

interface a {
	int maxUser = 3;
}

public class MultiServer implements a {
	Connection conn;
	Statement stat;
	ResultSet res;
	PreparedStatement prep;
	// jdbc 연결
	static ServerSocket serverSocket = null;
	static Socket socket = null;
	ServerCom servC = new ServerCom();
	// 클라이언트 정보저장을 위한 Map컬렉션생성
	Map<String, PrintWriter> clientMap;

	// 생성자
	public MultiServer() {
		Room.rList.put("lobby", null);
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin://@localhost:1521:orcl", "KOSMO", "1234");
			stat = conn.createStatement();
			System.out.println("오라클접속");

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 클라이언트의 이름과 출력스트림을 저장할 HashMap 컬렉션생성
		clientMap = new HashMap<String, PrintWriter>();
		// HashMap 동기화설정. 쓰레드가 사용자정보에 동시에 접근하는것을 차단
		Collections.synchronizedMap(clientMap);
	}

	public void init() {
		try {
			/*
			 * 9999번 포트를 설정하여 서버객체를 생성하고 클라이언트의 접속을 대기한다.
			 */
			serverSocket = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
			servC.start();
			while (true) {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + "(클라이언트)의 " + socket.getPort() + " 포트를통해 "
						+ socket.getLocalAddress() + "(서버)의" + socket.getLocalPort() + " 포트로 연결됨");

				/*
				 * 클라이언트의 메세지를 모든 클라이언트에게 전달하기 위한 쓰레드 생성 및 시작. 한명당 하나씩의 쓰레드가 생성된다.
				 */
				Thread mst = new MultiServerT(socket);
				mst.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		MultiServer ms = new MultiServer();
		ms.init();
	}

	// 접속된 모든 클라이언트에게 메세지를 전달하는 메소드
	public void sendAllMsg(String name, String msg, String flag, String sender) {
		// Map에 저장된 객체의 키값(접속자명)을 먼저 얻어온다.
		Iterator<String> it = clientMap.keySet().iterator();
		// 저장된 객체(클라이언트)의 갯수만큼 반복한다.
		while (it.hasNext()) {
			String clientName = it.next();
			try {
				boolean blocked = false;
				try {
					String findblock = "select * from blocklist ";
					res = stat.executeQuery(findblock);
					while (res.next()) {
						String regname = res.getString("name");
						String bname = res.getString("block");
						if (regname.equals(clientName) && bname.equals(name)) {
							blocked = true;
						}
					}
				} catch (Exception e) {
				}
				if (blocked == false) {
					if (Room.rUser.get(clientName).equals(Room.rUser.get(sender))) {

						// HashSet의 키값이터레이터 순서대로 String에 저장
						// 각 클라이언트의 PrintWriter객체를 얻어온다.

						PrintWriter it_out = (PrintWriter) clientMap.get(clientName);
						if (flag.equals("One")) {
							if (name.equals(clientName)) {
								it_out.println("[귓속말]" + sender + ":" + URLEncoder.encode(msg, "UTF-8"));
							}
						} else {
							// 그 외에는 모든 클라이언트에게 전송
							if (name.equals("")) {
								// 접속, 퇴장에서 사용되는 부분
								it_out.println(URLEncoder.encode(msg, "UTF-8"));
							} else {
								// 메세지를 보낼때 사용되는 부분
								it_out.println("[" + name + "] " + URLEncoder.encode(msg, "UTF-8"));
							}
						}
					}
				} else {
					System.out.println(name + "의 말 차단됨");
					break;
				}
				String sqlall = "insert into chat_talking values ( seq_chat.nextval, '" + name + "', '" + msg
						+ "',sysdate)";
				prep = conn.prepareStatement(sqlall);
				int save = prep.executeUpdate();
				prep.close();
			} catch (StringIndexOutOfBoundsException e) {
			} catch (Exception e) {
				System.out.println("예외:" + e);
				continue;
			}
		}
	}

	// 내부클래스
	class MultiServerT extends Thread {
		Socket socket;
		PrintWriter out = null;
		BufferedReader in = null;

		// 생성자 : socket을 기반으로 입출력 스트림 생성
		public MultiServerT(Socket socket) {
			this.socket = socket;
			try {
				out = new PrintWriter(this.socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
			} catch (Exception e) {
				System.out.println("예외:" + e);
			}
		}

		public void shut() {
			out.println("Q를눌러 종료");
			socket = null;
			in = null;
			out = null;
		}

		@Override
		public void run() {

			// HashSet의 키값이터레이터 순서대로 String에 저장
			// 각 클라이언트의 PrintWriter객체를 얻어온다.
			boolean fix = false;
			boolean send = true;
			String fixto = "";
			String s = "";
			String name = "";
			try {
				if (clientMap.size() == maxUser) {
					out.println("정원초과");
					shut();

				}

				// 클라이언트의 이름을 읽어와서 저장
				while (true) {

					String login = in.readLine();
					login = URLDecoder.decode(login, "UTF-8");

					Iterator<String> it = clientMap.keySet().iterator();
					boolean jb = false;
					while (it.hasNext()) {
						if (it.next().equals(login)) {
							out.println(login + "은 이미 사용중인 이름입니다.");
							out.println("이름입력:");
							jb = true;
						}
					}
					if (jb == false) {

						name = login;
						break;
					}
				}
				for (String black : servC.blackList) {
					if (black.equals(name)) {
						out.println("블랙리스트로 차단되었습니다.");
						shut();
					}
				}
				clientMap.put(name, out);
				sendAllMsg("", name + "님이 입장하셨습니다.", "All", "");
				System.out.println(name + " 접속");
				System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");

				Room.rUser.put(name, "lobby");// 처음접속시 로비에 넣어버림
				boolean a = Room.rUser.isEmpty();
				System.out.println(a);
				while (true) {

					s = in.readLine();
					s = URLDecoder.decode(s, "UTF-8");
					System.out.println(name + " >> " + s);
					for (String pword : servC.pWords) {
						if (s.contains(pword)) {
							out.println("금지어가 포함되어있습니다.>" + pword);
							send = false;

						}
					}
					if (send == true) {
						if (s.charAt(0) == '/') {
							// 만약 /로 시작한다면 명령어이다.
							/*
							 * 귓속말은 아래와 같이 전송하게된다. -> /to 대화명 대화내용 : 따라서 split()을 통해 space로 문자열을 분리한다.
							 */
							String[] strArr = s.split(" ");
							/*
							 * 대화내용에 스페이스가 있는 경우 문장의 끝까지를 출력해야 하므로 배열의 크기만큼 반복하면서 문자열을 이어준다.
							 */
							String msgContent = "";
							for (int i = 2; i < strArr.length; i++)
								msgContent += strArr[i] + " ";
							if (strArr[0].equals("/fixto")) { // 귓말고정
								fix = true;
								fixto = strArr[1];
								out.println("[" + fixto + "]로 귓속말 고정");
							} else if (strArr[0].equals("/unfixto")) { // 귓말해제
								fix = false;
								out.println("귓속말 고정 해제");
							} else if (strArr[0].equals("/block")) { // 차단하기
								System.out.println(name + "이 " + strArr[1] + "를 차단함");
								out.println(strArr[1] + "를 차단함");
								String block = "insert into blocklist VALUES ('" + name + "','" + strArr[1] + "')";
								prep = conn.prepareStatement(block);
								int blockQ = prep.executeUpdate();
								prep.close();
							} else if (strArr[0].equals("/unblock")) { // 차단해제
								System.out.println(name + "이 " + strArr[1] + "를 차단해제함");
								out.println(strArr[1] + "를 차단 해제함");
								String unblock = "delete from blocklist where name like '" + name + "' and block like '"
										+ strArr[1] + "'";
								prep = conn.prepareStatement(unblock);
								int unblockQ = prep.executeUpdate();
								prep.close();

							} else if (strArr[0].equals("/roomenter")) { // 방들어가기
								Room.enter(name, strArr[1], in, out);
							} else if (strArr[0].equals("/roomout")) { // 방나가기
								Room.out(name, out);
							} else if (strArr[0].equals("/roomlist")) { // 방나가기
								Room.roomList(out);
							} else if (strArr[0].equals("/list")) { // 방나가기
								Room.userList(name, out);
							} else if (strArr[0].equals("/makeroom")) { // 방만들기
								if (strArr.length < 4) {
									Room.makeRoom(strArr[1], strArr[2], out);
								} else {

								}
								Room.makeRoom(strArr[1], strArr[2], strArr[3], out);
							} else if (strArr[0].equals("/to")) {
								sendAllMsg(strArr[1], msgContent, "One", name);
							} else {
								out.println("잘못된 명령어에요");
							}
						} else {
							if (fix) {
								sendAllMsg(fixto, s, "One", name);
							} else {
								sendAllMsg(name, s, "All", name);
							}
						}

					}

				}
			} catch (Exception e) {
			} finally {
				/*
				 * 클라이언트가 접속을 종료하면 Socket예외가 발생하게 되어 finally절로 진입하게된다. 이때 "대화명"을 통해 정보를 삭제한다.
				 */
				clientMap.remove(name);
				if (socket.isClosed()) {
					System.out.println("1명 정원초과로 입장실패");
				} else {
					sendAllMsg("", name + "님이 퇴장하셨습니다.", "All", name);
				}

				// 퇴장하는 클라이언트의 쓰레드명을 보여준다.
				System.out.println(name + " [" + Thread.currentThread().getName() + "] 퇴장");
				System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");

				try {
					in.close();
					out.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
