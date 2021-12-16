package me.davidebn.morracinese.clientapp;

import me.davidebn.morracinese.serverapp.ServerApp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final String serverName;
    private final int port;

    private final Scanner keyBoard;

    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;

    public Client(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
        this.keyBoard = new Scanner(System.in);
    }

    public void connect(){
        try {
            System.out.println("Client partito in esecuzione.");
            socket = new Socket(serverName, port);

            System.out.println("Connesso al server!");
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Errore nella connessione al server!");
            e.printStackTrace();
        }
    }

    public void game(){
        String command = "";
        while(!command.startsWith(ServerApp.MATCH_ENDED.replace("%result%", ""))){
            try {
                command = input.readLine();
                System.out.println(command);

                if (command.equals(ServerApp.WELCOME_MESSAGE)
                        || command.equals(ServerApp.CHOOSE_MOVE)
                        || command.equals(ServerApp.WRONG_MOVE)){
                    String input = handleInput(command);
                    output.writeBytes(input + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handleInput(String input){
        if (input.equals(ServerApp.CHOOSE_MOVE)){
            String move = keyBoard.nextLine();

            while(!isNumber(move)){
                System.out.println(ServerApp.WRONG_MOVE);
                move = keyBoard.nextLine();
            }

            return move;
        }

        return keyBoard.nextLine();
    }

    private boolean isNumber(String input){
        try{
            int move = Integer.parseInt(input);

            return move >= 1 && move <= 3;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
