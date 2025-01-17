import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiverViewModel {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private static final int PORT = 1995;

    public void startServer() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            // IPv6 주소를 위한 ServerSocket 생성
            serverSocket = new ServerSocket(PORT, 5, InetAddress.getByName("::"));
            System.out.println("연결 대기 중...");
            socket = serverSocket.accept();
            System.out.println("기기와 연결되었습니다.");

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            String clientIP = socket.getInetAddress().getHostAddress();
            
            try {
                Object receivedObject = in.readObject();
                if (receivedObject instanceof String) {
                    String receivedMessage = (String) receivedObject;

                    String truncatedMessage = receivedMessage.length() > 20
                            ? receivedMessage.substring(0, 20) + "..."
                            : receivedMessage;
                    // 현재 시간을 hh:mm:ss.SSS 형식으로 가져오기
                    String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
                    
                    System.out.println("수신된 메시지 from " + clientIP + ": " + truncatedMessage + " [" + timeStamp + "]");
                    // 클라이언트로 수신 확인 메시지 전송
                    String acknowledgmentMessage = "Window에서 메시지를 받았습니다.";
                    out.writeObject(acknowledgmentMessage);
                    out.flush();
                }
            } catch (EOFException e) {
                System.out.println("클라이언트 연결이 종료되었습니다.");
            } catch (SocketException e) {
                System.out.println("연결이 리셋되었습니다: " + e.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (BindException e) {
            System.out.println("포트가 이미 사용 중입니다: " + e.getMessage());
            try {
                Thread.sleep(1000); // 1초 대기
                serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName("::")); // 다시 시도
            } catch (InterruptedException | IOException ie) {
                ie.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null && !socket.isClosed()) socket.close();
                if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}