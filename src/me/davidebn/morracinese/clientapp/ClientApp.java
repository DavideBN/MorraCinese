package me.davidebn.morracinese.clientapp;

public class ClientApp {

    public static void main(String[] args) {
        Client client = new Client("localhost", 6789);
        client.connect();
        client.game();
    }
}
