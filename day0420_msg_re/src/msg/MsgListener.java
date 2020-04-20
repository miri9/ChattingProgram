package msg;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MsgListener {
	//중앙 서버로부터 메시지 받기 위한 리스너
	// DB 와 웹서버의 중계서버 같은..
	public static void main(String[] args)throws Exception {
		//@SuppressWarnings("resource")
		/*코드 지저분해질 우려 있어 초기 버전에서는 서버소켓 close() X*/
		ServerSocket listen = new ServerSocket(8888);
		System.out.println("Waiting .. other massages.. Humm.");

		while(true) {
			try(
					Socket socket = listen.accept();
					Scanner inScanner = new Scanner(socket.getInputStream());
				) {
				//서버로부터 받아 출력한다.
				System.out.println("서버로 부터 메시지 도착.");
				System.out.println(inScanner.nextLine());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}//end try-catch
		}//end while

	}

}
