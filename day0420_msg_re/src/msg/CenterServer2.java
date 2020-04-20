package msg;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CenterServer2 {
	// 리스너 안 떠있을 경우 예외 : Connection refused: connect
	//bad code

	public static void main(String[] args) throws Exception {
		// 채팅 참여자의 이름,ip주소 저장
		Map<String,String> ipMap = new HashMap<>();
		ipMap.put("A","Your IP ADRESS");

		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(8889);
		System.out.println("READY CENTER SERVER");

		while(true) {
			try { // 예외로 while 루프 멈추지 않도록 묶어준다.
				Socket socket = server.accept();
				System.out.println(socket);

				// 문자열로 읽는다.
				Scanner inScanner = new Scanner(socket.getInputStream()); 
				String clientMsg = inScanner.nextLine();
				inScanner.close();

				// 서버 자신에게 띄울 내용 "msgMode1,2:보내는이:받는이:본문"
				System.out.println(clientMsg);

				String[] arr = clientMsg.split(":");//:로 구분하여 끊어줌
				String msg="["+arr[2]+"] "+arr[1]+"->"+arr[3];// 리스너에게 보낼 메시지

				// 리스너에게 보낼 내용 "[전체] 보내는이->본문" : 전체,귓속말 분기점
				if(arr[0].equals("1")) {
					//전체
					ipMap.values().forEach(ip->{
						try (
								Socket allPerson = new Socket(ip,8888);
								OutputStream personOut = allPerson.getOutputStream();
								){
							personOut.write((msg+"\n").getBytes());
						}catch(Exception e) {
							System.out.println(e.getMessage());
						} // end try-catch
					});
				} else {
					//귓속말 : 보내는이, 받는이 리스너에 메시지를 모두 띄운다.
					String targetIp = ipMap.get(arr[2]);//배열 2에 받을 사람의 ip가 담겨있음.
					String sendIp = ipMap.get(arr[1]);//배열 1에 보낼 사람의 ip가 담겨있음.

					try(
							Socket targetPerson = new Socket(targetIp,8888);
							Socket sendPerson = new Socket(sendIp,8888);
							OutputStream targetPersonOut = targetPerson.getOutputStream();
							OutputStream sendPersonOut = sendPerson.getOutputStream();
							) {

						targetPersonOut.write(("[귓속말] " +arr[1]+"님으로부터 메세지  : "+arr[3]+"\n").getBytes());
						sendPersonOut.write(("[귓속말] " +arr[2]+"님에게 메세지  : "+arr[3]+"\n").getBytes());
					} catch (Exception e2) {
						e2.getMessage();
					}
				} // end if-else문
				
				System.out.println("------------------");

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} // end 전체 try-catch문
		} // end while


	} // end main

} // end class
