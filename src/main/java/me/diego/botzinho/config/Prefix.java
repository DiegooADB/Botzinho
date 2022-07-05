package me.diego.botzinho.config;

import me.diego.botzinho.scripts.CommandReader;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Prefix {
    private String prefix;

    public Prefix() {
        try {
            this.prefix = CommandReader.readConfig("prefix");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
