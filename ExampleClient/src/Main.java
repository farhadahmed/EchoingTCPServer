import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            // timeout after 5 seconds
            socket.setSoTimeout(5000);

            // Use PrintWriter to create output stream to send server and use BufferedReader to read the stream coming from server
            PrintWriter outputStreamToSendServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader serverResponseStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner s = new Scanner(System.in);
            String outputMessage, response;

            // Until user enters "exit", send message to server on port 5000
            do {
                System.out.println("Enter a message: ");
                outputMessage = s.nextLine();
                outputStreamToSendServer.println(outputMessage);

                if (!outputMessage.toLowerCase().equals("exit")) {
                    response = serverResponseStream.readLine();
                    System.out.println(response);
                }
            } while (!outputMessage.equals("exit"));

            // If user enters exit, grab response stream and print.
            response = serverResponseStream.readLine();
            System.out.println(response);
        } catch (SocketTimeoutException e) {
            System.out.println("ERROR socket timeout: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERROR connecting to server: " + e.getMessage());
        }
    }
}
