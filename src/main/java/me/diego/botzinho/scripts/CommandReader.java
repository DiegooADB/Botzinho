package me.diego.botzinho.scripts;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CommandReader {
    static JSONParser parser = new JSONParser();

    public static HashMap<String, Set<String>> readCommands() throws IOException, ParseException {
        ClassLoader classLoader = CommandReader.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("config.json")).getFile());

        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonFile = (JSONObject) obj;
        JSONObject commandsJson = (JSONObject) jsonFile.get("commands");

        HashMap<String, Set<String>> commands = new HashMap<>();

        commandsJson.keySet().forEach(command -> {
            org.json.JSONObject jsonObject = new org.json.JSONObject(commandsJson);
            JSONArray string = jsonObject.getJSONObject(command.toString()).getJSONArray("aliases");

            Set<String> aliases = new HashSet<>();

            string.forEach(ali -> {
                aliases.add(ali.toString());
            });

            commands.put(command.toString(), aliases);
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
