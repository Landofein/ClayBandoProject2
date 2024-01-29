import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ClientNist {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Syntax: TimeClient <serverIP> <serverPort>");
            return;
        }

        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);


        DatagramSocket socket = new DatagramSocket();
        DatagramPacket request = new DatagramPacket(new byte[0], 0, serverIP, serverPort);
        socket.send(request);


        DatagramPacket response = new DatagramPacket(new byte[4], 4);
        socket.receive(response);


        long secondsSince1900 = ByteBuffer.wrap(response.getData()).getInt() & 0xFFFFFFFFL;


        ZonedDateTime dateTime = Instant.ofEpochSecond(secondsSince1900 - 2208988800L)
                .atZone(ZoneId.of("America/New_York"));


        System.out.println("Current Date & Time (Eastern Time Zone): " + dateTime);


        socket.close();
    }
}
