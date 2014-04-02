package es.greuze.sandbox.prisoners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;

class GentlePrisoner extends es.greuze.sandbox.prisoners.Prisoner {
    def giveAnswer() : String = {
        "SILENT"
    }

    def notifyPoliceResponse(response: String) {
        println("Police response: " + response)
    }
}

object PrisonerClient {

    def main(args : Array[String]) {
        val p = new GentlePrisoner
        p.initialize(5001)
        p.startContest()
    }
}
