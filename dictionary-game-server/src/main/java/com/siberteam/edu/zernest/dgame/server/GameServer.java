package com.siberteam.edu.zernest.dgame.server;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.siberteam.edu.zernest.dgame.general.GameCommands;
import com.siberteam.edu.zernest.dgame.interfaces.ILogger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class GameServer implements ILogger {
    private final ServerSocket serverSocket;
    private final List<String> wordsSlots;
    private final ImmutableList<List<String>> dictionariesList;
    private final ArrayList<PrintWriter> clientOutputStreams;
    private boolean gameFinished;
    private final int CLIENTS_NUMBER;
    private int currentClientCounter;

    public GameServer(List<String> generalDictionaryList, int PORT, int CLIENTS_NUMBER) throws IOException {
        this.CLIENTS_NUMBER = CLIENTS_NUMBER;
        serverSocket = new ServerSocket(PORT);
        clientOutputStreams = new ArrayList<>();
        dictionariesList = ImmutableList.copyOf(Lists.partition(generalDictionaryList, CLIENTS_NUMBER));
        wordsSlots = Lists.newCopyOnWriteArrayList();
        gameFinished = false;
    }

    public void startServer() throws IOException {
        while (currentClientCounter < CLIENTS_NUMBER) {
            Socket clientSocket = serverSocket.accept();

            setUpClient(clientSocket);

            new Thread(new ClientHandler(clientSocket, this)).start();

            log("got a connection: " + clientSocket.getRemoteSocketAddress());

            currentClientCounter++;
        }

        startGameProcess();
    }

    private void setUpClient(Socket clientSocket) throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        clientOutputStreams.add(writer);

        ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outputStream.writeObject(getClientDictionary());
        outputStream.writeObject(getSetFromDictionariesList(dictionariesList));

        writer.println(GameCommands.WAITING.getCommand());
    }

    private ImmutableSet<String> getSetFromDictionariesList(List<List<String>> listOfLists) {
        Set<String> mutableSet = new HashSet<>();
        listOfLists.forEach(mutableSet::addAll);
        return ImmutableSet.copyOf(mutableSet);
    }

    private void startGameProcess() {
        tellEveryone(GameCommands.START.getCommand());

        while (!gameFinished) {
            if (wordsSlots.size() == CLIENTS_NUMBER) {
                for (PrintWriter client : clientOutputStreams) {
                    client.println(wordsSlots.remove(new Random(System.currentTimeMillis())
                            .nextInt(wordsSlots.size())));
                }
                tellEveryone(GameCommands.NEXT_MOVE.getCommand());
            }
        }

        tellEveryone(GameCommands.FINISH.getCommand());
        log(GameCommands.FINISH.getDescription());
    }

    public void tellEveryone(String message) {
        for (PrintWriter writer : clientOutputStreams) {
            writer.println(message);
        }
    }

    private ImmutableList<String> getClientDictionary() {
        return ImmutableList.copyOf(dictionariesList.get(currentClientCounter));
    }

    public List<String> getWordsSlots() {
        return wordsSlots;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public  boolean isGameFinished() {
        return gameFinished;
    }

    @Override
    public String toString() {
        return "GameServer" + "[" +
                ", serverSocket=" + serverSocket +
                ", clientsCount=" + CLIENTS_NUMBER +
                ", clientOutputStreams=" + clientOutputStreams +
                ", wordsSlots=" + wordsSlots +
                ']';
    }
}

