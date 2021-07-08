package com.siberteam.edu.zernest.dgame;

import org.apache.commons.cli.*;

public class CommandLineParser {
    private static final Options options = new Options();
    private int port;
    private int wordsCount;
    private int clientsCount;

    static {
        options.addRequiredOption("p", "port", true,
                "Server's port");
        options.addRequiredOption("w", "wordsCount", true,
                "Number of words in the players' dictionaries");
        options.addRequiredOption("c", "clientsCount", true,
                "Number of players");
    }

    public void parseCommandLine(String[] args) throws ParseException {
        org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        port = Integer.parseInt(cmd.getOptionValue("p"));
        wordsCount = Integer.parseInt(cmd.getOptionValue("w"));
        clientsCount = Integer.parseInt(cmd.getOptionValue("c"));
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();

        String syntax = "Main";
        String usageHeader = "Example of using Game Server";
        String usageFooter = "Usage example: -p 8080 -w 5 -c 2";

        formatter.printHelp(syntax, usageHeader, options, usageFooter);
    }

    public int getPort() {
        return port;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public int getClientsCount() {
        return clientsCount;
    }

    @Override
    public String toString() {
        return "CommandLineParser" + "[" +
                "port=" + port +
                ", wordsCount=" + wordsCount +
                ", clientsCount=" + clientsCount +
                ']';
    }
}
