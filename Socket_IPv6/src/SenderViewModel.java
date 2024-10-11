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
            // IPv6 주소를 사용하여 Socket 생성
            socket = new Socket(serverIP, PORT);
            System.out.println("서버에 연결되었습니다.");
            
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            
            // 메시지 작성
            String message = "This is a test";
            String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
            out.println("From Window [" + timeStamp + "] " + message);
            System.out.println("메시지가 전송되었습니다: " + message);
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
