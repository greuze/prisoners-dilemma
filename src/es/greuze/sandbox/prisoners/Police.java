package es.greuze.sandbox.prisoners;

import java.net.*;

import java.io.*;

public class Police {

    final String HOST = "localhost";
    final int PORT = 5000;

    Socket socket;
    InputStream in;
    OutputStream out;

    public void initialize() {
        // Create socket connection
        try {
            socket = new Socket(HOST, PORT);
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + HOST);
            System.exit(1);
        } catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }

    public char requestAnswer() throws IOException {
        // Send data over socket
        System.out.println("Will request next");
        out.write('n');

        // Receive text from prisoner
        char response = (char) in.read();
        System.out.println("Text received: '" + response + "'");

        // Send reply to prisoner
        char reply = 'f'; // TODO: f=free
        System.out.println("Reply : " + reply);
        out.write(reply);

        return response;
    }

    public void requestExit() throws IOException {
        // Send data over socket
        System.out.println("Will request to exit");
        out.write('x');
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
