package com.siberteam.edu.zernest.dgame.client;

import com.siberteam.edu.zernest.dgame.general.GameCommands;
import com.siberteam.edu.zernest.dgame.interfaces.IErrorHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ServerHandler implements Runnable, IErrorHandler {
    private final GameClient client;
    private final ClientInterface clientInterface;
    private boolean gameFinished;

    public ServerHandler(GameClient client) {
        this.client = client;
        clientInterface = client.getClientInterface();
    }

    @Override
    public void run() {
        String message;
        try {
            while (!gameFinished && (message = client.getServerReader().readLine()) != null) {
                GameCommands command = GameCommands.getGameCommand(message);
                if (command != null) {
                    clientInterface.printMessage(command.getDescription());
                    switch (command) {
                        case START:
                            handleStartCommand();
                        case NEXT_MOVE:
                            handleNextMoveCommand();
                            break;
                        case FINISH:
                            handleFinishCommand();
                            break;
                    }
                } else {
                    handleWord(message);
                }
            }
        } catch (IOException | InterruptedException e) {
            handleException(e);
        }
    }


    private void handleStartCommand() {
        clientInterface.printInfo();
        clientInterface.getMoveButton().setEnabled(true);
        clientInterface.getInfoButton().setEnabled(true);
    }

    private void handleNextMoveCommand() {
        clientInterface.printRound();
        client.setMadeMove(false);
    }

    private void handleFinishCommand() throws IOException {
        clientInterface.getMoveButton().setEnabled(false);
        clientInterface.getInfoButton().setEnabled(false);
        clientInterface.printWinner();
        clientInterface.printInfo();
        gameFinished = true;
        client.getSocket().close();
    }

    private void handleWord(String message) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(10);

        clientInterface.printReceivedMessage(message);
        client.getRemainingWordsToWin().remove(message);

        if (client.getRemainingWordsToWin().size() == 0) {
            client.setWinner(true);
            client.getServerWriter().println(GameCommands.FINISH.getCommand());
        }
    }

    @Override
    public String toString() {
        return "ServerHandler" + "[" +
                "client=" + client +
                ", clientInterface=" + clientInterface +
                ']';
    }
}
