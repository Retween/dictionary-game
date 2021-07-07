package com.siberteam.edu.zernest.dgame.client;

import com.siberteam.edu.zernest.dgame.general.GameCommands;
import com.siberteam.edu.zernest.dgame.interfaces.IErrorHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ServerHandler implements Runnable, IErrorHandler {
    private final GameClient client;
    private final ClientInterface clientInterface;

    public ServerHandler(GameClient client) {
        this.client = client;
        clientInterface = client.getClientInterface();
    }

    @Override
    public void run() {
        String message;
        try {
            readerLoop:
            while ((message = client.getReader().readLine()) != null) {
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
                            break readerLoop;
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
        client.getSocket().close();
    }

    private void handleWord(String message) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(10);

        clientInterface.printReceivedMessage(message);
        client.getRemainingWords().remove(message);

        if (client.getRemainingWords().size() == 0) {
            client.setWinner(true);
            client.getWriter().println(GameCommands.FINISH.getCommand());
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
