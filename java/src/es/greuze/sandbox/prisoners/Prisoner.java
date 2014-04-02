package es.greuze.sandbox.prisoners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Prisoner {

    private static final boolean DEBUG = Boolean.TRUE;

    private BufferedReader in;
    private PrintWriter out;

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
            System.out.println("Waiting for Police...");
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

    public void startContest() {
        while (mustContinue()) {
            String response = sendAnswer();
            notifyPoliceResponse(response);
        }
    }

    private boolean mustContinue() {
        // Receive data from Police
        log("Waiting instruction...");
        String request = null;
        try {
            request = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log("Got '" + request + "'");

        if ("NEXT".equals(request)) {
            return true;
        } else if ("EXIT".equals(request)) {
            return false;
        } else {
            throw new RuntimeException("Unknown operation " + request);
        }
    }

    private String sendAnswer() {
        //Send data back to Police
        String answer = giveAnswer();
        log("Sending '" + answer + "' to Police");
        out.println(answer);

        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void log(String message) {
        if (DEBUG) {
            System.out.println(message);
        }
    }

    // Implement in subclasses
    public abstract String giveAnswer();
    public abstract void notifyPoliceResponse(String response);
}
