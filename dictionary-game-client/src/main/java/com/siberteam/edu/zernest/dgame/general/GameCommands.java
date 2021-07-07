package com.siberteam.edu.zernest.dgame.general;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum GameCommands {
    START("/start", "Game started!"),
    FINISH("/finish", "Game finished!"),
    WAITING("/wait", "Waiting for other players..."),
    NEXT_MOVE("/next", "Make your next move");

    private static final Map<String, GameCommands> commandsMap = new HashMap<>();
    private final String command;
    private final String description;

    static {
        Arrays.stream(values()).forEach(gameCommand -> commandsMap.put(gameCommand.command, gameCommand));
    }

    GameCommands(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static GameCommands getGameCommand(String command) {
        return commandsMap.get(command);
    }

    public String getDescription() {
        return description;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return "GameCommands" + "[" +
                "command='" + command + '\'' +
                ", description='" + description + '\'' +
                ']';
    }
}
