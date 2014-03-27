package es.greuze.sandbox.prisoners;

import java.net.*;

import java.io.*;

public class Police {

    final String HOST = "localhost";
    final int PORT = 5000;

    Socket socket;

    BufferedReader in;
    PrintWriter out;

    public void initialize() {
        // Create socket connection
        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + HOST);
            System.exit(1);
        } catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }

    public String requestAnswer() throws IOException {
        // Send data over socket
        System.out.println("Request next");
        out.println("NEXT");

        // Receive text from prisoner
        String response = in.readLine();
        System.out.println("Text received: '" + response + "'");

        // Send reply to prisoner
        String reply = "FREE";
        System.out.println("Reply : " + reply);
        out.println(reply);

        return response;
    }

    public void requestExit() throws IOException {
        // Send data over socket
        System.out.println("Will request to exit");
        out.println("EXIT");
    }

    public static void main(String[] args) throws Exception {
        Police police = new Police();
        police.initialize();

        for (int i = 0; i < 2; i++) {
            System.out.println("Got answer: '" + police.requestAnswer() + "'");
        }
        police.requestExit();
    }
}
