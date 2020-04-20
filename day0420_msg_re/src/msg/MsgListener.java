package msg;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MsgListener {
	//�߾� �����κ��� �޽��� �ޱ� ���� ������
	// DB �� �������� �߰輭�� ����..
	public static void main(String[] args)throws Exception {
		//@SuppressWarnings("resource")
		/*�ڵ� ���������� ��� �־� �ʱ� ���������� �������� close() X*/
		ServerSocket listen = new ServerSocket(8888);
		System.out.println("Waiting .. other massages.. Humm.");

		while(true) {
			try(
					Socket socket = listen.accept();
					Scanner inScanner = new Scanner(socket.getInputStream());
				) {
				//�����κ��� �޾� ����Ѵ�.
				System.out.println("������ ���� �޽��� ����.");
				System.out.println(inScanner.nextLine());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}//end try-catch
		}//end while

	}

}
