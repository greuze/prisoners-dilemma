package es.greuze.sandbox.prisoners;

import java.net.*;

import java.io.*;

public class Prisoner {

    final int PORT = 5000;

    ServerSocket server;
    Socket client;
    InputStream in = null;
    OutputStream out = null;

    public void initialize() {
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Could not listen on port " + PORT);
            System.exit(-1);
        }

        try {
            System.out.println("Waiting...");
            client = server.accept();
        } catch (IOException e) {
            System.out.println("Accept failed: " + PORT);
            System.exit(-1);
        }

        try {
            in = client.getInputStream();
            out = client.getOutputStream();
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(-1);
        }
    }

    public boolean mustContinue() throws IOException {
        // Receive data from Police
        System.out.println("Listening...");
        char byteRead = (char) in.read();
        System.out.println("Got '" + byteRead + "'");

        // If socket is closed, finish=true
        return byteRead == 'n'; // TODO: Could be -1 (end of stream)
    }

    public char giveAnswer(char answer) throws IOException {
        //Send data back to Police
        out.write(answer);

        return (char) in.read(); // TODO: Could be -1 (end of stream)
    }

    public static void main(String[] args) throws Exception {
        Prisoner prisoner = new Prisoner();
        prisoner.initialize();

        while (prisoner.mustContinue()) {
            char response = prisoner.giveAnswer('s'); // TODO: s=silent
            System.out.println("Got response '" + response + "' from Police");
        }
    }
}
