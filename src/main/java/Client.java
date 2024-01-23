import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception{
        //expect two command-line arguments
        if(args.length != 2){
            System.out.println("Syntax: Client <serverIP> <serverPort>");
            return;
        }

        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);

        //"GetTime", "GetDate"
        Scanner keyboard = new Scanner(System.in);
        String message = keyboard.nextLine();

        DatagramPacket request = new DatagramPacket(
                message.getBytes(),
                message.getBytes().length
                ,serverIP, serverPort);

        //allows the OS to:
        // - allocate memory buffer
        // - assign a random port number to this socket

        DatagramSocket socket = new DatagramSocket();

        socket.send(request);

        DatagramPacket clientRequest = new DatagramPacket(
                new byte[128],
                128);

        socket.receive(clientRequest);

        byte[] content = Arrays.copyOf(
                clientRequest.getData(),
                clientRequest.getLength());

        String serverMessage = new String(content);

        System.out.println(serverMessage);

    }

}