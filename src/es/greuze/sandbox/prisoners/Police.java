package es.greuze.sandbox.prisoners;

import java.net.*;

import java.io.*;

public class Police {

    private BufferedReader in1;
    private PrintWriter out1;
    private BufferedReader in2;
    private PrintWriter out2;

    /**
     * Initialize the Police object with connection data.
     */
    public void initialize(String prisoner1Host, int prisoner1Port, String prisoner2Host, int prisoner2port) {
        // Create socket connection
        try {
            Socket socket1 = new Socket(prisoner1Host, prisoner1Port);
            in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            out1 = new PrintWriter(new OutputStreamWriter(socket1.getOutputStream()), true);

            Socket socket2 = new Socket(prisoner2Host, prisoner2port);
            in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            out2 = new PrintWriter(new OutputStreamWriter(socket2.getOutputStream()), true);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
            System.exit(1);
        } catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }

    /**
     * Request answer to both prisoners, and send them the result.
     */
    public void requestAnswer() throws IOException {
        // Send data over socket
        System.out.println("Request next answers to both prisoners");
        out1.println("NEXT");
        out2.println("NEXT");

        // Receive text from prisoners
        String response1 = in1.readLine();
        System.out.println("Text received from prisoner 1: '" + response1 + "'");
        String response2 = in2.readLine();
        System.out.println("Text received from prisoner 2: '" + response2 + "'");

        // Send reply to prisoners
        String reply = "FREE"; // TODO: Replies could be different
        System.out.println("Reply : " + reply);
        out1.println(reply);
        out2.println(reply);
    }

    public void requestExit() throws IOException {
        // Send data over socket
        System.out.println("Will request prisoners to exit");
        out1.println("EXIT");
        out2.println("EXIT");
    }

    public static void main(String[] args) throws Exception {
        Police police = new Police();
        int totalRounds;

        if (args.length == 5) {
            totalRounds = Integer.parseInt(args[4]);
            police.initialize(args[0],Integer.parseInt(args[1]),
                                args[2], Integer.parseInt(args[3]));
        } else {
            // Default values
            totalRounds = 5;
            police.initialize("localhost", 5000, "localhost", 5001);
        }

        for (int i = 1; i <= totalRounds; i++) {
            System.out.println("Starting round " + i);
            police.requestAnswer();
        }
        police.requestExit();
    }
}
