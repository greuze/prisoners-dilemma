package es.greuze.sandbox.prisoners;

import java.net.*;

import java.io.*;

public abstract class Prisoner {

    BufferedReader in;
    PrintWriter out;

    public void initialize(int port) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port);
            System.exit(-1);
        }

        Socket client = null;
        try {
            System.out.println("Waiting...");
            client = server.accept();
        } catch (IOException e) {
            System.out.println("Accept failed: " + port);
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

    protected String sendAnswer() throws IOException {
        //Send data back to Police
        String answer = giveAnswer();
        System.out.println("Sending '" + answer + "' to Police");
        out.println(answer);

        return in.readLine();
    }

    // Implement in subclases
    public abstract String giveAnswer();
}
