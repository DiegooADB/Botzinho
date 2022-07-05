package me.diego.botzinho;

import io.github.cdimascio.dotenv.Dotenv;
import me.diego.botzinho.config.Prefix;
import me.diego.botzinho.debug.Guilds;
import me.diego.botzinho.scripts.CommandExecutor;
import me.diego.botzinho.scripts.CommandReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

public class Botzinho {

    public static JDA jda;
    private static final HashMap<String, Set<String>> commands = new HashMap<>();
    private static Prefix prefix;

    static {
        try {
            prefix = new Prefix();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws LoginException, IOException, ParseException {
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();

        jda = JDABuilder.create(dotenv.get("TOKEN"),
                EnumSet.allOf(GatewayIntent.class)).build();

        onStart();
    }

    private static void onStart() throws IOException, ParseException {
        jda.addEventListener(new CommandExecutor());
        Guilds.showGuilds();
        commands.putAll(CommandReader.readCommands());
        prefix.setPrefix(CommandReader.readConfig("prefix"));
    }

    public static HashMap<String, Set<String>> getCommands() {
        return commands;
    }

    public static String getPrefix() {
        return prefix.getPrefix();
    }
}
