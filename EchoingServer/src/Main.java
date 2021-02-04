import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        int port = 5000;

        // Create server socket that listens on port 5000.
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at port " + port);
            // Because this is an infinite loop, the server will only terminate when manually terminated.
            while (true) {
                // Listens for and accepts connections to the socket at the specified port.
                new Echo(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("ERROR server IOException: " + e.getMessage());
        }
    }
}
