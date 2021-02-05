import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Echo extends Thread {
    private Socket socket;

    public Echo(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // The server uses input streams and output streams to receive and send input from/to the client
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Infinite loop that continues responding to client messages and breaks when the client writes "exit"
            while(true) {
                String echoString = clientInput.readLine();
                System.out.println("Received message: " + echoString);
                if (echoString.toLowerCase().equals("exit")) {
                    output.println("Goodbye.");
                    break;
                }
                output.println("I got your message: " + echoString);
            }
        } catch (IOException e) {
            System.out.println("ERROR IOException: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("ERROR IOException on closing socket: " + e.getMessage());
            }
        }
    }
}
