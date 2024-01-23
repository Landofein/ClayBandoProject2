import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws Exception{
        //expect one command-line argument from the admin
        if (args.length !=1 ) {
            System.out.println("Syntax: Server <serverPort>");
            return;
        }
        int port = Integer.parseInt(args[0]);

        //request the OS to assign port (i.e., the argument)
        // to this socket
        DatagramSocket socket = new DatagramSocket(port);

        //create the empty container for receiving client request
        DatagramPacket clientRequest = new DatagramPacket(
                new byte[128],
                128);

        socket.receive(clientRequest);

        byte[] content = Arrays.copyOf(
                clientRequest.getData(),
                clientRequest.getLength());

        String clientMessage = new String(content);

        System.out.println(clientMessage);

        String message;
        if(clientMessage.equalsIgnoreCase("GetTime")){
            LocalDateTime now = LocalDateTime.now();
            message = now.toLocalTime().toString();

        }else if (clientMessage.equalsIgnoreCase("GetDate")){
            LocalDateTime now = LocalDateTime.now();
            message = now.toLocalDate().toString();
        }else{
            //must be an invalid query
            message = "Query should be either GetTime or DetDate.";
        }
        DatagramPacket request = new DatagramPacket(
                message.getBytes(),
                message.getBytes().length,
                clientRequest.getAddress(),
                clientRequest.getPort()
        );
        socket.send(request);

    }
}