package es.greuze.sandbox.prisoners;

import java.net.*;

import java.io.*;

public class Prisoner {

    final int PORT = 5000;

    ServerSocket server;
    Socket client;

    BufferedReader in;
    PrintWriter out;

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
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(-1);
        }
    }

    public boolean mustContinue() throws IOException {
        // Receive data from Police
        System.out.println("Waiting instruction...");
        String request = in.readLine();
        System.out.println("Got '" + request + "'");

        if ("NEXT".equals(request)) {
            return true;
        } else if ("EXIT".equals(request)) {
            return false;
        } else {
            throw new RuntimeException("Unknown operation");
        }
    }

    public String giveAnswer(String answer) throws IOException {
        //Send data back to Police
        out.println(answer);

        return in.readLine();
    }

    public static void main(String[] args) throws Exception {
        Prisoner prisoner = new Prisoner();
        prisoner.initialize();

        while (prisoner.mustContinue()) {
            String response = prisoner.giveAnswer("SILENT");
            System.out.println("Got response '" + response + "' from Police");
        }
    }
}
