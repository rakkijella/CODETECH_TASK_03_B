import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        System.out.println("Connected to the chat server");

        new Thread(new ReadHandler(socket)).start();
        new Thread(new WriteHandler(socket)).start();
    }

    static class ReadHandler implements Runnable {
        private BufferedReader in;

        public ReadHandler(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }
    }

    static class WriteHandler implements Runnable {
        private PrintWriter out;
        private BufferedReader console;

        public WriteHandler(Socket socket) throws IOException {
            out = new PrintWriter(socket.getOutputStream(), true);
            console = new BufferedReader(new InputStreamReader(System.in));
        }

        public void run() {
            try {
                String message;
                while ((message = console.readLine()) != null) {
                    out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Error sending message.");
            }
        }
    }
}
