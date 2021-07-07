package com.siberteam.edu.zernest.dgame.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MakeMoveButtonListener implements ActionListener {
    private final ClientInterface clientInterface;
    private final GameClient gameClient;
    private String move = null;

    public MakeMoveButtonListener(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
        gameClient = clientInterface.getGameClient();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameClient.isMadeMove()) {
            move = gameClient.makeMove();

            gameClient.getServerWriter().println(move);

            gameClient.setMadeMove(true);
        }
        clientInterface.printMove(move);
    }

    @Override
    public String toString() {
        return "MakeMoveButtonListener" + "[" +
                "clientInterface=" + clientInterface +
                ", gameClient=" + gameClient +
                ", move='" + move + '\'' +
                ']';
    }
}
