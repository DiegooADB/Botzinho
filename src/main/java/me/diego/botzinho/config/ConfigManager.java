package me.diego.botzinho.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConfigManager {
    public ConfigManager() {
        try {
            loadConfig();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private String prefix = "!";
    private Boolean debug;
    private Long devServerId;

    public String getPrefix() {
        return prefix;
    }
    public boolean isOnDebugMode() {
        return debug;
    }
    public Long getDevServerId() {
        return devServerId;
    }

    private void loadConfig() throws IOException, ParseException {
        File file = new File("src/main/resources/config.json");
        JSONObject json = (JSONObject) new JSONParser().parse(new FileReader(file));

        prefix = (String) json.get("prefix");
        debug = (Boolean) json.get("debug");
        devServerId = (Long) json.get("devServerId");
    }

    private static ConfigManager instance;

    public static ConfigManager getInstance() {
        if (instance == null) instance = new ConfigManager();
        return instance;
    }
}
