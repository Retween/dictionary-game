package com.siberteam.edu.zernest.dgame;

import org.apache.commons.cli.*;

import java.io.File;

public class CommandLineParser {
    private static final Options options = new Options();
    private File outputFile;
    private String host;
    private int port;

    static {
        options.addRequiredOption("f", "resultFile", true,
                "File to record a result if you win");
        options.addRequiredOption("h", "serverHost", true,
                "Server's host");
        options.addRequiredOption("p", "serverPort", true,
                "Server's port");
    }

    public void parseCommandLine(String[] args) throws ParseException {
        org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        outputFile = new File(cmd.getOptionValue("f"));
        host = cmd.getOptionValue("h");
        port = Integer.parseInt(cmd.getOptionValue("p"));
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();

        String syntax = "Main";
        String usageHeader = "Example of using Game Client";
        String usageFooter = "Usage example: -f result.txt -h localhost -p 8080";

        formatter.printHelp(syntax, usageHeader, options, usageFooter);
    }

    public File getOutputFile() {
        return outputFile;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "CommandLineParser" + "[" +
                "outputFile=" + outputFile +
                ", host='" + host + '\'' +
                ", port=" + port +
                ']';
    }
}
