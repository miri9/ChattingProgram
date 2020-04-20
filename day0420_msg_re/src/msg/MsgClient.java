package msg;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MsgClient {
	//bad code
	public static void main(String[] args) throws Exception{
		Scanner keyScanner =  new Scanner(System.in);
		String clientName = "A:"; //보내는이 : 당신의 이름을 입력


		// 전체,귓속말,다시 입력 분기점 (do-while문)
		String msgMode =""; //분기점
		String targetName = ""; // 받는이
		do {
			System.out.println("메시지 종류를 선택하세요. (전체: 1 /귓속말: 2)");
			msgMode = keyScanner.nextLine()+":";
			targetName = "";
			
			if(msgMode.equals("2:")) { // 귓속말
				System.out.print("누구에게 보낼 거에요? ");
				targetName=keyScanner.nextLine() + ":";
				break;
			}
			else if(msgMode.equals("1:")) { // 전체
				System.out.println("전체에게 보냅니다.");
				targetName="전체:";
				break;
			}
			System.out.println("다시 입력해주세요.");
		}while(true);


		System.out.print("내용을 입력하세요. ");
		String content = keyScanner.nextLine() + "\n";
		String msg = msgMode + clientName + targetName + content; // 1:보내는이:받는이:본문

		System.out.println("----------------------------");

		Socket socket = new Socket("SERVER IP ADRESS",8889);//서버로 메시지 출력한다.
		OutputStream out = socket.getOutputStream();

		out.write(msg.getBytes());
		out.flush(); // 버퍼에 남아있는 나머지 내용이 모두 출력되도록 한다. 
		
		//close
		keyScanner.close();
		socket.close();

	}//end of main
}
