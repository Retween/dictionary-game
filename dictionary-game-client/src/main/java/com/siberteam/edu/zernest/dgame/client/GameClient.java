package com.siberteam.edu.zernest.dgame.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.siberteam.edu.zernest.dgame.interfaces.ILogger;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameClient implements ILogger {
    private Socket socket;
    private PrintWriter serverWriter;
    private BufferedReader serverReader;
    private ClientInterface clientInterface;
    private Set<String> remainingWordsToWin;
    private ImmutableList<String> dictionarySet;
    private ImmutableSet<String> generalDictionarySet;
    private boolean madeMove;
    private boolean winner;
    private int roundCounter;

    public GameClient(String host, int port) throws IOException {
        setUpNetworking(host, port);
    }

    public void startGameClient() throws IOException, ClassNotFoundException, InterruptedException {
        clientInterface = new ClientInterface(this);

        setUpData();

        Thread serverHandler = new Thread(new ServerHandler(this));
        serverHandler.start();
        serverHandler.join();
    }

    @SuppressWarnings("unchecked")
    private void setUpData() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        dictionarySet = (ImmutableList<String>) inputStream.readObject();
        generalDictionarySet = (ImmutableSet<String>) inputStream.readObject();
        remainingWordsToWin = new HashSet<>(generalDictionarySet);
    }

    private void setUpNetworking(String host, int port) throws IOException {
        socket = new Socket(host, port);
        serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverWriter = new PrintWriter(socket.getOutputStream(), true);
        log("networking established");
    }

    public String makeMove() {
        return dictionarySet.get(new Random(System.currentTimeMillis())
                .nextInt(dictionarySet.size()));
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getServerReader() {
        return serverReader;
    }

    public PrintWriter getServerWriter() {
        return serverWriter;
    }

    public boolean isMadeMove() {
        return madeMove;
    }

    public void setMadeMove(boolean madeMove) {
        this.madeMove = madeMove;
    }

    public List<String> getDictionarySet() {
        return dictionarySet;
    }

    public Set<String> getRemainingWordsToWin() {
        return remainingWordsToWin;
    }

    public int getAndIncrementRoundCounter() {
        return roundCounter++;
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public Set<String> getGeneralDictionarySet() {
        return generalDictionarySet;
    }

    @Override
    public String toString() {
        return "GameClient" + "[" +
                "socket=" + socket +
                ", clientInterface=" + clientInterface +
                ", dictionary=" + dictionarySet +
                ", madeMove=" + madeMove +
                ", winner=" + winner +
                ", roundCounter=" + roundCounter +
                ']';
    }
}

