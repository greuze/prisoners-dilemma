package es.greuze.sandbox.prisoners;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Police {

    private static final boolean DEBUG = Boolean.FALSE;

    private BufferedReader in1;
    private PrintWriter out1;

    private BufferedReader in2;
    private PrintWriter out2;

    // To calculate statistics
    private List<String> results1 = new ArrayList<String>();
    private List<String> results2 = new ArrayList<String>();

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
    private void requestAnswer() {
        // Send data over socket
        log("Request next answers to both prisoners");
        out1.println("NEXT");
        out2.println("NEXT");

        // Receive text from prisoners
        String response1;
        String response2;
        try {
            response1 = in1.readLine();
            log("Text received from prisoner 1: '" + response1 + "'");
            response2 = in2.readLine();
            log("Text received from prisoner 2: '" + response2 + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Calculate replies
        String reply1;
        String reply2;
        if ("SILENT".equals(response1)) {
            if ("SILENT".equals(response2)) {
                reply1 = "MINOR";
                reply2 = "MINOR";
            } else if ("BETRAY".equals(response2)) {
                reply1 = "MAJOR";
                reply2 = "FREE";
            } else {
                throw new RuntimeException("Unknown response " + response2 + " from prisoner 2");
            }
        } else if ("BETRAY".equals(response1)) {
            if ("SILENT".equals(response2)) {
                reply1 = "FREE";
                reply2 = "MAJOR";
            } else if ("BETRAY".equals(response2)) {
                reply1 = "MEDIUM";
                reply2 = "MEDIUM";
            } else {
                throw new RuntimeException("Unknown response " + response2 + " from prisoner 2");
            }
        } else {
            throw new RuntimeException("Unknown response " + response1 + " from prisoner 1");
        }

        // Store the results
        results1.add(reply1);
        results2.add(reply2);

        // Send reply to prisoners
        log("Reply prisoner 1 : " + reply1);
        log("Reply prisoner 2: " + reply2);
        out1.println(reply1);
        out2.println(reply2);
    }

    private void requestExit() {
        // Send data over socket
        log("Will request prisoners to exit");
        out1.println("EXIT");
        out2.println("EXIT");
    }

    private void printStatistics(int totalRounds) {
        System.out.println("From a total of " + totalRounds + " round:");
        System.out.println("\nPrisoner 1 results:");
        printSingleStatistics(results1, totalRounds);
        System.out.println("\nPrisoner 2 results:");
        printSingleStatistics(results2, totalRounds);
    }

    private void printSingleStatistics(List<String> results, int totalRounds) {
        int frees = 0, minors = 0, mediums = 0, majors = 0;
        for (String result : results) {
            if ("FREE".equals(result)) {
                frees++;
            } else if ("MINOR".equals(result)) {
                minors++;
            } else if ("MEDIUM".equals(result)) {
                mediums++;
            } else if ("MAJOR".equals(result)) {
                majors++;
            }
        }
        System.out.println(frees + " times free (" + (100 * frees / totalRounds) + "%)");
        System.out.println(minors + " times minor (" + (100 * minors / totalRounds) + "%)");
        System.out.println(mediums + " times medium (" + (100 * mediums / totalRounds) + "%)");
        System.out.println(majors + " times major (" + (100 * majors / totalRounds) + "%)");

        System.out.println("With a total of " + (majors * 10 + mediums * 5 + minors) + " years of jail");
    }

    private void log(String message) {
        if (DEBUG) {
            System.out.println(message);
        }
    }

    public void startContest(int totalRounds) {
        for (int i = 1; i <= totalRounds; i++) {
            log("Starting round " + i);
            requestAnswer();
        }
        requestExit();

        printStatistics(totalRounds);
    }

    public static void main(String[] args) {
        Police police = new Police();
        int totalRounds;

        if (args.length == 5) {
            totalRounds = Integer.parseInt(args[4]);
            police.initialize(args[0],Integer.parseInt(args[1]),
                                args[2], Integer.parseInt(args[3]));
        } else {
            // Default values
            totalRounds = 50;
            police.initialize("localhost", 5000, "localhost", 5001);
        }

        police.startContest(totalRounds);
    }
}
