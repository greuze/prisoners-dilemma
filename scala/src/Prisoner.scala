package es.greuze.sandbox.prisoners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;

abstract class Prisoner {

    def giveAnswer : String
    def notifyPoliceResponse(response: String)

    var in : BufferedReader = null
    var out : PrintWriter = null

    def initialize(port: Int) {
        val serverSocket = new ServerSocket(port)
        println("Waiting for Police on port " + port);
        val socket = serverSocket.accept

        in = new BufferedReader(new InputStreamReader(socket.getInputStream))
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream), true)
    }

    def startContest() {
        while (mustContinue()) {
            val answer = giveAnswer
            println("I answer: " + answer)
            out.println(answer)
            val response = in.readLine
            notifyPoliceResponse(response)
        }
    }
    
    def mustContinue() : Boolean = {
        // Receive data from Police
        val request = in.readLine

        if ("NEXT" == request) {
            true;
        } else if ("EXIT" == request) {
            false;
        } else {
            throw new RuntimeException("Unknown operation " + request);
        }
    }
}
