package msg;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MsgClient {
	//bad code
	public static void main(String[] args) throws Exception{
		Scanner keyScanner =  new Scanner(System.in);
		String clientName = "A:"; //�������� : ����� �̸��� �Է�


		// ��ü,�ӼӸ�,�ٽ� �Է� �б��� (do-while��)
		String msgMode =""; //�б���
		String targetName = ""; // �޴���
		do {
			System.out.println("�޽��� ������ �����ϼ���. (��ü: 1 /�ӼӸ�: 2)");
			msgMode = keyScanner.nextLine()+":";
			targetName = "";
			
			if(msgMode.equals("2:")) { // �ӼӸ�
				System.out.print("�������� ���� �ſ���? ");
				targetName=keyScanner.nextLine() + ":";
				break;
			}
			else if(msgMode.equals("1:")) { // ��ü
				System.out.println("��ü���� �����ϴ�.");
				targetName="��ü:";
				break;
			}
			System.out.println("�ٽ� �Է����ּ���.");
		}while(true);


		System.out.print("������ �Է��ϼ���. ");
		String content = keyScanner.nextLine() + "\n";
		String msg = msgMode + clientName + targetName + content; // 1:��������:�޴���:����

		System.out.println("----------------------------");

		Socket socket = new Socket("SERVER IP ADRESS",8889);//������ �޽��� ����Ѵ�.
		OutputStream out = socket.getOutputStream();

		out.write(msg.getBytes());
		out.flush(); // ���ۿ� �����ִ� ������ ������ ��� ��µǵ��� �Ѵ�. 
		
		//close
		keyScanner.close();
		socket.close();

	}//end of main
}
