package com.siberteam.edu.zernest.dgame.client;

import com.siberteam.edu.zernest.dgame.interfaces.IErrorHandler;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class ClientInterface implements IErrorHandler {
    private final JButton moveButton;
    private final GameClient gameClient;
    private final JButton infoButton;
    private final JTextPane incoming;

    public ClientInterface(GameClient gameClient) {
        this.gameClient = gameClient;

        JFrame frame = new JFrame("Dictionary Game");
        JPanel mainPanel = new JPanel();

        incoming = new JTextPane();
        incoming.setPreferredSize(new Dimension(400, 400));
        incoming.setEditable(false);

        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        DefaultCaret caret = (DefaultCaret) incoming.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        moveButton = new JButton("Move");
        moveButton.addActionListener(new MakeMoveButtonListener(this));
        moveButton.setEnabled(false);

        infoButton = new JButton("Information");
        infoButton.addActionListener(new InfoButtonListener(this));
        infoButton.setEnabled(false);

        mainPanel.add(moveButton);
        mainPanel.add(infoButton);

        frame.getContentPane().add(BorderLayout.CENTER, qScroller);
        frame.getContentPane().add(BorderLayout.SOUTH, mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.pack();
    }

    private void addColoredText(JTextPane pane, String text, Color color) {
        StyledDocument doc = pane.getStyledDocument();

        Style style = pane.addStyle("Color Style", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            handleException(e);
        }
    }

    public void printWinner() {
        if (gameClient.isWinner()) {
            addColoredText(incoming, "You WIN!\n\n", Color.MAGENTA);
        } else {
            addColoredText(incoming, "You LOSE!\n\n", Color.MAGENTA);
        }
    }

    public void printReceivedMessage(String message) {
        addColoredText(incoming, "You receive: ", Color.BLACK);
        addColoredText(incoming, message + "\n\n", Color.RED);
    }

    public void printRound() {
        addColoredText(incoming, "\tROUND: " + gameClient.getAndIncrementRoundCounter() + "\n", Color.BLACK);
    }

    public void printInfo() {
        addColoredText(incoming, "Your dictionary: ", Color.BLACK);
        for (String st : gameClient.getDictionarySet()) {
            addColoredText(incoming, st + ", ", Color.BLUE);
        }
        addColoredText(incoming, "\n", Color.BLACK);

        addColoredText(incoming, "Remaining words to win: ", Color.BLACK);
        for (String st : gameClient.getRemainingWords()) {
            addColoredText(incoming, st + ", ", Color.RED);
        }
        addColoredText(incoming, "\n\n", Color.BLACK);
    }

    public void printMove(String move) {
        addColoredText(incoming, "Your move: ", Color.BLACK);
        addColoredText(incoming, move, Color.BLUE);
        addColoredText(incoming, ". Wait others...\n\n", Color.BLACK);
    }

    public void printMessage(String message) {
        addColoredText(incoming, message + "\n\n", Color.MAGENTA);
    }

    public JButton getInfoButton() {
        return infoButton;
    }

    public JButton getMoveButton() {
        return moveButton;
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    @Override
    public String toString() {
        return "ClientInterface" + "[" +
                "incoming=" + incoming +
                ", moveButton=" + moveButton +
                ", gameClient=" + gameClient +
                ", infoButton=" + infoButton +
                ']';
    }
}
