package me.davidebn.morracinese.serverapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server;
    private final int port;

    private Player queue;

    public Server(int port) {
        this.port = port;
    }

    public void start(){
        try {
            server = new ServerSocket(port);
            System.out.println("Server partito in esecuzione...");
            while (true){
                Socket client = server.accept();
                System.out.println("New Client: " + client);

                Player player = new Player(client);
                Thread thread = new Thread(player);
                thread.start();
                thread.join();

                if (queue != null){
                    Game game = new Game(player, queue);
                    System.out.printf("Started game: %s VS %s \n", player.getName(), queue.getName());
                    Thread gameThread = new Thread(game);
                    gameThread.start();
                    queue = null;
                }else{
                    queue = player;
                    player.sendMessage(ServerApp.WAITING_PLAYER);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
