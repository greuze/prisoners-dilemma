package es.greuze.sandbox.prisoners;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GentlePrisoner extends Prisoner {

    @Override
    public String giveAnswer() {
        return "SILENT";
    }

    public static void main(String[] args) throws Exception {
        GentlePrisoner prisoner = new GentlePrisoner();
        prisoner.initialize(5000);

        while (prisoner.mustContinue()) {
            String response = prisoner.sendAnswer();
            System.out.println("GentlePrisoner got response '" + response + "' from Police");
        }
    }
}
