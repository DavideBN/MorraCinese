package me.davidebn.morracinese.serverapp;

import java.io.IOException;

public class Game implements Runnable{

    private final Player firstPlayer;
    private final Player secondPlayer;

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    @Override
    public void run() {
        sendNames();
        requestMove();
    }

    private void requestMove() {
        try {
            firstPlayer.sendMessage(ServerApp.CHOOSE_MOVE);
            secondPlayer.sendMessage(ServerApp.CHOOSE_MOVE);

            int firstMove = Integer.parseInt(firstPlayer.getInput().readLine());
            int secondMove = Integer.parseInt(secondPlayer.getInput().readLine());

            int result = getResult(firstMove, secondMove);

            String firstMoveS = getMoveFromInt(firstMove);
            String secondMoveS = getMoveFromInt(secondMove);
            String winner = (result == 1) ? firstPlayer.getName() : secondPlayer.getName();

            if (result == 0){
                winner = "Pareggio";
            }else{
                winner += " Vincitore";
            }

            String resultMessage = ServerApp.MATCH_ENDED
                    .replace("%firstPlayer%", firstPlayer.getName())
                    .replace("%secondPlayer%", secondPlayer.getName())
                    .replace("%firstMove%", firstMoveS)
                    .replace("%secondMove%", secondMoveS)
                    .replace("%result%", winner);

            firstPlayer.sendMessage(resultMessage);
            secondPlayer.sendMessage(resultMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getMoveFromInt(int move){
        switch (move){
            case 1:
                return "Carta";
            case 2:
                return "Forbice";
            case 3:
                return "Sasso";
            default:
                return "";
        }
    }

    private int getResult(int firstMove, int secondMove){
        if (firstMove == secondMove){
            return 0;
        }

        if ((firstMove == 1 && secondMove == 3) || firstMove == 2 && secondMove == 1){
            return 1;
        }

        return 2;
    }

    private void sendNames() {
        firstPlayer.sendMessage(ServerApp.MATCH_FOUND
                .replace("%player%", "" + secondPlayer.getName()));
        secondPlayer.sendMessage(ServerApp.MATCH_FOUND
                .replace("%player%", "" + firstPlayer.getName()));
    }
}
