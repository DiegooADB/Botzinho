package me.diego.botzinho.scripts;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CommandReader {
    static JSONParser parser = new JSONParser();

    public static Set<String> readCommands() throws IOException, ParseException {
        ClassLoader classLoader = CommandReader.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("config.json")).getFile());

        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonFile = (JSONObject) obj;
        JSONObject commandsJson = (JSONObject) jsonFile.get("commands");

        Set<String> commands = new HashSet<>();

        commandsJson.keySet().forEach(command -> {
            commands.add(command.toString());
        });

        return commands;
    }

    public static String readConfig(String configKey) throws IOException, ParseException {
        ClassLoader classLoader = CommandReader.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("config.json")).getFile());

        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonFile = (JSONObject) obj;
        return (String) jsonFile.get(configKey);
    }
}
