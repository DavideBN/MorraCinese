package me.davidebn.morracinese.serverapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Player implements Runnable{

    private String name;
    private BufferedReader input;
    private DataOutputStream output;

    public Player(Socket socket) {
        try {
            this.input = new BufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            sendMessage(ServerApp.WELCOME_MESSAGE);
            name = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public String getName() {
        return name;
    }

    public void sendMessage(String message){
        try {
            output.writeBytes(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getInput() {
        return input;
    }
}
