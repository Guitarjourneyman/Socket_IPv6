import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Socket_IPv6 extends JFrame {
    
    private JTextArea receivedMessagesArea;
    private JTextArea sendMessageArea;
    private JButton sendButton_TCP;
    private JButton receiveButton_TCP;
    private ReceiverViewModel receiver_tcp;
    private SenderViewModel sender_tcp;
    private JTextField inputIp;
    
    public Socket_IPv6() {
        // GUI �⺻ ����
        setTitle("Wifi P2P");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // �޽��� ���� ����
        receivedMessagesArea = new JTextArea();
        receivedMessagesArea.setEditable(false);
        receivedMessagesArea.setLineWrap(true);
        receivedMessagesArea.setWrapStyleWord(true);
        JScrollPane receivedScrollPane = new JScrollPane(receivedMessagesArea);

        // �޽��� ���� ����
        sendMessageArea = new JTextArea(5,50);
        sendMessageArea.setLineWrap(true);
        sendMessageArea.setWrapStyleWord(true);
        JScrollPane sendScrollPane = new JScrollPane(sendMessageArea);

        // ��ư ����
        sendButton_TCP = new JButton("TCP �޽��� ������");
        receiveButton_TCP = new JButton("TCP ���� ���");
        inputIp = new JTextField(15); // IP �Է� �ʵ�
        
        // �г� ���̾ƿ� ����
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(sendButton_TCP);
        buttonPanel.add(receiveButton_TCP);
        buttonPanel.add(inputIp);

        // ���� ���̾ƿ� ����
        setLayout(new BorderLayout());
        add(receivedScrollPane, BorderLayout.CENTER);
        add(sendScrollPane, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);
        
        // ��ư �̺�Ʈ ó��
        sendButton_TCP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sender_tcp = new SenderViewModel();
                String serverIP = inputIp.getText();           
                sender_tcp.startClient(serverIP);
                receivedMessagesArea.append("TCP�� �޽����� ���۵Ǿ����ϴ�.\n");
            }
        });

        receiveButton_TCP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiver_tcp = new ReceiverViewModel();
                new Thread(() -> receiver_tcp.startServer()).start();
                receivedMessagesArea.append("TCP ���� ��� ��...\n");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Socket_IPv6().setVisible(true));
    }
}
