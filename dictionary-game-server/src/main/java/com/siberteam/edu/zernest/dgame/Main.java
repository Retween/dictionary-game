package com.siberteam.edu.zernest.dgame;

import com.siberteam.edu.zernest.dgame.interfaces.IDictionaryReader;
import com.siberteam.edu.zernest.dgame.interfaces.IErrorHandler;
import com.siberteam.edu.zernest.dgame.server.GameServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {
    private static final IErrorHandler handler = new IErrorHandler() {
        @Override
        public void handleException(Exception e) {
            IErrorHandler.super.handleException(e);
        }
    };

    private static final IDictionaryReader reader = new IDictionaryReader() {
        @Override
        public List<String> getDictionary(InputStream inputStream, int allWordsCount) throws IOException {
            return IDictionaryReader.super.getDictionary(inputStream, allWordsCount);
        }
    };

    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        try {
            parser.parseCommandLine(args);

            InputStream dictionaryInputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("dictionary.txt");

            List<String> dictionary = reader.getDictionary(dictionaryInputStream,
                    parser.getWordsCount() * parser.getClientsCount());

            GameServer server = new GameServer(dictionary, parser.getPort(), parser.getClientsCount());

            server.startServer();
        } catch (Exception e) {
            parser.printHelp();
            handler.handleException(e);
        }
    }
}
