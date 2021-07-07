package com.siberteam.edu.zernest.dgame.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoButtonListener implements ActionListener {
    private final ClientInterface clientInterface;

    public InfoButtonListener(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clientInterface.printInfo();
    }

    @Override
    public String toString() {
        return "InfoButtonListener" + "[" +
                "clientInterface=" + clientInterface +
                ']';
    }
}
