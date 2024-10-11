import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SenderViewModel {
    private static final int PORT = 1995;

    public void startClient(String serverIP) {
        Scanner scanner = new Scanner(System.in);
        
        Socket socket = null;
        PrintWriter out = null;
        
        try {
            // IPv6 �ּҸ� ����Ͽ� Socket ����
            socket = new Socket(serverIP, PORT);
            System.out.println("������ ����Ǿ����ϴ�.");
            
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            
            // �޽��� �ۼ�
            String message = "This is a test";
            String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
            out.println("From Window [" + timeStamp + "] " + message);
            System.out.println("�޽����� ���۵Ǿ����ϴ�: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (socket != null) socket.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
