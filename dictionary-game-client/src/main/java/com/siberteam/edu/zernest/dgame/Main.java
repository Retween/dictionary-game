package com.siberteam.edu.zernest.dgame;

import com.siberteam.edu.zernest.dgame.client.GameClient;
import com.siberteam.edu.zernest.dgame.interfaces.IDictionaryWriter;
import com.siberteam.edu.zernest.dgame.interfaces.IErrorHandler;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Main {
    private static final IErrorHandler handler = new IErrorHandler() {
        @Override
        public void handleException(Exception e) {
            IErrorHandler.super.handleException(e);
        }
    };

    private static final IDictionaryWriter writer = new IDictionaryWriter() {
        @Override
        public void writeDictionary(Set<String> dictionary, File outputFile) throws IOException {
            IDictionaryWriter.super.writeDictionary(dictionary, outputFile);
        }
    };

    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        try {
            parser.parseCommandLine(args);

            GameClient client = new GameClient(parser.getHost(), parser.getPort());
            client.startGameClient();

            if (client.isWinner()) {
                writer.writeDictionary(client.getGeneralDictionarySet(), parser.getOutputFile());
            }
        } catch (Exception e) {
            parser.printHelp();
            handler.handleException(e);
        }
    }
}
