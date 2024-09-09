import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class BroadcastReceiver {
    public static final int PORT = 15875; // 与发送器使用的端口号相同

    public static void main(String[] args) {
        try {
            // 创建DatagramSocket并监听指定端口
            DatagramSocket socket = new DatagramSocket(PORT);
            System.out.println("Waiting to receive broadcast message...");
            byte[] buffer = new byte[1024];
            // 创建DatagramPacket准备接收数据
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true){
                // 接收数据
                socket.receive(packet);
                // 解析并打印接收到的消息
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Broadcast message received: " + receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}