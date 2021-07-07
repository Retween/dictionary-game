package com.siberteam.edu.zernest.dgame.server;

import com.siberteam.edu.zernest.dgame.general.GameCommands;
import com.siberteam.edu.zernest.dgame.interfaces.IErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable, IErrorHandler {
    private final BufferedReader reader;
    private final GameServer server;

    public ClientHandler(Socket socket, GameServer server) throws IOException {
        this.server = server;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null && !server.isGameFinished()) {
                GameCommands command = GameCommands.getGameCommand(message);
                if (command != null) {
                    if (command.equals(GameCommands.FINISH)) {
                        server.setGameFinished(true);
                    }
                } else {
                    server.getWordsSlots().add(message);
                }
            }
        } catch (IOException e) {
            handleException(e);
        }
    }

    @Override
    public String toString() {
        return "ClientHandler" + "[" +
                "reader=" + reader +
                ", server=" + server +
                ']';
    }
}
