package me.diego.botzinho.config;

import me.diego.botzinho.scripts.CommandReader;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Prefix {
    private String prefix;

    public Prefix() throws IOException, ParseException {
        this.prefix = CommandReader.readConfig("prefix");
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "Prefix{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
