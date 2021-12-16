package me.davidebn.morracinese.serverapp;

public class ServerApp {

    public static String WELCOME_MESSAGE = "Benvenuto! Inserisci il tuo nome: ";
    public static String WAITING_PLAYER = "Sei il primo arrivato! Attendi un giocatore...";
    public static String MATCH_FOUND = "Partita trovata! Giocherai contro %player%!";
    public static String CHOOSE_MOVE = "Scegli la tua mossa! (1 Carta, 2 Forbice, 3 Sasso)";
    public static String WRONG_MOVE = "Reinserisci la tua mossa! (1 Carta, 2 Forbice, 3 Sasso)";
    public static String MATCH_ENDED = "Partita terminata!\n%firstPlayer%: %firstMove%\n%secondPlayer%: %secondMove%\nRisultato: %result%";

    public static void main(String[] args) {
        Server server = new Server(6789);
        server.start();
    }
}
