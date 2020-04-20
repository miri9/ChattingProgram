package msg;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CenterServer2 {
	// ������ �� ������ ��� ���� : Connection refused: connect
	//bad code

	public static void main(String[] args) throws Exception {
		// ä�� �������� �̸�,ip�ּ� ����
		Map<String,String> ipMap = new HashMap<>();
		ipMap.put("A","Your IP ADRESS");

		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(8889);
		System.out.println("READY CENTER SERVER");

		while(true) {
			try { // ���ܷ� while ���� ������ �ʵ��� �����ش�.
				Socket socket = server.accept();
				System.out.println(socket);

				// ���ڿ��� �д´�.
				Scanner inScanner = new Scanner(socket.getInputStream()); 
				String clientMsg = inScanner.nextLine();
				inScanner.close();

				// ���� �ڽſ��� ��� ���� "msgMode1,2:��������:�޴���:����"
				System.out.println(clientMsg);

				String[] arr = clientMsg.split(":");//:�� �����Ͽ� ������
				String msg="["+arr[2]+"] "+arr[1]+"->"+arr[3];// �����ʿ��� ���� �޽���

				// �����ʿ��� ���� ���� "[��ü] ��������->����" : ��ü,�ӼӸ� �б���
				if(arr[0].equals("1")) {
					//��ü
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
					//�ӼӸ� : ��������, �޴��� �����ʿ� �޽����� ��� ����.
					String targetIp = ipMap.get(arr[2]);//�迭 2�� ���� ����� ip�� �������.
					String sendIp = ipMap.get(arr[1]);//�迭 1�� ���� ����� ip�� �������.

					try(
							Socket targetPerson = new Socket(targetIp,8888);
							Socket sendPerson = new Socket(sendIp,8888);
							OutputStream targetPersonOut = targetPerson.getOutputStream();
							OutputStream sendPersonOut = sendPerson.getOutputStream();
							) {

						targetPersonOut.write(("[�ӼӸ�] " +arr[1]+"�����κ��� �޼���  : "+arr[3]+"\n").getBytes());
						sendPersonOut.write(("[�ӼӸ�] " +arr[2]+"�Կ��� �޼���  : "+arr[3]+"\n").getBytes());
					} catch (Exception e2) {
						e2.getMessage();
					}
				} // end if-else��
				
				System.out.println("------------------");

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} // end ��ü try-catch��
		} // end while


	} // end main

} // end class
